package com.zurtar.mhma.chatbot

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.Instant
import java.util.Date

//Used to store and manage ChatLog objects
@RequiresApi(Build.VERSION_CODES.O)
object ChatbotLogManager {
    //Stores list of ChatLog objects
    private val logList = mutableListOf<ChatLog>()

    //Returns all ChatLog objects stored in the manager.
    fun getAllLogs() : List<ChatLog>{
        return logList
    }

    //Creates a new ChatLog and adds it to the logList. (not currently in use, but may be used for demonstration
    fun addLog(branch: ChatBranch, messageList: List<ChatMessage>) {
        logList.add(ChatLog(System.currentTimeMillis().toInt(), branch, messageList, Date.from(Instant.now())))
    }

    // Adds an existing ChatLog object to the logList
    fun addLog(log: ChatLog) {
        logList.add(log)
    }

    // Deletes a ChatLog object from the logList based on its id parameter
    fun deleteLog(id: Int){
        logList.removeIf {
            it.id == id
        }
    }

    //Retrieves a specific ChatLog object from the logList based on its id. Mainly used to view previously created logs from the ChatListPage
    fun getLog(id: Int): ChatLog? {
        return logList.find{
            it.id == id
        }
    }
}