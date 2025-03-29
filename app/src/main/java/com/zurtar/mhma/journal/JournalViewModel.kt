package com.zurtar.mhma.journal

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class JournalViewModel : ViewModel() {
    private var _entryList = MutableLiveData<List<JournalEntry>>()
    val entryList: LiveData<List<JournalEntry>> = _entryList

    //get all entries is generally used to update the entryList for display
    fun getAllEntries(){
        _entryList.value = JournalEntryManager.getAllEntries().reversed()
    }

    //adds an entry given its title and content
    fun addEntry(title : String, content : String) {
        JournalEntryManager.addEntry(title, content)
        getAllEntries()
    }

    //adds an entry given a JournalEntry object
    fun addEntry(entry: JournalEntry) {
        JournalEntryManager.addEntry(entry)
        getAllEntries()
    }

    //gets an entry given its id
    fun getEntry(id: Int): JournalEntry? {
        return JournalEntryManager.getEntry(id)
    }

    //deletes an entry given its id
    fun deleteEntry(id : Int) {
        JournalEntryManager.deleteEntry(id)
        getAllEntries()
    }

    //resaves new content over an entry at an existing id, given its id and new title and content
    fun editEntry(id: Int, newTitle: String, newContent: String) {
        JournalEntryManager.editEntry(id, newTitle, newContent)
        getAllEntries()
    }
}