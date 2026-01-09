package com.harukadev.messaging

import com.harukadev.domain.slash.SlashCommandHandler

object JobRouter {

    fun route(job: Job) {
        when (job.type) {
            "SLASH_COMMAND" -> SlashCommandHandler.handle(job)
            else -> error("Unknown job type: ${job.type}")
        }
    }
}