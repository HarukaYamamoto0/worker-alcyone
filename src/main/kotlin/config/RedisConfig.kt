package com.harukadev.config

import io.lettuce.core.RedisClient
import io.lettuce.core.RedisURI
import io.lettuce.core.api.StatefulRedisConnection
import java.time.Duration

object RedisConfig {

    private val redisClient: RedisClient by lazy {
        val host = System.getenv("REDIS_HOST") ?: "localhost"
        val port = System.getenv("REDIS_PORT")?.toInt() ?: 6379
        val password = System.getenv("REDIS_PASSWORD")

        val clientBuilder = RedisURI.builder()
            .withHost(host)
            .withPort(port)

        if (password != null) clientBuilder.withPassword(password)
        RedisClient.create(clientBuilder.build())
    }

    val connection: StatefulRedisConnection<String, String> by lazy {
        redisClient.connect()
    }

    fun shutdown() {
        connection.close()
        redisClient.shutdown()
    }
}
