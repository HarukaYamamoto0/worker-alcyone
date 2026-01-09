package com.harukadev.util

import kotlinx.serialization.json.Json

object Json {

    @PublishedApi
    internal val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
    }

    inline fun <reified T> decode(raw: String): T =
        json.decodeFromString(raw)

    inline fun <reified T> encode(value: T): String =
        json.encodeToString(value)
}
