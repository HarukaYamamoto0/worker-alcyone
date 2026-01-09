package com.harukadev.domain.commands

import com.harukadev.adapters.discord.CommandType
import java.util.concurrent.ConcurrentHashMap

object CommandRegistry {
    private val slashCommands = ConcurrentHashMap<String, SlashCommand>()
    private val userCommands = ConcurrentHashMap<String, UserContextCommand>()
    private val messageCommands = ConcurrentHashMap<String, MessageContextCommand>()

    fun register(command: DiscordCommand) {
        when (command) {
            is SlashCommand -> slashCommands[command.name] = command
            is UserContextCommand -> userCommands[command.name] = command
            is MessageContextCommand -> messageCommands[command.name] = command
        }
    }

    fun getCommand(name: String, type: CommandType): DiscordCommand? {
        return when (type) {
            CommandType.SLASH_COMMAND -> slashCommands[name]
            CommandType.USER -> userCommands[name]
            CommandType.MESSAGE -> messageCommands[name]
        }
    }
}
