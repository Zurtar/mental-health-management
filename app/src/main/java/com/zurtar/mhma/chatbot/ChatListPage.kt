package com.zurtar.mhma.chatbot

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.painterResource
import java.text.SimpleDateFormat
import java.util.Locale
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Scaffold
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import com.zurtar.mhma.R
import com.zurtar.mhma.data.ChatLog
import com.zurtar.mhma.util.ChatListTopAppBar

/**
 * Main function for the ChatListPage, used to display a list of previously completed
 * chatLogs or cognitive behavioural therapy activities.
 *
 * Relies on helper composables ChatListPageContent and LogItem. ChatListPageContent
 * is used for rendering the content of the page, while LogItem defines the composables
 * that are listed on the page.
 *
 * @param modifier Optional modifier for the composable.
 * @param viewModel The view model for accessing the list of logs.
 * @param onNavigateToChatbot Callback function for navigating to the chatbot page.
 * @param onNavigateToChatLog Callback function for navigating to a specific chat log.
 * @param openDrawer Callback function for opening the navigation drawer.
 */
@Composable
fun ChatListPage(
    modifier: Modifier = Modifier,
    viewModel: ChatbotViewModel = hiltViewModel(),
    onNavigateToChatbot: () -> Unit,
    onNavigateToChatLog: (String) -> Unit,
    openDrawer: () -> Unit
) {
    val logList by viewModel.logList.observeAsState()

    Scaffold(modifier = modifier.fillMaxSize(),
        topBar = {
            ChatListTopAppBar(openDrawer = openDrawer, onNavigateToChatbot)
        }
    ) { innerPadding ->
        ChatListPageContent(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            logList = logList,
            deleteLog = viewModel::deleteLog,
            onNavigateToChatLog = onNavigateToChatLog,
            onNavigateToChatbot = onNavigateToChatbot
        )
    }
}

/**
 * Composable function used to display the contents of the ChatListPage.
 *
 * @param modifier Optional modifier for the composable.
 * @param logList List of ChatLog objects to display.
 * @param deleteLog Callback function to delete a log.
 * @param onNavigateToChatbot Callback function for navigating to the chatbot page.
 * @param onNavigateToChatLog Callback function for navigating to a specific chat log.
 */
@Composable
private fun ChatListPageContent(
    modifier: Modifier = Modifier,
    logList: List<ChatLog>?,
    deleteLog: (String) -> Unit,
    onNavigateToChatbot: () -> Unit,
    onNavigateToChatLog: (String) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        logList?.let {
            LazyColumn(
                content = {
                    itemsIndexed(it) { _: Int, item: ChatLog ->
                        LogItem(
                            item = item,
                            onDelete = { item.id?.let { it1 -> deleteLog(it1) } },
                            onNavigateToChatLog = onNavigateToChatLog
                        )
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

/**
 * Composable to create a "card" for the ChatLog object.
 *
 * @param item The ChatLog item to display.
 * @param onDelete Callback function to delete the log.
 * @param onNavigateToChatLog Callback function to navigate to a specific chat log.
 */
@Composable
fun LogItem(
    item: ChatLog,
    onDelete: () -> Unit,
    onNavigateToChatLog: (String) -> Unit
) {

    var expanded by remember { mutableStateOf(false) }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.onSecondaryContainer)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically

    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(8.dp)
        ) {
            Text(
                text = SimpleDateFormat("HH:mm:aa, MMMM dd", Locale.ENGLISH).format(item.createdAt),
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSecondary
            )
            Text(
                text = getBranchDisplayName(item.logType),
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.onSecondary
            )
        }
        IconButton(onClick = { expanded = true }) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_arrow_drop_down_circle_24),
                contentDescription = "Options",
                tint = MaterialTheme.colorScheme.onSecondary
            )
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                DropdownMenuItem(
                    text = { Text("View") },
                    onClick = {
                        item.id?.let { onNavigateToChatLog(it) }
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

/**
 * Helper function for displaying the different chat branches on buttons.
 *
 * @param logType The chat branch type for which the display name is generated.
 * @return The display name of the chat branch.
 */
fun getBranchDisplayName(logType: ChatBranch?): String {
    return when (logType) {
        ChatBranch.SmartGoal -> "Smart Goal "
        ChatBranch.ThoughtRecord -> "Thought Record"
        ChatBranch.AnxietyExploration -> "Anxiety Exploration"
        ChatBranch.ActionPlan -> "Action Plan"
        ChatBranch.CBTModeling -> "CBT Modeling"
        null -> "Error: Unknown log type"
        else -> "Error: Unknown log type"
    }
}