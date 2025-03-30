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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.window.Popup
import com.zurtar.mhma.util.DefaultTopAppBar
import java.text.SimpleDateFormat
import java.util.Locale
import kotlin.text.isNotBlank

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import java.util.Date



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
            onNavigateToChatList = onNavigateToChatList
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
    onNavigateToChatList: () -> Unit
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
                .background(MaterialTheme.colorScheme.onPrimaryContainer)
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Chatbot",
                fontSize = 30.sp,
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier
                    .padding(8.dp),
            )
            Button(
                onClick = onNavigateToChatList
            ) {
                Text(text = "Chat Log",
                    color = MaterialTheme.colorScheme.onPrimary
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
            if ((currentBranch == ChatBranch.ActionPlan && viewModel.getBranchStep() == 2) || (currentBranch == ChatBranch.SmartGoal && viewModel.getBranchStep() == 5)) {
                DatePickerFieldToModal()
            }
            else if (currentBranch != ChatBranch.Initial) {
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
                .background(MaterialTheme.colorScheme.onSecondaryContainer)
                .padding(8.dp)
        ) {
            Text(
                text = message.message,
                color = MaterialTheme.colorScheme.onSecondary
            )
            Text(
                text = formattedTime,
                color = MaterialTheme.colorScheme.onSecondary,
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
                .background(MaterialTheme.colorScheme.onTertiaryContainer)
                .padding(8.dp)
        ) {
            Text(
                text = message.message,
                color = MaterialTheme.colorScheme.onTertiary
            )
            Text(
                text = formattedTime,
                color = MaterialTheme.colorScheme.onTertiary,
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerDocked() {
    var showDatePicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()
    val selectedDate = datePickerState.selectedDateMillis?.let {
        convertMillisToDate(it)
    } ?: ""

    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        OutlinedTextField(
            value = selectedDate,
            onValueChange = { },
            label = { Text("Date for Completion") },
            readOnly = true,
            trailingIcon = {
                IconButton(onClick = { showDatePicker = !showDatePicker }) {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = "Select date"
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
        )

        if (showDatePicker) {
            Popup(
                onDismissRequest = { showDatePicker = false },
                alignment = Alignment.TopStart
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .offset(y = 64.dp)
                        .shadow(elevation = 4.dp)
                        .background(MaterialTheme.colorScheme.surface)
                        .padding(16.dp)
                ) {
                    DatePicker(
                        state = datePickerState,
                        showModeToggle = false
                    )
                }
            }
        }
    }
}

@Composable
fun DatePickerFieldToModal(modifier: Modifier = Modifier) {
    var selectedDate by remember { mutableStateOf<Long?>(null) }
    var showModal by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = selectedDate?.let { convertMillisToDate(it) } ?: "",
        onValueChange = { },
        label = { Text("Date to be completed") },
        placeholder = { Text("MM/DD/YYYY") },
        trailingIcon = {
            Icon(Icons.Default.DateRange, contentDescription = "Select date")
        },
        modifier = modifier
            .fillMaxWidth()
            .pointerInput(selectedDate) {
                awaitEachGesture {
                    // Modifier.clickable doesn't work for text fields, so we use Modifier.pointerInput
                    // in the Initial pass to observe events before the text field consumes them
                    // in the Main pass.
                    awaitFirstDown(pass = PointerEventPass.Initial)
                    val upEvent = waitForUpOrCancellation(pass = PointerEventPass.Initial)
                    if (upEvent != null) {
                        showModal = true
                    }
                }
            }
    )

    if (showModal) {
        DatePickerModal(
            onDateSelected = { selectedDate = it },
            onDismiss = { showModal = false }
        )
    }
}

fun convertMillisToDate(millis: Long): String {
    val formatter = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
    return formatter.format(Date(millis))
}

@OptIn(ExperimentalMaterial3Api::class)
// [START android_compose_components_datepicker_modal]
@Composable
fun DatePickerModal(
    onDateSelected: (Long?) -> Unit,
    onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState()

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                onDateSelected(datePickerState.selectedDateMillis)
                onDismiss()
            }) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}
