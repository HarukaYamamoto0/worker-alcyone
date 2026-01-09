package com.harukadev.infrastructure.redis

import com.harukadev.config.RedisConfig
import io.lettuce.core.api.sync.RedisCommands

object RedisClientProvider {

    fun commands(): RedisCommands<String, String> = RedisConfig.connection.sync()

    fun asyncCommands() = RedisConfig.connection.async()
}
