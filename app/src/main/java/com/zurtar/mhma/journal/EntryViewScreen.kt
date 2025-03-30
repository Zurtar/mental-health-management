package com.zurtar.mhma.journal

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
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
import androidx.compose.material3.MaterialTheme
import com.zurtar.mhma.util.DefaultTopAppBar
import com.zurtar.mhma.util.JournalingTopAppBar

/*
The entry view screen is navigated to by the drop down menu on the journal item buttons
from the journaling screen. From this screen, there will be two buttons at the bottom, "Back"
and "Edit", back will popback to the journaling screen. Edit will navigate to the appropriate
Entry modification screen.
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
            JournalingTopAppBar(openDrawer = openDrawer)
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
    val entry = onGetEntry(id)

    var title by remember { mutableStateOf(entry?.title ?: "") }
    var content by remember { mutableStateOf(entry?.content ?: "") }

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