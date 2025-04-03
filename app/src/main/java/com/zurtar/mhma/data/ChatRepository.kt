package com.zurtar.mhma.data

import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObjects
import com.zurtar.mhma.chatbot.ChatBranch
import com.zurtar.mhma.chatbot.Sender
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
 * Represents a single chat message in a conversation.
 * A [ChatMessage] consists of the sender (either bot or user), the message content,
 * and the timestamp of when the message was created.
 *
 * @property sender Indicates whether the message is from the user or the bot.
 * @property message The actual content of the message.
 * @property createdAt The timestamp when the message was created.
 */
@Serializable
data class ChatMessage(
    val sender: Sender = Sender.Null,
    val message: String = "",

    @Serializable(with = DateSerializer::class)
    var createdAt: Date? = null
)

/**
 * Represents a log of a chat activity or interaction.
 * This contains a list of user messages, the chat branch type, and metadata like creation time
 * and completion date. The chatbot's responses are not stored in the logs to save memory,
 * as they are inferred from the log type.
 *
 * @property id The unique identifier for this chat log.
 * @property logType The type of chat branch, determining the flow of the conversation.
 * @property content The list of messages from the user.
 * @property createdAt The timestamp when the chat log was created.
 * @property toBeCompleted The date when this chat log is expected to be completed.
 */
@Serializable
data class ChatLog(
    var id: String? = null,
    var logType: ChatBranch? = null,
    var content: List<ChatMessage> = listOf(),

    @Serializable(with = DateSerializer::class)
    var createdAt: Date? = null,

    @Serializable(with = DateSerializer::class)
    var toBeCompleted: Date? = null
)

/**
 * Repository for managing chat logs. This class acts as a middle layer
 * between the data source and the rest of the application.
 *
 * @property chatRemoteDataSource The data source for interacting with the Firestore database.
 */
@Singleton
class ChatRepository @Inject constructor(
    private var chatRemoteDataSource: ChatRemoteDataSource
) {
    /**
     * Adds a new chat log to the remote data source.
     *
     * @param log The chat log to be added.
     */
    suspend fun addLog(log: ChatLog) =
        chatRemoteDataSource.addLog(log)

    /**
     * Deletes a chat log from the remote data source.
     *
     * @param log The chat log to be deleted.
     */
    suspend fun deleteLog(log: ChatLog) =
        chatRemoteDataSource.deleteLog(log)

    /**
     * Deletes a chat log from the remote data source by its ID.
     *
     * @param id The unique ID of the chat log to be deleted.
     */
    suspend fun deleteLog(id: String) =
        chatRemoteDataSource.deleteLog(id)

    /**
     * Retrieves all chat logs from the remote data source as a flow.
     * The logs are emitted in real-time as changes occur in the Firestore collection.
     *
     * @return A [Flow] of lists of [ChatLog] objects.
     */
    fun getChatLogs(): Flow<List<ChatLog>> =
        chatRemoteDataSource.getChatLogs()
}

/**
 * Data source for managing chat logs in Firestore.
 * This class provides functions to add, delete, and retrieve chat logs from Firestore.
 *
 * @property fireStoreDatasource The Firestore instance used to interact with the database.
 */
@Singleton
class ChatRemoteDataSource @Inject constructor(
    private val fireStoreDatasource: FirebaseFirestore
) {
    private val TAG: String = "JournalRemoteDataSource"

    private val collectionRef = fireStoreDatasource.collection("users")
        .document(Firebase.auth.currentUser?.uid!!)
        .collection("ChatLogs")

    /**
     * Adds a new chat log to the Firestore database.
     *
     * @param branch The chat branch associated with the log.
     * @param messageList The list of user messages for this chat log.
     */
    suspend fun addLog(branch: ChatBranch, messageList: List<ChatMessage>) {
        // Implementation for adding a chat log based on the branch and messages
    }

    /**
     * Adds a chat log to Firestore.
     * If the log does not have an ID, the operation is skipped.
     *
     * @param log The chat log to be added.
     */
    suspend fun addLog(log: ChatLog) {
        if (log.id == null) return
        val response = collectionRef
            .document(log.id!!)
            .set(log).await()
    }

    /**
     * Deletes a chat log from Firestore.
     * If the log does not have an ID, the operation is skipped.
     *
     * @param log The chat log to be deleted.
     */
    suspend fun deleteLog(log: ChatLog) {
        if (log.id == null) return
        val response = collectionRef
            .document(log.id!!)
            .delete().await()
    }

    /**
     * Deletes a chat log from Firestore by its ID.
     *
     * @param id The unique ID of the chat log to be deleted.
     */
    suspend fun deleteLog(id: String) {
        val response = collectionRef
            .document(id)
            .delete().await()
    }

    /**
     * Retrieves chat logs from Firestore in real-time.
     * This method listens for changes in the Firestore collection and emits updated logs.
     *
     * @return A [Flow] of lists of [ChatLog] objects.
     */
    fun getChatLogs(): Flow<List<ChatLog>> = callbackFlow {
        val listenerRegistration = collectionRef.addSnapshotListener { snapshot, e ->
            if (e != null) {
                Log.w(TAG, "Listen failed.", e)
                return@addSnapshotListener
            }

            snapshot?.toObjects<ChatLog>()?.let { trySend(it.reversed()) }
        }

        trySend(listOf<ChatLog>())
        awaitClose { listenerRegistration.remove() }
    }
}
