package com.harukadev.messaging

object RetryPolicy {
    const val MAX_RETRIES = 3

    fun shouldRetry(job: Job): Boolean {
        return job.retries < MAX_RETRIES
    }
}