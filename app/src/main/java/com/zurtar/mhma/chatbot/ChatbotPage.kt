package com.zurtar.mhma.chatbot

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.zurtar.mhma.util.DefaultTopAppBar
import java.text.SimpleDateFormat
import java.util.Locale
import kotlin.text.isNotBlank



@Composable
fun ChatbotPage(
    modifier: Modifier = Modifier,
    viewModel: ChatbotViewModel = viewModel(),
    onNavigateToChatList: () -> Unit,
    openDrawer: () -> Unit
) {
    Scaffold(modifier = modifier.fillMaxSize(),
        topBar = {
            DefaultTopAppBar(openDrawer = openDrawer)
        }
    ) { innerPadding ->
        ChatbotPageContent(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            viewModel = viewModel,
            onNavigateToChatList = onNavigateToChatList,
        )
    }
}

/*
Composable function for the main Chatbot page. It relies on two additional composables and
a helper function.

The two other composables it uses are UserMessageItem and BotMessageItem. These get presented in
a lazy column, and are called on based on the Sender (Bot or User) parameter of the message being sent.
 */
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ChatbotPageContent(
    modifier: Modifier = Modifier,
    viewModel: ChatbotViewModel = viewModel(),
    onNavigateToChatList: () -> Unit,
) {
    var userMessage by remember { mutableStateOf("") }
    val messages by viewModel.allMessages.observeAsState(initial = emptyList())
    val listState = rememberLazyListState()

    val currentBranch = viewModel.getCurrentBranch()
    val branchList = viewModel.getBranchList()

    LaunchedEffect(messages.size) {
        if (messages.isNotEmpty()) {
            listState.animateScrollToItem(messages.size - 1)
        }
    }
    Column(
        modifier = modifier
            .fillMaxSize(),
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
                text = "Chatbot",
                fontSize = 30.sp,
                color = Color.White,
                modifier = Modifier
                    .padding(8.dp),
            )
            Button(
                onClick = onNavigateToChatList
            ) {
                Text(text = "Chat Log",
                    color = Color.Black
                )
            }
        }
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.Bottom
        ) {
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                state = listState,
                reverseLayout = false
            ) {
                items(messages) { message ->
                    when (message.sender) {
                        Sender.User -> UserMessageItem(message)
                        Sender.Bot -> BotMessageItem(message)
                    }
                }
                if (currentBranch == ChatBranch.Initial) {
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 16.dp),
                            horizontalAlignment = Alignment.End,
                            verticalArrangement = Arrangement.Bottom
                        ) {
                            branchList.forEach { branch ->
                                Button(
                                    onClick = {
                                        viewModel.sendMessage(getBranchSelection(branch))
                                    },
                                    modifier = Modifier
                                        .padding(bottom = 8.dp)
                                ) {
                                    Text(getBranchSelection(branch))
                                }
                            }
                        }
                    }
                }
                if (currentBranch == ChatBranch.Explanation) {
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 16.dp),
                            horizontalAlignment = Alignment.End,
                            verticalArrangement = Arrangement.Bottom
                        ) {
                            branchList.filter { it != ChatBranch.Explanation }.forEach { branch ->
                                Button(
                                    onClick = {
                                        viewModel.sendMessage(getBranchSelection(branch))
                                    },
                                    modifier = Modifier
                                        .padding(bottom = 8.dp)
                                ) {
                                    Text(getBranchSelection(branch))
                                }
                            }
                            Button(
                                onClick = {
                                    viewModel.sendMessage("I'd like to do an activity now.")
                                },
                                modifier = Modifier
                                    .padding(bottom = 8.dp)
                            ) {
                                Text("I'd like to do an activity now.")
                            }
                        }
                    }
                }
            }
            if (currentBranch != ChatBranch.Initial) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                        .imePadding(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = userMessage,
                        onValueChange = { userMessage = it },
                        modifier = Modifier.weight(1f),
                        label = { Text("Enter message") }
                    )
                    Button(
                        onClick = {
                            if (userMessage.isNotBlank()) {
                                viewModel.sendMessage(userMessage)
                                userMessage = ""
                            }
                        },
                        modifier = Modifier.padding(start = 8.dp)
                    ) {
                        Text("Send")
                    }
                }
            }
        }
    }
}

@Composable
fun UserMessageItem(message: ChatMessage) {
    val formattedTime = SimpleDateFormat("HH:mm", Locale.getDefault()).format(message.createdAt)
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.End
    ) {
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(MaterialTheme.colorScheme.primary)
                .padding(8.dp)
        ) {
            Text(text = message.message,
                color = Color.White)
            Text(
                text = formattedTime,
                color = Color.White,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.align(Alignment.End)
            )
        }
    }
}

@Composable
fun BotMessageItem(message: ChatMessage) {
    val formattedTime = SimpleDateFormat("HH:mm", Locale.getDefault()).format(message.createdAt)
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.Start
    ) {
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(MaterialTheme.colorScheme.onPrimary)
                .padding(8.dp)
        ) {
            Text(
                text = message.message,
                color = Color.White)
            Text(
                text = formattedTime,
                color = Color.White,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.align(Alignment.End)
            )
        }
    }
}

/*
Used for branch selection from buttons. Determines what string is passed
when button is pressed.
*/
fun getBranchSelection(logType: ChatBranch): String {
    return when (logType) {
        ChatBranch.SmartGoal -> "Smart Goal "
        ChatBranch.ThoughtRecord -> "Thought Record"
        ChatBranch.AnxietyExploration -> "Anxiety Exploration"
        ChatBranch.ActionPlan -> "Action Plan"
        ChatBranch.CBTModeling -> "CBT Modeling"
        ChatBranch.Explanation -> "What are these different activities for?"
        else -> "Error: Unknown log type"
    }
}