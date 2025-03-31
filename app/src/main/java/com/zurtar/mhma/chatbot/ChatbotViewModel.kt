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

@HiltViewModel
class ChatbotViewModel @Inject constructor(
    private val chatRepository: ChatRepository
) : ViewModel() {

    //message manager for handling chat messages and responses
    private val messageManager = ChatbotMessageManager(
        this::addCurrentBranchLog,
        this::addCurrentBranchLog
    )

    //Used for list of all messages
    private val _allMessages = MutableLiveData<List<ChatMessage>>()
    val allMessages: LiveData<List<ChatMessage>> = _allMessages

    //used for list of main chat logs
    private val _logList = MutableLiveData<List<ChatLog>>()
    val logList: LiveData<List<ChatLog>> = _logList

    //initialisation of view model
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

    fun getBranchStep(): Int {
        return messageManager.getBranchStep()
    }

    /*
    Sends a message to the chatbot, also calls on simulate bot responses
    to generate appropriate response. Buttons in the Chatbot page call on this
    function as well, passing specific strings.
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

    fun sendCompletionDate(selectedDate: Date?) {
        messageManager.sendCompletionDate(selectedDate)
    }

    //gets currently active branch/dialogue activity
    fun getCurrentBranch(): ChatBranch {
        return messageManager.getCurrentBranch()
    }

    //gets list of all available chat branches
    fun getBranchList(): List<ChatBranch> {
        return messageManager.getBranchList()
    }


    fun addCurrentBranchLog(log: ChatLog) {
        viewModelScope.launch { chatRepository.addLog(log) }
        messageManager.currentBranchMessages.clear()
    }

    /*
    Creates a new chatlog. It copies a message list provided to it rather than
    directly storing the message list. After producing a copy of the message list, it clears
    the currentBranchMessages.
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


    //retrieves log based on id
    fun getLog(id: String): ChatLog? {
        return logList.value?.find {
            it.id == id
        }
    }

    //deletes log based on id
    fun deleteLog(id: String) {
        viewModelScope.launch { chatRepository.deleteLog(id) }
    }
}