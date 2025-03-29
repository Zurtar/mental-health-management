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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import com.zurtar.mhma.util.DefaultTopAppBar

/*
EntryModification page can be called upon in two different ways: a version where it is
given an id number, and one where it is not given an id number. If it is not given an
id, then title anc content boxes will be empty: this is used for new entry creation. If
it is given an id, then title and content boxes will be filled from the associated
entry's data: this is used for entry editing.
 */
@Composable
fun EntryViewScreen(
    modifier: Modifier = Modifier,
    viewModel: JournalViewModel = viewModel(),
    id: Int? = null,
    openDrawer: () -> Unit,
    onNavigateBack: () -> Unit,
    onNavigateToEntryEdit: (Int) -> Unit

) {
    Scaffold(modifier = modifier.fillMaxSize(),
        topBar = {
            DefaultTopAppBar(openDrawer = openDrawer)
        }
    ) { innerPadding ->
        if (id != null) {
            EntryViewScreenContent(
                modifier = modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
                id = id,
                onGetEntry = viewModel::getEntry,
                onEditEntry = viewModel::editEntry,
                onNavigateBack = onNavigateBack,
                onNavigateToEntryEdit = onNavigateToEntryEdit
            )
        }
        else {
            Text("Error retrieving entry")
            return@Scaffold
        }
    }
}

@Composable
private fun EntryViewScreenContent(
    modifier: Modifier = Modifier,
    id: Int,
    onGetEntry: (Int) -> JournalEntry?,
    onEditEntry: (Int, String, String) -> Unit,
    onNavigateBack: () -> Unit,
    onNavigateToEntryEdit: (Int) -> Unit
) {
    var title = onGetEntry(id)?.title ?: ""
    var content = onGetEntry(id)?.content ?: ""

    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxSize()) {
        Text(
            text = title,
            style = MaterialTheme.typography.headlineMedium,
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = content,
            style = MaterialTheme.typography.bodyMedium,
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
                Text("Back")
            }
            Button(
                onClick = {onNavigateToEntryEdit(id)},
                modifier = Modifier.weight(1f)
            ) {
                Text("Edit")
            }
        }
    }
}