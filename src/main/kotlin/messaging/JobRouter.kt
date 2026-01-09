package com.harukadev.messaging

import com.harukadev.domain.InteractionHandler

object JobRouter {

    suspend fun route(job: Job) {
        when (job.type) {
            "DISCORD_INTERACTION" -> InteractionHandler.handle(job)
            else -> error("Unknown job type: ${job.type}")
        }
    }
}