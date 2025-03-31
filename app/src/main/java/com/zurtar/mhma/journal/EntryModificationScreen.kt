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

/*
EntryModification page can be called upon in two different ways: a version where it is
given an id number, and one where it is not given an id number. If it is not given an
id, then title anc content boxes will be empty: this is used for new entry creation. If
it is given an id, then title and content boxes will be filled from the associated
entry's data: this is used for entry editing.
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

    val entry = list.find {
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
                onClick = {onAddEntry(title, content)
                    onNavigateBack()},
                modifier = Modifier.weight(1f)
            ) {
                Text("Save")
            }
        }
    }
}

@Composable
private fun EntryModificationScreenContent(
    modifier: Modifier = Modifier,
    entry: JournalEntry?,
    onEditEntry: (String, String, String) -> Unit,
    onNavigateBack: () -> Unit
) {
    var title by remember { mutableStateOf(entry?.title ?: "") }
    var content by remember { mutableStateOf(entry?.content ?: "") }

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
            Button(onClick = {
                onEditEntry(entry?.id ?: "null", title, content)
                onNavigateBack()
            }) {
                Text("Save Entry")
            }
        }
    }
}