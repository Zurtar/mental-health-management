package com.zurtar.mhma

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
import androidx.compose.ui.graphics.Color
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
import androidx.compose.material3.Button
import androidx.compose.ui.text.style.TextAlign

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
    onNavigateToChatLog: (Int) -> Unit
) {
    val logList by viewModel.logList.observeAsState()
    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.onPrimary)
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Chat Logs",
                fontSize = 30.sp,
                color = Color.White,
                modifier = Modifier
                    .padding(8.dp),
            )
            Button(onClick = onNavigateToChatbot) {
                Text(text = "Chatbot")
            }
        }

        ChatListPageContent(
            modifier = Modifier
                .weight(1f)
                .fillMaxSize(),
            logList = logList,
            deleteLog = viewModel::deleteLog,
            onNavigateToChatLog = onNavigateToChatLog
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
    onNavigateToChatLog: (Int) -> Unit
) {
    Column(
        modifier = modifier
    ) {
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
            .background(MaterialTheme.colorScheme.onPrimary)
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
                color = Color.LightGray
            )
            Text(
                text = getBranchDisplayName(item.logType),
                fontSize = 20.sp,
                color = Color.White
            )
        }
        IconButton(onClick = { expanded = true }) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_arrow_drop_down_circle_24),
                contentDescription = "Options",
                tint = Color.White
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