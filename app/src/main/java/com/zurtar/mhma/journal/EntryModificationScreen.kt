package com.zurtar.mhma.journal

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

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
    viewModel: JournalViewModel = viewModel(),
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
        Button(onClick = {
            viewModel.addEntry(title, content)
            onNavigateBack()
        }) {
            Text("Save Entry")
        }
    }
}

@Composable
fun EntryModificationScreen(
    modifier: Modifier = Modifier,
    viewModel: JournalViewModel = viewModel(),
    id: Int,
    onNavigateBack: () -> Unit
) {
    var title by remember { mutableStateOf(viewModel.getEntry(id)?.title ?: "") }
    var content by remember { mutableStateOf(viewModel.getEntry(id)?.content ?: "") }

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
        Button(onClick = {
            viewModel.editEntry(id, title, content)
            onNavigateBack()
        }) {
            Text("Save Entry")
        }
    }
}