package com.zurtar.mhma

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.Instant
import java.util.Date

object JournalEntryManager {
    private val entryList = mutableListOf<JournalEntry>()

    fun getAllEntries() : List<JournalEntry>{
        return entryList
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun addEntry(title : String, content : String) {
        entryList.add(JournalEntry(System.currentTimeMillis().toInt(), title, content, Date.from(Instant.now())))
    }

    fun deleteEntry(id : Int) {
        entryList.removeIf {
            it.id==id
        }
    }
}