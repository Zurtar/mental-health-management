package com.zurtar.mhma.chatbot

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zurtar.mhma.data.ChatLog
import com.zurtar.mhma.data.ChatMessage
import com.zurtar.mhma.data.ChatRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.Instant
import java.util.Date
import java.util.UUID
import javax.inject.Inject

/**
 * ViewModel responsible for managing chatbot interactions.
 * Handles message processing, chat logs, and user interactions.
 *
 * @property chatRepository Repository handling chat data storage and retrieval.
 */
@HiltViewModel
class ChatbotViewModel @Inject constructor(
    private val chatRepository: ChatRepository
) : ViewModel() {

    /** Message manager for handling chat messages and responses. */
    private val messageManager = ChatbotMessageManager(
        this::addCurrentBranchLog,
        this::addCurrentBranchLog
    )

    /** LiveData holding the list of all messages exchanged in the chat. */
    private val _allMessages = MutableLiveData<List<ChatMessage>>()
    val allMessages: LiveData<List<ChatMessage>> = _allMessages

    /** LiveData holding the list of all main chat logs. */
    private val _logList = MutableLiveData<List<ChatLog>>()
    val logList: LiveData<List<ChatLog>> = _logList

    /**
     * Initializes the ViewModel by fetching stored chat logs and simulating an initial bot response.
     */
    init {
        viewModelScope.launch {
            chatRepository.getChatLogs().collect { logs ->
                _logList.postValue(logs)
            }
        }

        _allMessages.value = messageManager.allMessages
        viewModelScope.launch {
            messageManager.simulateBotResponse("")
            _allMessages.postValue(messageManager.allMessages)
        }
    }

    /**
     * Retrieves the current step in the chatbot's branching logic.
     * @return The current step in the chat branch.
     */
    fun getBranchStep(): Int {
        return messageManager.getBranchStep()
    }

    /**
     * Sends a message from the user to the chatbot.
     * Also triggers the chatbot's response simulation.
     * @param userMessage The message input by the user.
     */
    fun sendMessage(userMessage: String) {
        val message = ChatMessage(Sender.User, userMessage, Date())
        messageManager.addMessage(message)
        _allMessages.value = messageManager.allMessages

        messageManager.currentBranchMessages.add(message)

        viewModelScope.launch {
            messageManager.simulateBotResponse(userMessage)
            _allMessages.postValue(messageManager.allMessages)
        }
    }

    /**
     * Sends a completion date for a chat log if applicable.
     * @param selectedDate The date selected by the user.
     */
    fun sendCompletionDate(selectedDate: Date?) {
        messageManager.sendCompletionDate(selectedDate)
    }

    /**
     * Retrieves the currently active chat branch or dialogue.
     * @return The active chat branch.
     */
    fun getCurrentBranch(): ChatBranch {
        return messageManager.getCurrentBranch()
    }

    /**
     * Retrieves a list of all available chat branches.
     * @return A list of available chat branches.
     */
    fun getBranchList(): List<ChatBranch> {
        return messageManager.getBranchList()
    }

    /**
     * Adds a chat log to the repository and clears the current branch messages.
     * @param log The chat log to be added.
     */
    fun addCurrentBranchLog(log: ChatLog) {
        viewModelScope.launch { chatRepository.addLog(log) }
        messageManager.currentBranchMessages.clear()
    }

    /**
     * Creates and adds a new chat log based on a list of messages.
     * It makes a copy of the provided message list before storing it.
     * @param messageList The list of messages to be stored in the log.
     * @param logType The type of chat branch associated with the log.
     * @param toBeCompleted (Optional) A date indicating when the chat log should be completed.
     */
    fun addCurrentBranchLog(
        messageList: List<ChatMessage>,
        logType: ChatBranch,
        toBeCompleted: Date? = null
    ) {
        val copiedMessageList = messageList.map { it.copy() }

        val log = ChatLog(
            id = UUID.randomUUID().toString(),
            logType = logType,
            content = copiedMessageList,
            createdAt = Date.from(Instant.now()),
            toBeCompleted = toBeCompleted
        )

        addCurrentBranchLog(log)
    }

    /**
     * Retrieves a specific chat log based on its ID.
     * @param id The unique identifier of the chat log.
     * @return The matching chat log, or null if not found.
     */
    fun getLog(id: String): ChatLog? {
        return logList.value?.find { it.id == id }
    }

    /**
     * Deletes a chat log based on its ID.
     * @param id The unique identifier of the chat log to be deleted.
     */
    fun deleteLog(id: String) {
        viewModelScope.launch { chatRepository.deleteLog(id) }
    }
}
