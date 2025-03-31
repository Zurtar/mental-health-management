package com.zurtar.mhma.data.models

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.util.Date

/***
 * Journal
 *
 * Mood Evaluation
 *
 * Chat log
 */


@Serializable
data class BiWeeklyEvaluationEntry(
    var depressionScore: Int = -1,
    var anxietyScore: Int = -1,
    var depressionResults: String = "",
    var anxietyResults: String = "",

    @Serializable(with = DateSerializer::class)
    var dateCompleted: Date? = null
)
object DateSerializer : KSerializer<Date> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("DateSerializer", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: Date) {
        encoder.encodeString(value.time.toString())
    }

    override fun deserialize(decoder: Decoder): Date {
        return Date(decoder.decodeString().toLong())
    }
}
