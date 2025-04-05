package com.zurtar.mhma.journal

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.zurtar.mhma.data.JournalEntry
import com.zurtar.mhma.util.DefaultTopAppBar
import com.zurtar.mhma.util.JournalingTopAppBar
import kotlinx.coroutines.coroutineScope

/**
 * Composable for the EntryModification page. It can be used for either creating a new entry or editing an existing one.
 * If no entry ID is provided, the fields will be empty for a new entry. If an ID is provided, the fields will be populated
 * with the data from the existing entry.
 *
 * @param modifier Modifier to be applied to the composable.
 * @param viewModel The ViewModel providing the UI state.
 * @param id The ID of the journal entry to modify, or null for new entry creation.
 * @param openDrawer A lambda to open the navigation drawer.
 * @param onNavigateBack A lambda to navigate back to the previous screen.
 */
@Composable
fun EntryModificationScreen(
    modifier: Modifier = Modifier,
    viewModel: JournalViewModel = hiltViewModel(),
    id: String? = null,
    openDrawer: () -> Unit,
    onNavigateBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val list = uiState.entryList

    val entry = uiState.entryList.find {
        it.id == id
    }
    Scaffold(modifier = modifier.fillMaxSize(),
        topBar = {
            JournalingTopAppBar(openDrawer = openDrawer)
        }
    ) { innerPadding ->
        if (id == null) {
            EntryModificationScreenContent(
                modifier = modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
                onAddEntry = viewModel::addEntry,
                onNavigateBack = onNavigateBack,
            )
            return@Scaffold
        }

        EntryModificationScreenContent(
            modifier = modifier
                .padding(innerPadding)
                .fillMaxSize(),
            entry = entry,
            onEditEntry = viewModel::updateEntry,
            onNavigateBack = onNavigateBack,
        )
    }
}

/**
 * Composable for the content of the EntryModification screen when adding a new entry. Displays text fields for the title
 * and content of the journal entry.
 *
 * @param modifier Modifier to be applied to the composable.
 * @param onAddEntry A lambda to handle the creation of a new entry.
 * @param onNavigateBack A lambda to handle navigation back action.
 */
@Composable
private fun EntryModificationScreenContent(
    modifier: Modifier = Modifier,
    onAddEntry: (String, String) -> Unit,
    onNavigateBack: () -> Unit
) {
    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }

    Column(modifier = modifier.padding(16.dp)) {
        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Write journal entry title here...") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = content,
            onValueChange = { content = it },
            label = { Text("Write your entry here...") },
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            horizontalArrangement = Arrangement
                .spacedBy(8.dp)

        ) {
            Button(
                onClick = onNavigateBack,
                modifier = Modifier.weight(1f)
            ) {
                Text("Cancel")
            }
            Button(
                onClick = {
                    onAddEntry(title, content)
                    onNavigateBack()
                },
                modifier = Modifier.weight(1f)
            ) {
                Text("Save")
            }
        }
    }
}

/**
 * Composable for the content of the Journal Entry when editing an existing entry. Displays text fields for the title
 * and content, pre-filled with the existing entry’s data.
 *
 * @param modifier Modifier to be applied to the composable.
 * @param entry The journal entry to edit.
 * @param onEditEntry A lambda to handle the editing of an existing entry.
 * @param onNavigateBack A lambda to handle navigation back action.
 */
@Composable
private fun EntryModificationScreenContent(
    modifier: Modifier = Modifier,
    entry: JournalEntry?,
    onEditEntry: (String, String, String) -> Unit,
    onNavigateBack: () -> Unit
) {

    var entry_ = entry?.copy() ?: JournalEntry()
    var title by remember { mutableStateOf(entry_.title) }
    var content by remember { mutableStateOf(entry_.content) }

    Column(modifier = modifier.padding(16.dp)) {
        OutlinedTextField(
            value = if (title.isEmpty()) entry_.title else title,
            onValueChange = { title =  it },
            label = { Text("Write journal entry title here...") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = if (content.isEmpty()) entry_.content else content,
            onValueChange = { content = it },
            label = { Text("Write your entry here...") },
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            horizontalArrangement = Arrangement
                .spacedBy(8.dp)

        ) {
            Button(
                onClick = onNavigateBack,
                modifier = Modifier.weight(1f)
            ) {
                Text("Cancel")
            }
            Button(onClick = {
                onEditEntry(entry_.id, title, content)
                onNavigateBack()
            }) {
                Text("Save Entry")
            }
        }
    }
}