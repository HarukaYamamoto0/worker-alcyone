package com.harukadev.adapters.discord

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class InteractionPayload(
    val interactionId: Snowflake,
    val interactionToken: String,
    val applicationId: Snowflake,
    val commandName: String,
    val commandType: CommandType = CommandType.SLASH_COMMAND,
    val options: List<InteractionOption> = emptyList(),
    val userId: Snowflake,
    val guildId: Snowflake? = null,

    val channelId: Snowflake? = null,
    val targetId: Snowflake? = null,
    val timestamp: Long
)

@Serializable
data class InteractionOption(
    val name: String,
    val type: Int,
    val value: JsonElement? = null,
    val options: List<InteractionOption>? = null
)

enum class CommandType(val value: Int) {
    SLASH_COMMAND(0),
    USER(1),
    MESSAGE(2);

    companion object {
        fun fromValue(value: Int) = entries.find { it.value == value } ?: SLASH_COMMAND
    }
}