package com.harukadev.workers

import com.harukadev.infrastructure.redis.RedisClientProvider
import com.harukadev.messaging.JobDeserializer
import com.harukadev.messaging.JobRouter
import com.harukadev.messaging.RetryPolicy
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.future.await
import kotlinx.coroutines.withContext

class WorkerSupervisor {

    private val redis = RedisClientProvider.asyncCommands()
    private val queue = "discord:interactions"
    private val dlq = "discord:interactions:dlq"

    suspend fun start() = withContext(Dispatchers.IO) {
        while (true) {
            val result = try {
                redis.brpop(30L, queue).toCompletableFuture().await()
            } catch (ex: Exception) {
                println("Redis error: ${ex.message}")
                continue
            }

            if (result == null) continue

            val payload = result.value

            val job = try {
                JobDeserializer.deserialize(payload)
            } catch (ex: Exception) {
                println("Deserialize error: ${ex.message}")
                redis.lpush(dlq, payload)
                continue
            }

            try {
                JobRouter.route(job)
            } catch (ex: Exception) {
                if (RetryPolicy.shouldRetry(job)) {
                    println("Retry job: ${ex.message}")
                    val retryJob = job.copy(retries = job.retries + 1)
                    redis.lpush(queue, JobDeserializer.serialize(retryJob))
                } else {
                    redis.lpush(dlq, payload)
                }
            }
        }
    }
}

