package com.zurtar.mhma.data

import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenSource
import com.google.firebase.firestore.MetadataChanges
import com.google.firebase.firestore.SnapshotListenOptions
import com.google.firebase.firestore.toObjects
import com.zurtar.mhma.data.models.DateSerializer
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import java.util.Date
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder


@Serializable
data class DailyEvaluationEntryDBSafe(
    val selectedEmotions: List<String> = listOf(),
    val emotionIntensities: List<Float> = listOf(0f, 0f, 0f),
    val emotionsMap: Map<String, Float> = mapOf(),
    val stressLevel: String = "default_initial",


    val strongestEmotionFirst: String = "",
    val strongestEmotionSecond: Float = 0f,

    @Serializable(with = DateSerializer::class)
    val dateCompleted: Date? = null
)

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


object EmotionPairSerializer : KSerializer<Pair<String, Float>> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("DateSerializer", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: Pair<String, Float>) {
        encoder.encodeString(value.first)
        encoder.encodeFloat(value.second)
    }

    override fun deserialize(decoder: Decoder): Pair<String, Float> {
        return Pair(decoder.decodeString(), decoder.decodeFloat())
    }

}

@Singleton
class DailyMoodRepository @Inject constructor(
    private var dailyMoodRemoteDataSource: DailyMoodRemoteDataSource
) {

    suspend fun fetchMoodEntries(): List<DailyEvaluationEntry> =
        dailyMoodRemoteDataSource.fetchMoodEntries()

    suspend fun addMoodEntry(moodEntry: DailyEvaluationEntry) =
        dailyMoodRemoteDataSource.addMoodEntry(moodEntry)

    fun getMoodEntries(): Flow<List<DailyEvaluationEntry>> =
        dailyMoodRemoteDataSource.getMoodEntries()
}

@Singleton
class DailyMoodRemoteDataSource @Inject constructor(
    private val fireStoreDatasource: FirebaseFirestore
) {
    private val TAG = "DailyMoodRemoteDataSource"

    suspend fun addMoodEntry(moodEntry: DailyEvaluationEntry) {
        val response = fireStoreDatasource.collection("users")
            .document(Firebase.auth.currentUser?.uid!!)
            .collection("DailyMoodEntries")
            .add(moodEntry.toDBSafe()).await()
    }

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

    fun getMoodEntries(): Flow<List<DailyEvaluationEntry>> = callbackFlow {
      /*  val collectionRef = fireStoreDatasource.collection("users")
            .document(Firebase.auth.currentUser?.uid!!)
            .collection("DailyMoodEntries")
*/
        val options = SnapshotListenOptions.Builder()
            .setMetadataChanges(MetadataChanges.INCLUDE)
            .setSource(ListenSource.DEFAULT)
            .build();

        val listenerRegistration = fireStoreDatasource.collection("users")
            .document(Firebase.auth.currentUser?.uid!!)
            .collection("DailyMoodEntries").addSnapshotListener(options) { snapshot, e ->
            if (e != null) {
                Log.w(TAG, "Listen failed.", e)
                return@addSnapshotListener
            }
            Log.w(TAG, snapshot.toString())

            snapshot?.toObjects<DailyEvaluationEntryDBSafe>()?.let { trySend(it.toNormalList()) }
        }

        trySend(listOf<DailyEvaluationEntry>())
        awaitClose { listenerRegistration.remove() }
    }
}

fun List<DailyEvaluationEntry>.toDBSafeList(): List<DailyEvaluationEntryDBSafe> {
    return this.map { it.toDBSafe() }
}

fun List<DailyEvaluationEntryDBSafe>.toNormalList(): List<DailyEvaluationEntry> {
    return this.map { it.toNormal() }
}


fun DailyEvaluationEntry.toDBSafe(): DailyEvaluationEntryDBSafe {
    return DailyEvaluationEntryDBSafe(
        selectedEmotions = this.selectedEmotions,
        emotionIntensities = this.emotionIntensities,
        emotionsMap = this.emotionsMap,
        stressLevel = this.stressLevel,
        strongestEmotionFirst = this.emotionsMap.entries.sortedByDescending { it.value }.first().toPair().first,
        strongestEmotionSecond = this.emotionsMap.entries.sortedByDescending { it.value }.first().toPair().second,
        dateCompleted = this.dateCompleted
    )
}

fun DailyEvaluationEntryDBSafe.toNormal(): DailyEvaluationEntry {
    return DailyEvaluationEntry(
        selectedEmotions = this.selectedEmotions,
        emotionIntensities = this.emotionIntensities,
        emotionsMap = this.emotionsMap,
        stressLevel = this.stressLevel,
        strongestEmotion = this.emotionsMap.entries.sortedByDescending { it.value }.first().toPair(),
        dateCompleted = this.dateCompleted
    )
}