package com.zurtar.mhma

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.Instant
import java.util.Date

object JournalEntryManager {
    private val entryList = mutableListOf<JournalEntry>()

    //returns current list of entries, used to update display after adding or deleting entries
    fun getAllEntries() : List<JournalEntry>{
        return entryList
    }

    //used to add entry given title and content
    @RequiresApi(Build.VERSION_CODES.O)
    fun addEntry(title : String, content : String) {
        entryList.add(JournalEntry(System.currentTimeMillis().toInt(), title, content, Date.from(Instant.now())))
    }


    //used to add entry given a JournalEntry object
    fun addEntry(entry: JournalEntry) {
        entryList.add(entry)
    }

    //used to delete an entry given its id
    fun deleteEntry(id : Int) {
        entryList.removeIf {
            it.id==id
        }
    }

    //used to get an entry given its id
    fun getEntry(id: Int): JournalEntry? {
        return entryList.find { it.id == id }
    }

    //used to edit an entry given its id, new title, and new content
    fun editEntry(id: Int, newTitle: String, newContent: String) {
        val entry = getEntry(id)
        entry?.let {
            entryList.remove(it)
            entryList.add(JournalEntry(id, newTitle, newContent, it.createdAt))
        }
    }
}