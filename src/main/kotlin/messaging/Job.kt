package com.harukadev.messaging

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class Job(
    val type: String,
    val payload: JsonElement,
    val retries: Int = 0
)