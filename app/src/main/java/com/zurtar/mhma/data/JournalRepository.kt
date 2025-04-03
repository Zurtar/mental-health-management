package com.zurtar.mhma.data

import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObjects
import com.zurtar.mhma.auth.AuthState
import com.zurtar.mhma.data.models.DateSerializer
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import kotlinx.serialization.Serializable
import java.util.Date
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Represents a journal entry that contains a title, content, and the date the entry was created.
 *
 * @property id The unique identifier for the journal entry.
 * @property title The title of the journal entry.
 * @property content The content of the journal entry.
 * @property createdAt The date when the journal entry was created.
 */
data class JournalEntry(
    var id: String = "",
    var title: String = "",
    var content: String = "",

    @Serializable(with = DateSerializer::class)
    var createdAt: Date? = null
)

/**
 * Repository class responsible for handling operations related to journal entries.
 * It acts as a bridge between the data source and the rest of the application, providing methods to
 * add, update, delete, and fetch journal entries.
 *
 * @property journalRemoteDataSource The remote data source for interacting with Firestore for journal entry data.
 */
@Singleton
class JournalRepository @Inject constructor(
    private val journalRemoteDataSource: JournalRemoteDataSource
) {
    /**
     * Deletes a journal entry by its ID.
     *
     * @param id The ID of the journal entry to delete.
     */
    suspend fun deleteJournalEntry(id: String) =
        journalRemoteDataSource.deleteJournalEntry(id = id)

    /**
     * Deletes a journal entry using the provided [JournalEntry] object.
     *
     * @param journalEntry The journal entry to delete.
     */
    suspend fun deleteJournalEntry(journalEntry: JournalEntry) =
        journalRemoteDataSource.deleteJournalEntry(id = journalEntry.id)

    /**
     * Adds a new journal entry to the Firestore database.
     *
     * @param journalEntry The journal entry to add.
     */
    suspend fun addJournalEntry(journalEntry: JournalEntry) =
        journalRemoteDataSource.addJournalEntry(journalEntry)

    /**
     * Updates an existing journal entry in the Firestore database.
     * If the entry does not exist, it will be added.
     *
     * @param journalEntry The journal entry to update.
     */
    suspend fun updateMoodEntry(journalEntry: JournalEntry) =
        journalRemoteDataSource.updateMoodEntry(journalEntry)

    /**
     * Returns a [Flow] that emits a list of journal entries in real-time, listening for updates from Firestore.
     *
     * @return A [Flow] of [JournalEntry] objects.
     */
    fun getJournalEntries(): Flow<List<JournalEntry>> =
        journalRemoteDataSource.getJournalEntries()
}

/**
 * Data source class for managing journal entries in Firestore. This class is responsible for adding,
 * deleting, updating, and fetching journal entries from the Firestore database.
 *
 * @property fireStoreDatasource The Firestore instance used to interact with the Firestore database.
 */
@Singleton
class JournalRemoteDataSource @Inject constructor(
    private val fireStoreDatasource: FirebaseFirestore
) {
    private val TAG: String = "JournalRemoteDataSource"

    /**
     * Deletes a journal entry from Firestore by its ID.
     *
     * @param id The ID of the journal entry to delete.
     */
    suspend fun deleteJournalEntry(id: String) {
        val response = fireStoreDatasource.collection("users")
            .document(Firebase.auth.currentUser?.uid!!)
            .collection("JournalEntries")
            .document(id)
            .delete().await()
    }

    /**
     * Adds a new journal entry to Firestore.
     *
     * @param journalEntry The journal entry to add.
     */
    suspend fun addJournalEntry(journalEntry: JournalEntry) {
        val response = fireStoreDatasource.collection("users")
            .document(Firebase.auth.currentUser?.uid!!)
            .collection("JournalEntries")
            .document(journalEntry.id)
            .set(journalEntry)
    }

    /**
     * Updates an existing journal entry in Firestore. If the journal entry does not exist, it will be added.
     *
     * @param journalEntry The journal entry to update.
     */
    suspend fun updateMoodEntry(journalEntry: JournalEntry) {
        val response = fireStoreDatasource.collection("users")
            .document(Firebase.auth.currentUser?.uid!!)
            .collection("JournalEntries")
            .document(journalEntry.id)
            .set(journalEntry).await()
    }

    /**
     * Fetches all journal entries from Firestore and returns them as a list of [JournalEntry] objects.
     *
     * @return A list of [JournalEntry] objects fetched from Firestore.
     */
    suspend fun fetchJournalEntries(): List<JournalEntry> {
        val response = fireStoreDatasource.collection("users")
            .document(Firebase.auth.currentUser?.uid!!)
            .collection("JournalEntries")
            .get().await()

        return response.toObjects<JournalEntry>()
    }

    /**
     * Returns a [Flow] of journal entries, listening for real-time updates from Firestore.
     *
     * @return A [Flow] of [JournalEntry] objects representing the user's journal entries.
     */
    fun getJournalEntries(): Flow<List<JournalEntry>> = callbackFlow {
        val collectionRef = fireStoreDatasource.collection("users")
            .document(Firebase.auth.currentUser?.uid!!)
            .collection("JournalEntries")

        val listenerRegistration = collectionRef.addSnapshotListener { snapshot, e ->
            if (e != null) {
                Log.w(TAG, "Listen failed.", e)
                return@addSnapshotListener
            }

            snapshot?.toObjects<JournalEntry>()?.let { trySend(it.reversed()) }
        }

        trySend(listOf<JournalEntry>())
        awaitClose { listenerRegistration.remove() }
    }
}