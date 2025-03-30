@file:OptIn(ExperimentalMaterial3Api::class)

package com.zurtar.mhma.util

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DefaultTopAppBar(
    openDrawer: () -> Unit
) {
    TopAppBar(
        title = { Text("Mental-Health Tracker") },
        colors = topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary
        ),
        navigationIcon = {
            IconButton(onClick = openDrawer) {
                Icon(Icons.Default.Menu, contentDescription = "Menu")
            }
        },
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun ChatbotTopAppBar(
    openDrawer: () -> Unit,
    onNavigateToChatList: () -> Unit
) {
    TopAppBar(
        title = { Text("Chat Bot") },
        colors = topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary
        ),
        navigationIcon = {
            IconButton(onClick = openDrawer) {
                Icon(Icons.Default.Menu, contentDescription = "Menu")
            }
        },
        actions = {
            Button(
                onClick = onNavigateToChatList
            ) {
                Text(text = "Chat Log",
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        },
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun ChatListTopAppBar(
    openDrawer: () -> Unit,
    onNavigateToChatbot: () -> Unit
) {
    TopAppBar(
        title = { Text("Chat List") },
        colors = topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary
        ),
        navigationIcon = {
            IconButton(onClick = openDrawer) {
                Icon(Icons.Default.Menu, contentDescription = "Menu")
            }
        },
        actions = {
            Button(
                onClick = onNavigateToChatbot
            ) {
                Text(text = "Chat Bot",
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        },
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun ChatLogTopAppBar(
    openDrawer: () -> Unit,
    onNavigateBack: () -> Unit
) {
    TopAppBar(
        title = { Text("Chat Log") },
        colors = topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary
        ),
        navigationIcon = {
            IconButton(onClick = openDrawer) {
                Icon(Icons.Default.Menu, contentDescription = "Menu")
            }
        },
        actions = {
            Button(
                onClick = onNavigateBack
            ) {
                Text(text = "Back to List",
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        },
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun JournalingTopAppBar(
    openDrawer: () -> Unit,
) {
    TopAppBar(
        title = { Text("Journal") },
        colors = topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary
        ),
        navigationIcon = {
            IconButton(onClick = openDrawer) {
                Icon(Icons.Default.Menu, contentDescription = "Menu")
            }
        },
        modifier = Modifier.fillMaxWidth()
    )
}