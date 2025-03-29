package com.zurtar.mhma.chatbot

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import java.time.Instant
import java.util.Date

@RequiresApi(Build.VERSION_CODES.O)
class ChatbotViewModel : ViewModel() {

    //message manager for handling chat messages and responses
    private val messageManager = ChatbotMessageManager(::addCurrentBranchLog)

    //Used for list of all messages
    private val _allMessages = MutableLiveData<List<ChatMessage>>()
    val allMessages: LiveData<List<ChatMessage>> = _allMessages

    //used for list of main chat logs
    private val _logList = MutableLiveData<List<ChatLog>>()
    val logList: LiveData<List<ChatLog>> = _logList

    //initialisation of view model
    init {
        _allMessages.value = messageManager.allMessages
        viewModelScope.launch {
            messageManager.simulateBotResponse("")
            _allMessages.postValue(messageManager.allMessages)
        }
        getAllLogs()
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

    //gets currently active branch/dialogue activity
    fun getCurrentBranch(): ChatBranch {
        return messageManager.getCurrentBranch()
    }

    //gets list of all available chat branches
    fun getBranchList(): List<ChatBranch> {
        return messageManager.getBranchList()
    }

    /*
    gets all available logs stored in the logList. Reverses order so they are displayed
    in order of most recent user creation.
     */
    fun getAllLogs(){
        _logList.value = ChatbotLogManager.getAllLogs().reversed()
    }

    //add a new log from a log object. not currently in use, but may be used for demonstration purposes
    fun addLog(log: ChatLog) {
        ChatbotLogManager.addLog(log)
        getAllLogs()
    }

    /*
    Creates a new chatlog. It copies a message list provided to it rather than
    directly storing the message list. After producing a copy of the message list, it clears
    the currentBranchMessages.
     */
    fun addCurrentBranchLog(messageList: List<ChatMessage>, logType: ChatBranch) {
        val copiedMessageList = messageList.map { it.copy() }

        val log = ChatLog(ChatbotLogManager.getAllLogs().size, logType, copiedMessageList, Date.from(Instant.now()))
        ChatbotLogManager.addLog(log)
        getAllLogs()

        messageManager.currentBranchMessages.clear()
    }

    //retrieves log based on id
    fun getLog(id: Int): ChatLog? {
        return ChatbotLogManager.getLog(id)
    }

    //deletes log based on id
    fun deleteLog(id : Int) {
        ChatbotLogManager.deleteLog(id)
        getAllLogs()
    }
}