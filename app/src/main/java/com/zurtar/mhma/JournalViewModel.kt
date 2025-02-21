package com.zurtar.mhma

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.time.Instant
import java.util.Date

class JournalViewModel : ViewModel() {
    private var _entryList = MutableLiveData<List<JournalEntry>>()
    val entryList: LiveData<List<JournalEntry>> = _entryList

    fun getAllEntries(){
        _entryList.value = JournalEntryManager.getAllEntries().reversed()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun addEntry(title : String, content : String) {
        JournalEntryManager.addEntry(title, content)
        getAllEntries()
    }

    fun deleteEntry(id : Int) {
        JournalEntryManager.deleteEntry(id)
        getAllEntries()
    }
}