package com.harukadev.domain.commands

import com.harukadev.adapters.discord.InteractionPayload
import com.harukadev.adapters.discord.DiscordClient

data class InteractionContext(
    val payload: InteractionPayload,
    val client: DiscordClient
)

interface DiscordCommand {
    val name: String
    suspend fun execute(context: InteractionContext)
}

interface SlashCommand : DiscordCommand
interface UserContextCommand : DiscordCommand
interface MessageContextCommand : DiscordCommand
