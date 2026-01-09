package com.harukadev.domain.commands.impl

import com.harukadev.adapters.discord.DiscordClient
import com.harukadev.domain.commands.InteractionContext
import com.harukadev.domain.commands.SlashCommand

object PingCommand : SlashCommand {
    override val name: String = "ping"

    override suspend fun execute(context: InteractionContext) {
        context.client.reply(context.payload, DiscordClient.InteractionReplyOptions(
            content = "Pong!"
        ))
    }
}
