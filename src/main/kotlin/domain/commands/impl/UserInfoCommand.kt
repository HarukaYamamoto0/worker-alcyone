package com.harukadev.domain.commands.impl

import com.harukadev.adapters.discord.DiscordClient
import com.harukadev.domain.commands.InteractionContext
import com.harukadev.domain.commands.UserContextCommand

object UserInfoCommand : UserContextCommand {
    override val name: String = "User Info"

    override suspend fun execute(context: InteractionContext) {
        val targetId = context.payload.targetId
        context.client.reply(context.payload, DiscordClient.InteractionReplyOptions(
            content = "Informações sobre o usuário com ID: $targetId"
        ))
    }
}
