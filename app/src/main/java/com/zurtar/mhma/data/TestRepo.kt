package com.zurtar.mhma.data

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

data class BiWeeklyEvaluationEntry(
    var depressionScore: Int,
    var anxietyScore: Int,
    var dateCompleted: Date,
    var depressionResults: String = "",
    var anxietyResults: String = ""
)


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