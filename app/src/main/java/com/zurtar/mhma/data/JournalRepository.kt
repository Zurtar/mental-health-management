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

data class JournalEntry(
    var id: String = "",
    var title: String = "",
    var content: String = "",

    @Serializable(with = DateSerializer::class)
    var createdAt: Date? = null
)

@Singleton
class JournalRepository @Inject constructor(
    private val journalRemoteDataSource: JournalRemoteDataSource
) {
    suspend fun deleteJournalEntry(id: String) =
        journalRemoteDataSource.deleteJournalEntry(id = id)

    suspend fun deleteJournalEntry(journalEntry: JournalEntry) =
        journalRemoteDataSource.deleteJournalEntry(id = journalEntry.id)

    suspend fun addJournalEntry(journalEntry: JournalEntry) =
        journalRemoteDataSource.addJournalEntry(journalEntry)

    suspend fun updateMoodEntry(journalEntry: JournalEntry) =
        journalRemoteDataSource.updateMoodEntry(journalEntry)


    fun getJournalEntries(): Flow<List<JournalEntry>> =
        journalRemoteDataSource.getJournalEntries()
}

@Singleton
class JournalRemoteDataSource @Inject constructor(
    private val fireStoreDatasource: FirebaseFirestore
) {
    private val TAG: String = "JournalRemoteDataSource"

    suspend fun deleteJournalEntry(id: String) {
        val response = fireStoreDatasource.collection("users")
            .document(Firebase.auth.currentUser?.uid!!)
            .collection("JournalEntries")
            .document(id)
            .delete().await()
    }

    suspend fun addJournalEntry(journalEntry: JournalEntry) {
        val response = fireStoreDatasource.collection("users")
            .document(Firebase.auth.currentUser?.uid!!)
            .collection("JournalEntries")
            .document(journalEntry.id)
            .set(journalEntry)
    }

    // Will add if one is already present
    suspend fun updateMoodEntry(journalEntry: JournalEntry) {
        val response = fireStoreDatasource.collection("users")
            .document(Firebase.auth.currentUser?.uid!!)
            .collection("JournalEntries")
            .document(journalEntry.id)
            .set(journalEntry).await()
    }

    suspend fun fetchJournalEntries(): List<JournalEntry> {
        val response = fireStoreDatasource.collection("users")
            .document(Firebase.auth.currentUser?.uid!!)
            .collection("JournalEntries")
            .get().await()

        return response.toObjects<JournalEntry>()
    }

    fun getJournalEntries(): Flow<List<JournalEntry>> = callbackFlow {
        val collectionRef = fireStoreDatasource.collection("users")
            .document(Firebase.auth.currentUser?.uid!!)
            .collection("JournalEntries")


        val listenerRegistration = collectionRef.addSnapshotListener { snapshot, e ->
            if (e != null) {
                Log.w(TAG, "Listen failed.", e)
                return@addSnapshotListener
            }

            snapshot?.toObjects<JournalEntry>()?.let { trySend(it) }
        }

        trySend(listOf<JournalEntry>())
        awaitClose { listenerRegistration.remove() }
    }
}

