package com.zurtar.mhma.data.models

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.util.Date

/**
 * Represents the result of a bi-weekly mood evaluation.
 *
 * This data class holds the depression and anxiety scores, along with their corresponding results
 * and the date the evaluation was completed. It is used for tracking mood evaluations over a bi-weekly period.
 *
 * @property depressionScore The score for the depression evaluation. Default is -1 if not set.
 * @property anxietyScore The score for the anxiety evaluation. Default is -1 if not set.
 * @property depressionResults The results or message related to the depression evaluation.
 * @property anxietyResults The results or message related to the anxiety evaluation.
 * @property dateCompleted The date the evaluation was completed, represented as a nullable `Date` object.
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

/**
 * Custom serializer for `Date` objects to convert them into `String` during serialization
 * and back to `Date` during deserialization. Needed for Firestore integration
 *
 * The serializer stores the `Date` as a string representing the time in milliseconds since the epoch.
 *
 * Example: `Date("2021-12-31T12:59:59")` will be serialized to `"1638364799000"`.
 *
 * This serializer is used by `BiWeeklyEvaluationEntry` to handle the `dateCompleted` property.
 */
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
