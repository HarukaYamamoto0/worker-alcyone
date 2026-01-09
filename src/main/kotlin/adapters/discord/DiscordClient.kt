package com.harukadev.adapters.discord

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.request.patch
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation as ClientContentNegotiation
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

class DiscordClient {
    private val client = HttpClient(CIO) {
        install(ClientContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                encodeDefaults = true
            })
        }
    }

    private val baseUrl = "https://discord.com/api/v10"

    suspend fun reply(payload: InteractionPayload, options: InteractionReplyOptions) {
        val replyUrl = "${baseUrl}/webhooks/${payload.applicationId}/${payload.interactionToken}/messages/@original";

        try {
            client.patch(replyUrl) {
                contentType(ContentType.Application.Json)
                setBody(
                    InteractionReplyOptions(
                        content = options.content,
                    )
                )
            }
        } catch (e: ClientRequestException) {
            println(e)
        }
    }

    @Serializable
    data class InteractionReplyOptions(
        val content: String? = null
    )

    @Serializable
    private data class InteractionResponse(
        val type: Int,
        val data: InteractionResponseData
    )

    @Serializable
    private data class InteractionResponseData(
        val content: String
    )
}