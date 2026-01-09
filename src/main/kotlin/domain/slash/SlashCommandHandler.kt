package com.harukadev.domain.slash

import com.harukadev.messaging.Job

object SlashCommandHandler {

    fun handle(job: Job) {
        println(job.payload.)
    }
}