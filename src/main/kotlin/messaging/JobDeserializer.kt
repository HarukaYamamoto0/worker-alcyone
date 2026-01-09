package com.harukadev.messaging

import com.harukadev.util.Json

object JobDeserializer {

    fun deserialize(raw: String): Job {
        return Json.decode<Job>(raw)
    }

    fun serialize(job: Job): String {
        return Json.encode(job)
    }
}