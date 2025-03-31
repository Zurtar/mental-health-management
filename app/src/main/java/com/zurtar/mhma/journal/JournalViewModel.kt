package com.zurtar.mhma.journal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zurtar.mhma.data.JournalEntry
import com.zurtar.mhma.data.JournalRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.Instant
import java.util.Date
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton


data class JournalUIState(
    var entryList: List<JournalEntry> = listOf(),
)

@HiltViewModel
class JournalViewModel @Inject constructor(
    private val journalRepository: JournalRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(JournalUIState())
    val uiState: StateFlow<JournalUIState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            journalRepository.getJournalEntries().collect { entries ->
                _uiState.update { currentState ->
                    currentState.copy(
                        entryList = entries.reversed()
                    )
                }
            }
        }
    }

    //get all entries is generally used to update the entryList for display
    /* fun getAllEntries() {
         _entryList.value = JournalEntryManager.getAllEntries().reversed()
     }*/

    //adds an entry given its title and content
    fun addEntry(title: String, content: String) = addEntry(
        JournalEntry(
            id = UUID.randomUUID().toString(),
            title = title,
            content = content,
            createdAt = Date.from(Instant.now())
        )
    )


    //adds an entry given a JournalEntry object
    fun addEntry(entry: JournalEntry) {
        var entry_ = entry
        if (entry_.id == "-1")
            entry_ = entry_.copy(id = UUID.randomUUID().toString())
        if (entry_.createdAt == null)
            entry_ = entry_.copy(createdAt = Date.from(Instant.now()))
        viewModelScope.launch { journalRepository.addJournalEntry(entry_) }
    }

    //gets an entry given its id
    fun getEntry(id: String): JournalEntry? {
        return _uiState.value.entryList.find {
            it.id == id
        }
    }

    //deletes an entry given its id
    fun deleteEntry(id: String) {
        viewModelScope.launch { journalRepository.deleteJournalEntry(id) }
    }

    fun deleteEntry(entry: JournalEntry) = deleteEntry(entry.id)

    //resaves new content over an entry at an existing id, given its id and new title and content
    fun updateEntry(id: String, newTitle: String, newContent: String) {
        val entry = JournalEntry(
            id = id,
            title = newTitle,
            content = newContent,
            createdAt = Date.from(Instant.now())
        )

        viewModelScope.launch { journalRepository.updateMoodEntry(entry) }
    }
}