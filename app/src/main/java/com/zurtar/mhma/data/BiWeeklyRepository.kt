package com.zurtar.mhma.data

import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObjects
import com.zurtar.mhma.data.models.BiWeeklyEvaluationEntry
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository class for managing Bi-Weekly mood data.
 * This class interacts with the remote data source to fetch and add mood entries.
 *
 * @property biWeeklyMoodRemoteDataSource The data source used to interact with the remote server.
 */
@Singleton
class BiWeeklyMoodRepository @Inject constructor(
    private val biWeeklyMoodRemoteDataSource: BiWeeklyMoodRemoteDataSource
) {

    /**
     * Adds a BiWeekly mood entry to the remote data source.
     *
     * @param moodEntry The mood entry to be added.
     */
    suspend fun addMoodEntry(moodEntry: BiWeeklyEvaluationEntry) =
        biWeeklyMoodRemoteDataSource.addMoodEntry(moodEntry)

    /**
     * Fetches the latest BiWeekly mood entries from the remote data source.
     *
     * @return A list of [BiWeeklyEvaluationEntry] representing the latest mood entries.
     */
    suspend fun fetchLatestMoodEntries(): List<BiWeeklyEvaluationEntry> =
        biWeeklyMoodRemoteDataSource.fetchMoodEntries()
}

/**
 * Data source class for interacting with the Firestore database to manage Bi-Weekly mood entries.
 * This class handles adding and fetching mood entries from the Firestore database.
 *
 * @property fireStoreDatasource The Firestore instance used to interact with the database.
 */
@Singleton
class BiWeeklyMoodRemoteDataSource @Inject constructor(
    private val fireStoreDatasource: FirebaseFirestore
) {
    private val collectionRef = fireStoreDatasource.collection("users")
        .document(Firebase.auth.currentUser?.uid!!)
        .collection("BiweeklyMoodEntries")

    /**
     * Adds a new BiWeekly mood entry to the Firestore database.
     *
     * @param moodEntry The mood entry to be added.
     */
    suspend fun addMoodEntry(moodEntry: BiWeeklyEvaluationEntry) {
        val response = collectionRef
            .add(moodEntry).await()
    }

    /**
     * Fetches all the BiWeekly mood entries from the Firestore database.
     *
     * @return A list of [BiWeeklyEvaluationEntry] objects representing the mood entries.
     */
    suspend fun fetchMoodEntries(): List<BiWeeklyEvaluationEntry> {
        val response = collectionRef
            .get().await()

        return response.toObjects<BiWeeklyEvaluationEntry>()
    }
}
