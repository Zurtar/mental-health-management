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

/**
 * UI State for managing the journal entries.
 *
 * @property entryList A list of [JournalEntry] objects representing the journal entries.
 */
data class JournalUIState(
    var entryList: List<JournalEntry> = listOf(),
)

/**
 * ViewModel responsible for handling journal entry operations and providing them to the UI.
 *
 * This ViewModel interacts with the [JournalRepository] to manage the creation, updating, deletion,
 * and retrieval of journal entries. It exposes a [StateFlow] of [JournalUIState] that contains the
 * list of journal entries to be displayed in the UI.
 *
 * @property journalRepository A repository for performing journal-related operations.
 */
@HiltViewModel
class JournalViewModel @Inject constructor(
    private val journalRepository: JournalRepository
) : ViewModel() {

    // Mutable state flow for holding the UI state
    private val _uiState = MutableStateFlow(JournalUIState())
    // Publicly exposed state flow for UI to observe
    val uiState: StateFlow<JournalUIState> = _uiState.asStateFlow()

    init {
        // Collecting journal entries and updating the UI state
        viewModelScope.launch {
            journalRepository.getJournalEntries().collect { entries ->
                _uiState.update { currentState ->
                    currentState.copy(
                        entryList = entries.reversed() // Reverse to show the most recent entries first
                    )
                }
            }
        }
    }

    /**
     * Adds a new journal entry with the provided title and content.
     *
     * This method creates a new [JournalEntry] object with the given title and content,
     * and assigns a new UUID as the entry ID. The entry is then added to the repository.
     *
     * @param title The title of the journal entry.
     * @param content The content of the journal entry.
     */
    fun addEntry(title: String, content: String) = addEntry(
        JournalEntry(
            id = UUID.randomUUID().toString(),
            title = title,
            content = content,
            createdAt = Date.from(Instant.now()) // Sets the current time as the creation time
        )
    )

    /**
     * Adds or updates an existing journal entry.
     *
     * If the provided [JournalEntry] has an ID of "-1" (indicating it is a new entry),
     * a new UUID is assigned to the entry. It also ensures the creation time is set.
     * This entry is then passed to the repository to be added.
     *
     * @param entry The [JournalEntry] object to be added or updated.
     */
    fun addEntry(entry: JournalEntry) {
        var entry_ = entry
        if (entry_.id == "-1")
            entry_ = entry_.copy(id = UUID.randomUUID().toString())
        if (entry_.createdAt == null)
            entry_ = entry_.copy(createdAt = Date.from(Instant.now())) // Set the current time if not provided
        viewModelScope.launch { journalRepository.addJournalEntry(entry_) }
    }

    /**
     * Retrieves a journal entry by its ID.
     *
     * This method searches the list of journal entries in the UI state and returns the entry
     * that matches the provided ID.
     *
     * @param id The ID of the journal entry to be retrieved.
     * @return The [JournalEntry] object if found, or null if no matching entry is found.
     */
    fun getEntry(id: String): JournalEntry? {
        return _uiState.value.entryList.find {
            it.id == id
        }
    }

    /**
     * Deletes a journal entry by its ID.
     *
     * This method removes the journal entry with the specified ID from the repository.
     *
     * @param id The ID of the journal entry to be deleted.
     */
    fun deleteEntry(id: String) {
        viewModelScope.launch { journalRepository.deleteJournalEntry(id) }
    }

    /**
     * Deletes a journal entry by the entry object itself.
     *
     * This method calls [deleteEntry] with the ID of the provided [JournalEntry].
     *
     * @param entry The [JournalEntry] object to be deleted.
     */
    fun deleteEntry(entry: JournalEntry) = deleteEntry(entry.id)

    /**
     * Updates the title and content of an existing journal entry.
     *
     * This method creates a new [JournalEntry] object with the same ID as the existing one,
     * and the new title and content. It then passes the updated entry to the repository for saving.
     *
     * @param id The ID of the journal entry to be updated.
     * @param newTitle The new title for the journal entry.
     * @param newContent The new content for the journal entry.
     */
    fun updateEntry(id: String, newTitle: String, newContent: String) {
        val entry = JournalEntry(
            id = id,
            title = newTitle,
            content = newContent,
            createdAt = Date.from(Instant.now()) // Set the current time as the update time
        )

        viewModelScope.launch { journalRepository.updateMoodEntry(entry) }
    }
}
