package com.zurtar.mhma.journal

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.zurtar.mhma.data.JournalEntry
import com.zurtar.mhma.util.JournalingTopAppBar

/*
The entry view screen is navigated to by the drop down menu on the journal item buttons
from the journaling screen. From this screen, there will be two buttons at the bottom, "Back"
and "Edit", back will popback to the journaling screen. Edit will navigate to the appropriate
Entry modification screen.
 */


/**
 * Composable, Displays the entry view screen, where users can see the details of a selected journal entry.
 * It has options to navigate back to the journaling screen or edit the current entry.
 *
 * @param modifier Modifier to be applied to the composable.
 * @param viewModel The [ViewModel] providing the UI state.
 * @param id The ID of the `JounralEntry` to view.
 * @param openDrawer A lambda to open the navigation drawer.
 * @param onNavigateBack A lambda to navigate back to the journaling screen.
 * @param onNavigateToEntryEdit A lambda to navigate to the entry edit screen.
 */
@Composable
fun EntryViewScreen(
    modifier: Modifier = Modifier,
    viewModel: JournalViewModel = hiltViewModel(),
    id: String? = null,
    openDrawer: () -> Unit,
    onNavigateBack: () -> Unit,
    onNavigateToEntryEdit: (String) -> Unit
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
            Text("Error retrieving entry")
            return@Scaffold
        }

        EntryViewScreenContent(
            modifier = modifier
                .padding(innerPadding)
                .fillMaxSize(),
            entry = entry,
            onNavigateBack = onNavigateBack,
            onNavigateToEntryEdit = onNavigateToEntryEdit
        )
    }
}

/**
 * Composable, Displays the content of the entry view screen, showing the title and content of the selected entry.
 * Also provides buttons to navigate back or edit the entry.
 *
 * @param modifier Modifier to be applied to the composable.
 * @param entry The journal entry to display.
 * @param onNavigateBack A lambda to handle the navigation back action.
 * @param onNavigateToEntryEdit A lambda to navigate to the entry edit screen.
 */
@Composable
private fun EntryViewScreenContent(
    modifier: Modifier = Modifier,
    entry: JournalEntry?,
    onNavigateBack: () -> Unit,
    onNavigateToEntryEdit: (String) -> Unit
) {
    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column() {
            Text(
                text = entry?.title ?: "Please Try Again",
                style = MaterialTheme.typography.headlineMedium,
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = entry?.content ?: "",
                style = MaterialTheme.typography.bodyMedium,
            )
        }
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
                onClick = { onNavigateToEntryEdit(entry?.id ?: "null") },
                modifier = Modifier.weight(1f)
            ) {
                Text("Edit")
            }
        }
    }
}