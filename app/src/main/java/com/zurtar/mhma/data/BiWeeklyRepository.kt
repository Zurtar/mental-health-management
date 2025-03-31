package com.zurtar.mhma.data

import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObjects
import com.zurtar.mhma.data.models.BiWeeklyEvaluationEntry
import kotlinx.coroutines.tasks.await
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.util.Date
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BiWeeklyMoodRepository @Inject constructor(
    private val biWeeklyMoodRemoteDataSource: BiWeeklyMoodRemoteDataSource,
//    private val moodRemoteLocalSource: MoodLocalDataSource
) {

    suspend fun addMoodEntry(moodEntry: BiWeeklyEvaluationEntry) =
        biWeeklyMoodRemoteDataSource.addMoodEntry(moodEntry)

    suspend fun fetchLatestMoodEntries(): List<BiWeeklyEvaluationEntry> =
        biWeeklyMoodRemoteDataSource.fetchMoodEntries()
}

@Singleton
class BiWeeklyMoodRemoteDataSource @Inject constructor(
    private val fireStoreDatasource: FirebaseFirestore
) {
    private val collectionRef = fireStoreDatasource.collection("users")
        .document(Firebase.auth.currentUser?.uid!!)
        .collection("BiweeklyMoodEntries")

    suspend fun addMoodEntry(moodEntry: BiWeeklyEvaluationEntry) {
        val response = collectionRef
            .add(moodEntry).await()
    }

    suspend fun fetchMoodEntries(): List<BiWeeklyEvaluationEntry> {
        val response = collectionRef
            .get().await()

        return response.toObjects<BiWeeklyEvaluationEntry>()
    }
}