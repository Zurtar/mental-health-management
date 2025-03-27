package com.zurtar.mhma.data

import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.firestore.toObjects
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import java.util.Date

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

class MoodRepository(
    private val moodRemoteDataSource: MoodRemoteDataSource,
//    private val moodRemoteLocalSource: MoodLocalDataSource
) {

    suspend fun addMoodEntry(moodEntry: MoodEntry) =
        moodRemoteDataSource.addMoodEntry(moodEntry)

    suspend fun fetchLatestMoodEntries(): List<MoodEntry> =
        moodRemoteDataSource.fetchMoodEntries()
}

class MoodRemoteDataSource() {
    private val db = Firebase.firestore

    suspend fun addMoodEntry(moodEntry: MoodEntry) {
        val response = db.collection("users")
            .document(Firebase.auth.currentUser?.uid!!)
            .collection("mood-entries")
            .add(moodEntry).await()
    }

    suspend fun fetchMoodEntries(): List<MoodEntry> {
        val response = db.collection("users")
            .document(Firebase.auth.currentUser?.uid!!)
            .collection("mood-entries")
            .get().await()

        return response.toObjects<MoodEntry>()
    }
}
