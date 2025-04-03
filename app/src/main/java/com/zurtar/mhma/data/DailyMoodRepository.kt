package com.zurtar.mhma.data

import android.util.Log
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Field
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenSource
import com.google.firebase.firestore.MetadataChanges
import com.google.firebase.firestore.PropertyName
import com.google.firebase.firestore.SnapshotListenOptions
import com.google.firebase.firestore.toObjects
import com.zurtar.mhma.data.models.DateSerializer
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.Date
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlin.random.Random

/**
 * Represents a daily mood evaluation entry that is stored safely in the database.
 * This includes the selected emotions, their intensities, a map of emotions to their values,
 * the stress level, the strongest emotions (first and second), and the date the entry was completed.
 *
 * @property selectedEmotions The list of emotions selected by the user.
 * @property emotionIntensities The intensities of the selected emotions.
 * @property emotionsMap A map containing the emotions and their respective intensities.
 * @property stressLevel The user's reported stress level.
 * @property strongestEmotionFirst The first strongest emotion (stored in Firestore).
 * @property strongestEmotionSecond The intensity of the second strongest emotion.
 * @property dateCompleted The date when the mood evaluation was completed.
 */
@Serializable
data class DailyEvaluationEntryDBSafe(
    val selectedEmotions: List<String> = listOf(),
    val emotionIntensities: List<Float> = listOf(0f, 0f, 0f),
    val emotionsMap: Map<String, Float> = mapOf(),
    val stressLevel: String = "default_initial",


    @PropertyName("strongestEmotion_first")
    @get:PropertyName("strongestEmotion_first")
    val strongestEmotionFirst: String? = null,

    @PropertyName("strongestEmotion_second")
    @get:PropertyName("strongestEmotion_second")
    val strongestEmotionSecond: Float = 0f,

    @Serializable(with = DateSerializer::class)
    val dateCompleted: Date? = null
)

/**
 * Represents a daily mood evaluation entry that is used in the application.
 * This includes the selected emotions, their intensities, a map of emotions to their values,
 * the stress level, the strongest emotion (as a pair), and the date the entry was completed.
 *
 * @property selectedEmotions The list of emotions selected by the user.
 * @property emotionIntensities The intensities of the selected emotions.
 * @property emotionsMap A map containing the emotions and their respective intensities.
 * @property stressLevel The user's reported stress level.
 * @property strongestEmotion The strongest emotion and its intensity as a pair.
 * @property dateCompleted The date when the mood evaluation was completed.
 */
@Serializable
data class DailyEvaluationEntry(
    val selectedEmotions: List<String> = listOf(),
    val emotionIntensities: List<Float> = listOf(0f, 0f, 0f),
    val emotionsMap: Map<String, Float> = mapOf(),
    val stressLevel: String = "default_initial",

    val strongestEmotion: Pair<String, Float> = Pair("", 0f),

    @Serializable(with = DateSerializer::class)
    val dateCompleted: Date? = null
)

/**
 * Serializer for the [Pair] of emotions and their intensities, allowing serialization and deserialization.
 * This serializer encodes the emotion name as a string and its intensity as a float.
 */
object EmotionPairSerializer : KSerializer<Pair<String, Float>> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("DateSerializer", PrimitiveKind.STRING)

    /**
     * Serializes a [Pair] of emotion and intensity.
     *
     * @param encoder The encoder used to serialize the data.
     * @param value The pair of emotion and intensity to serialize.
     */
    override fun serialize(encoder: Encoder, value: Pair<String, Float>) {
        encoder.encodeString(value.first)
        encoder.encodeFloat(value.second)
    }

    /**
     * Deserializes a [Pair] of emotion and intensity from a serialized format.
     *
     * @param decoder The decoder used to deserialize the data.
     * @return The deserialized pair of emotion and intensity.
     */
    override fun deserialize(decoder: Decoder): Pair<String, Float> {
        return Pair(decoder.decodeString(), decoder.decodeFloat())
    }

}

/**
 * Repository for managing daily mood evaluations. This repository serves as a middle layer
 * between the data source and the application, handling operations related to daily mood entries.
 *
 * @property dailyMoodRemoteDataSource The remote data source for interacting with Firestore.
 */
@Singleton
class DailyMoodRepository @Inject constructor(
    private var dailyMoodRemoteDataSource: DailyMoodRemoteDataSource
) {
    /**
     * Fetches all mood entries from the remote data source.
     *
     * @return A list of [DailyEvaluationEntry] representing the mood entries.
     */
    suspend fun fetchMoodEntries(): List<DailyEvaluationEntry> =
        dailyMoodRemoteDataSource.fetchMoodEntries()

    /**
     * Adds a new mood entry to the remote data source.
     *
     * @param moodEntry The mood entry to be added.
     */
    suspend fun addMoodEntry(moodEntry: DailyEvaluationEntry) =
        dailyMoodRemoteDataSource.addMoodEntry(moodEntry)

    /**
     * Returns a [Flow] of mood entries from the remote data source.
     * This flow emits real-time updates when data changes.
     *
     * @return A [Flow] of lists of [DailyEvaluationEntry].
     */
    fun getMoodEntries(): Flow<List<DailyEvaluationEntry>> =
        dailyMoodRemoteDataSource.getMoodEntries()
}

/**
 * Data source for managing daily mood evaluations in Firestore.
 * This class provides methods for adding, fetching, and listening to mood entries.
 *
 * @property fireStoreDatasource The Firestore instance used to interact with the database.
 */
@Singleton
class DailyMoodRemoteDataSource @Inject constructor(
    private val fireStoreDatasource: FirebaseFirestore
) {
    private val TAG = "DailyMoodRemoteDataSource"

    /**
     * Adds a new mood entry to the Firestore database.
     *
     * @param moodEntry The mood entry to be added.
     */
    suspend fun addMoodEntry(moodEntry: DailyEvaluationEntry) {
        val response = fireStoreDatasource.collection("users")
            .document(Firebase.auth.currentUser?.uid!!)
            .collection("DailyMoodEntries")
            .add(moodEntry.toDBSafe()).await()
    }

    /**
     * Fetches all mood entries from the Firestore database.
     *
     * @return A list of [DailyEvaluationEntry] fetched from the database.
     */
    suspend fun fetchMoodEntries(): List<DailyEvaluationEntry> {
        val documents = fireStoreDatasource.collection("users")
            .document(Firebase.auth.currentUser?.uid!!)
            .collection("DailyMoodEntries")
            .get().await()

        for (doc in documents) {
            Log.d("FirestoreDocument", doc.data.toString())
        }

        return fireStoreDatasource.collection("users")
            .document(Firebase.auth.currentUser?.uid!!)
            .collection("DailyMoodEntries")
            .get().await().toObjects<DailyEvaluationEntryDBSafe>().toNormalList()
    }

    /**
     * Listens for real-time updates to the mood entries in Firestore and emits changes as a [Flow].
     *
     * @return A [Flow] of lists of [DailyEvaluationEntry].
     */
    fun getMoodEntries(): Flow<List<DailyEvaluationEntry>> = callbackFlow {
        val options = SnapshotListenOptions.Builder()
            .setMetadataChanges(MetadataChanges.INCLUDE)
            .setSource(ListenSource.DEFAULT)
            .build()

        val listenerRegistration = fireStoreDatasource.collection("users")
            .document(Firebase.auth.currentUser?.uid!!)
            .collection("DailyMoodEntries").addSnapshotListener(options) { snapshot, e ->
                if (e != null) {
                    Log.w(TAG, "Listen failed.", e)
                    return@addSnapshotListener
                }
                Log.w(TAG, snapshot.toString())

                snapshot?.toObjects<DailyEvaluationEntryDBSafe>()
                    ?.let { trySend(it.toNormalList()) }
            }

        trySend(listOf<DailyEvaluationEntry>())
        awaitClose { listenerRegistration.remove() }
    }
}

/**
 * Extension function to convert a list of [DailyEvaluationEntry] to a list of [DailyEvaluationEntryDBSafe].
 *
 * @return A list of [DailyEvaluationEntryDBSafe] representing the database-safe version of the entries.
 */
fun List<DailyEvaluationEntry>.toDBSafeList(): List<DailyEvaluationEntryDBSafe> {
    return this.map { it.toDBSafe() }
}

/**
 * Extension function to convert a list of [DailyEvaluationEntryDBSafe] to a list of [DailyEvaluationEntry].
 *
 * @return A list of [DailyEvaluationEntry] representing the normal version of the entries.
 */
fun List<DailyEvaluationEntryDBSafe>.toNormalList(): List<DailyEvaluationEntry> {
    return this.map { it.toNormal() }
}

/**
 * Converts a [DailyEvaluationEntry] to a [DailyEvaluationEntryDBSafe] object for safe storage in Firestore.
 *
 * @return A [DailyEvaluationEntryDBSafe] object.
 */
fun DailyEvaluationEntry.toDBSafe(): DailyEvaluationEntryDBSafe {
    return DailyEvaluationEntryDBSafe(
        selectedEmotions = this.selectedEmotions,
        emotionIntensities = this.emotionIntensities,
        emotionsMap = this.emotionsMap,
        stressLevel = this.stressLevel,
        strongestEmotionFirst = this.emotionsMap.entries.sortedByDescending { it.value }?.first()
            ?.toPair()?.first ?: listOf("Fearful", "Sad", "Angry").shuffled().first(),
        strongestEmotionSecond = this.emotionsMap.entries.sortedByDescending { it.value }?.first()
            ?.toPair()?.second ?: Random.nextInt(0, 10).toFloat(),
        dateCompleted = this.dateCompleted
    )
}

/**
 * Converts a [DailyEvaluationEntryDBSafe] to a [DailyEvaluationEntry].
 *
 * @return A [DailyEvaluationEntry] object.
 */
fun DailyEvaluationEntryDBSafe.toNormal(): DailyEvaluationEntry {
    return DailyEvaluationEntry(
        selectedEmotions = this.selectedEmotions,
        emotionIntensities = this.emotionIntensities,
        emotionsMap = this.emotionsMap,
        stressLevel = this.stressLevel,
        strongestEmotion = Pair(this.strongestEmotionFirst ?: "Other", this.strongestEmotionSecond),
        dateCompleted = this.dateCompleted
    )
}