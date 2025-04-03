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

/**
 * TODO:
 * - Theres a lot of repeated functionality, a generic implementation with a text and action parameter could
 *   provide the functionality with a lot less duplicated code! not a priority. -- Ethan
 */

/**
 * Composable function that displays the top app bar for the home screen.
 *
 * This app bar includes:
 * - A title "VibeCheck: Home".
 * - A navigation menu icon that invokes [openDrawer] when clicked.
 *
 * Styled using the primary color scheme for visual consistency.
 *
 * @param openDrawer Lambda function to open the navigation drawer when the menu icon is clicked.
 */
@Composable
fun DefaultTopAppBar(
    openDrawer: () -> Unit
) {
    TopAppBar(
        title = { Text("VibeCheck: Home") },
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

/**
 * Composable function for the top app bar on the chatbot screen.
 *
 * This app bar includes:
 * - A title "Chat With Zeke".
 * - A menu icon that invokes [openDrawer].
 * - An action button that navigates to the chat list screen using [onNavigateToChatList].
 *
 * @param openDrawer Lambda function to open the navigation drawer when the menu icon is clicked.
 * @param onNavigateToChatList Lambda function to navigate to the chat list screen when the button is clicked.
 */
@Composable
fun ChatbotTopAppBar(
    openDrawer: () -> Unit,
    onNavigateToChatList: () -> Unit
) {
    TopAppBar(
        title = { Text("Chat With Zeke") },
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
            Button(onClick = onNavigateToChatList) {
                Text("Chat List", color = MaterialTheme.colorScheme.onPrimary)
            }
        },
        modifier = Modifier.fillMaxWidth()
    )
}

/**
 * Composable function for the top app bar on the chat list screen.
 *
 * This app bar includes:
 * - A title "Chat List".
 * - A menu icon that invokes [openDrawer].
 * - An action button that navigates back to the chatbot screen using [onNavigateToChatbot].
 *
 * @param openDrawer Lambda function to open the navigation drawer when the menu icon is clicked.
 * @param onNavigateToChatbot Lambda function to navigate to the chatbot screen when the button is clicked.
 */
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
            Button(onClick = onNavigateToChatbot) {
                Text("Chat", color = MaterialTheme.colorScheme.onPrimary)
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
        title = { Text("Zeke Log") },
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
                Text(
                    text = "Back to List",
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

@Composable
fun MoodEvaluationTopAppBar(
    openDrawer: () -> Unit,
) {
    TopAppBar(
        title = { Text("Mood Evaluation") },
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
fun AnalyticsTopAppBar(
    openDrawer: () -> Unit,
) {
    TopAppBar(
        title = { Text("Analytics") },
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