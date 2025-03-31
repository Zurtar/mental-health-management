package com.zurtar.mhma.data

import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObjects
import com.zurtar.mhma.data.models.BiWeeklyEvaluationEntry
import com.zurtar.mhma.data.models.DateSerializer
import com.zurtar.mhma.mood.DailyEvaluationEntry
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import kotlinx.serialization.Serializable
import java.util.Date
import javax.inject.Inject
import javax.inject.Singleton

@Serializable
data class DailyEvaluationEntry(
    val selectedEmotions: List<String> = listOf(),
    val emotionIntensities: List<Float> = listOf(0f, 0f, 0f),
    val emotionsMap: Map<String, Float> = mapOf(),
    val stressLevel: String = "default_initial",
    val strongestEmotion: Pair<String, Float> = "" to 0f,

    @Serializable(with = DateSerializer::class)
    val dateCompleted: Date? = null
)

@Singleton
class DailyMoodRepository @Inject constructor(
    private var dailyMoodRemoteDataSource: DailyMoodRemoteDataSource
) {
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
            .add(moodEntry).await()
    }

    fun getMoodEntries(): Flow<List<DailyEvaluationEntry>> = callbackFlow {
        val collectionRef = fireStoreDatasource.collection("users")
            .document(Firebase.auth.currentUser?.uid!!)
            .collection("DailyMoodEntries")


        val listenerRegistration = collectionRef.addSnapshotListener { snapshot, e ->
            if (e != null) {
                Log.w(TAG, "Listen failed.", e)
                return@addSnapshotListener
            }

            snapshot?.toObjects<DailyEvaluationEntry>()?.let { trySend(it) }
        }

        trySend(listOf<DailyEvaluationEntry>())
        awaitClose { listenerRegistration.remove() }
    }
}