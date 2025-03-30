package com.zurtar.mhma.chatbot

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.lifecycle.viewmodel.compose.viewModel
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
import com.zurtar.mhma.R
import com.zurtar.mhma.util.ChatListTopAppBar

/*
Main function for the ChatListPage, which is used to display a list of previously completed
chatLogs or cognitive behavioural therapy activities.

Relies on helper composables ChatListPage Content and LogItem (ChatListPageContent is used
for rendering the content of the page, while LogItem defines the composables that are actually being
listed on the page)
 */
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ChatListPage(
    modifier: Modifier = Modifier,
    viewModel: ChatbotViewModel = viewModel(),
    onNavigateToChatbot: () -> Unit,
    onNavigateToChatLog: (Int) -> Unit,
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

/*
Composable function used to display the contents of the ChatListPage.
 */
@Composable
private fun ChatListPageContent(
    modifier: Modifier = Modifier,
    logList: List<ChatLog>?,
    deleteLog: (Int) -> Unit,
    onNavigateToChatbot: () -> Unit,
    onNavigateToChatLog: (Int) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(10.dp)
    ){
        logList?.let {
            LazyColumn(
                content = {
                    itemsIndexed(it) { _: Int, item: ChatLog ->
                        LogItem(
                            item = item,
                            onDelete = { deleteLog(item.id) },
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

/*
Composable to create "card" for ChatLog object
 */
@Composable
fun LogItem(
    item: ChatLog,
    onDelete: () -> Unit,
    onNavigateToChatLog: (Int) -> Unit
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
                        onNavigateToChatLog(item.id)
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

/*
Helper function for the display of the different chat branches
on buttons
 */
fun getBranchDisplayName(logType: ChatBranch): String {
    return when (logType) {
        ChatBranch.SmartGoal -> "Smart Goal "
        ChatBranch.ThoughtRecord -> "Thought Record"
        ChatBranch.AnxietyExploration -> "Anxiety Exploration"
        ChatBranch.ActionPlan -> "Action Plan"
        ChatBranch.CBTModeling -> "CBT Modeling"
        else -> "Error: Unknown log type"
    }
}