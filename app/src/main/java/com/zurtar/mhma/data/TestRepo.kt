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


data class MoodEntry(
    val depressionScore: Int = 0,
    val anxietyScore: Int = 0,
    val dateCompleted: Date? = null
)

@Singleton
class MoodRepository @Inject constructor(
    private val moodRemoteDataSource: MoodRemoteDataSource,
//    private val moodRemoteLocalSource: MoodLocalDataSource
) {

    suspend fun addMoodEntry(moodEntry: MoodEntry) =
        moodRemoteDataSource.addMoodEntry(moodEntry)

    suspend fun fetchLatestMoodEntries(): List<MoodEntry> =
        moodRemoteDataSource.fetchMoodEntries()
}

@Singleton
class MoodRemoteDataSource @Inject constructor(
    private val fireStoreDatasource: FirebaseFirestore
) {

    suspend fun addMoodEntry(moodEntry: MoodEntry) {
        val response = fireStoreDatasource.collection("users")
            .document(Firebase.auth.currentUser?.uid!!)
            .collection("mood-entries")
            .add(moodEntry).await()
    }

    suspend fun fetchMoodEntries(): List<MoodEntry> {
        val response = fireStoreDatasource.collection("users")
            .document(Firebase.auth.currentUser?.uid!!)
            .collection("mood-entries")
            .get().await()

        return response.toObjects<MoodEntry>()
    }
}
