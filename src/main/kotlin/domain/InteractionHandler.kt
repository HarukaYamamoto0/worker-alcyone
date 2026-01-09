package com.harukadev.domain

import com.harukadev.adapters.discord.DiscordClient
import com.harukadev.adapters.discord.InteractionPayload
import com.harukadev.domain.commands.CommandRegistry
import com.harukadev.domain.commands.InteractionContext
import com.harukadev.messaging.Job
import com.harukadev.util.Json
import kotlinx.serialization.json.decodeFromJsonElement

object InteractionHandler {
    private val discordClient = DiscordClient()

    suspend fun handle(job: Job) {
        val payload = try {
            Json.json.decodeFromJsonElement<InteractionPayload>(job.payload)
        } catch (e: Exception) {
            println("Error decoding InteractionPayload: ${e.message}")
            return
        }

        val command = CommandRegistry.getCommand(payload.commandName, payload.commandType)
        println(command)
        
        if (command != null) {
            val context = InteractionContext(payload, discordClient)
            try {
                command.execute(context)
            } catch (e: Exception) {
                println("Error executing command ${payload.commandName}: ${e.message}")
                throw e
            }
        } else {
            println("Command not found: ${payload.commandName} (Type: ${payload.commandType})")
        }
    }
}
