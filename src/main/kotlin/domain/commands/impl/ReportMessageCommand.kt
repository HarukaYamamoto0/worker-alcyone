package com.harukadev.domain.commands.impl

import com.harukadev.adapters.discord.DiscordClient
import com.harukadev.domain.commands.InteractionContext
import com.harukadev.domain.commands.MessageContextCommand

object ReportMessageCommand : MessageContextCommand {
    override val name: String = "Report Message"

    override suspend fun execute(context: InteractionContext) {
        val targetId = context.payload.targetId
        context.client.reply(context.payload, DiscordClient.InteractionReplyOptions(
            content = "Mensagem $targetId reportada com sucesso!"
        ))
    }
}
