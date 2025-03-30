package com.zurtar.mhma.data

import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.firestore.toObjects
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.Serializer
import kotlinx.serialization.UseSerializers
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.Date
import javax.inject.Inject
import javax.inject.Singleton

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

@Singleton
class MoodRepository @Inject constructor(
    private val moodRemoteDataSource: MoodRemoteDataSource,
//    private val moodRemoteLocalSource: MoodLocalDataSource
) {

    suspend fun addMoodEntry(moodEntry: BiWeeklyEvaluationEntry) =
        moodRemoteDataSource.addMoodEntry(moodEntry)

    suspend fun fetchLatestMoodEntries(): List<BiWeeklyEvaluationEntry> =
        moodRemoteDataSource.fetchMoodEntries()
}

@Singleton
class MoodRemoteDataSource @Inject constructor(
    private val fireStoreDatasource: FirebaseFirestore
) {
    suspend fun addMoodEntry(moodEntry: BiWeeklyEvaluationEntry) {
        val response = fireStoreDatasource.collection("users")
            .document(Firebase.auth.currentUser?.uid!!)
            .collection("BiweeklyMoodEntries")
            .add(moodEntry).await()
    }


    suspend fun fetchMoodEntries(): List<BiWeeklyEvaluationEntry> {
        val response = fireStoreDatasource.collection("users")
            .document(Firebase.auth.currentUser?.uid!!)
            .collection("BiweeklyMoodEntries")
            .get().await()


        return response.toObjects<BiWeeklyEvaluationEntry>()
    }
}