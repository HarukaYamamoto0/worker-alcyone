package com.harukadev.adapters.discord

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

@Serializable(with = SnowflakeSerializer::class)
@JvmInline
value class Snowflake(val value: Long) {
    override fun toString(): String = value.toString()
}

object SnowflakeSerializer : KSerializer<Snowflake> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("Snowflake", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): Snowflake {
        return Snowflake(decoder.decodeString().toLong())
    }

    override fun serialize(encoder: Encoder, value: Snowflake) {
        encoder.encodeString(value.toString())
    }
}
