package com.zurtar.mhma.journal

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.zurtar.mhma.R
import com.zurtar.mhma.util.JournalingTopAppBar
import com.zurtar.mhma.data.JournalEntry
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.Locale

/**
This is the home page for the Journaling section. It is composed of two functions:
JournalingPage, and EntryItem. EntryItem is used to to create composables of any
entries currently in existence, which JournalingPage then displays to the user in
a lazy column.
 **/

/**
 * Displays the home page for the Journaling section, consisting of a top app bar,
 * a floating action button to add a new journal entry, and a list of existing journal entries.
 *
 * @param modifier Modifier to be applied to the composable.
 * @param viewModel The ViewModel providing the UI state.
 * @param openDrawer A lambda to open the navigation drawer.
 * @param onNavigateToEntryCreation A lambda to navigate to the entry creation screen.
 * @param onNavigateToEntryView A lambda to navigate to a detailed view of a journal entry.
 */
@Composable
fun JournalingScreen(
    modifier: Modifier = Modifier,
    viewModel: JournalViewModel = hiltViewModel(),
    openDrawer: () -> Unit,
    onNavigateToEntryCreation: () -> Unit,
    onNavigateToEntryView: (String) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            JournalingTopAppBar(openDrawer = openDrawer)
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onNavigateToEntryCreation() }
            ) {
                Icon(Icons.Filled.Add, "Add new entry")
            }
        }
    ) { innerPadding ->
        JournalingScreenContent(
            modifier = modifier
                .padding(innerPadding)
                .fillMaxSize(),
            entryList = uiState.entryList,
            onNavigateToEntryView = onNavigateToEntryView,
            deleteEntry = viewModel::deleteEntry,
        )
    }


}

@Composable
private fun JournalingScreenContent(
    modifier: Modifier = Modifier,
    entryList: List<JournalEntry>?,
    onNavigateToEntryView: (String) -> Unit,
    deleteEntry: (String) -> Unit
) {
    Column(
        modifier = modifier
            .padding(10.dp)

    ) {
        entryList?.let {
            LazyColumn(
                content = {
                    itemsIndexed(it) { index: Int, item: JournalEntry ->
                        EntryItem(
                            item = item,
                            onDelete = { deleteEntry(entryList.get(index).id) },
                            onView = { onNavigateToEntryView(entryList.get(index).id) })
                    }
                }
            )
        } ?: Text(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            text = "No entries found",
            fontSize = 16.sp
        )

    }
}

@Composable
fun EntryItem(
    item: JournalEntry,
    onDelete: () -> Unit,
    onView: () -> Unit
) {
    if (item.createdAt == null)
        return

    var expanded by remember { mutableStateOf(false) }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.onPrimaryContainer)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically

    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(8.dp)
        ) {
            Text(
                text = SimpleDateFormat("HH:mm:aa, dd/mm", Locale.ENGLISH).format(
                    item.createdAt ?: LocalDateTime.MIN
                ),
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onPrimary
            )
            Text(
                text = item.title,
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.onPrimary
            )
            Text(
                text = item.content.take(200),
                fontSize = 10.sp,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
        IconButton(onClick = { expanded = true }) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_arrow_drop_down_circle_24),
                contentDescription = "Options",
                tint = MaterialTheme.colorScheme.onPrimary
            )
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                DropdownMenuItem(
                    text = { Text("View") },
                    onClick = {
                        onView()
                        expanded = false
                    }
                )
                DropdownMenuItem(
                    text = { Text("Delete") },
                    onClick = {
                        onDelete()
                        expanded = false
                    }
                )
            }
        }
    }

}