package com.zurtar.mhma.chatbot

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.zurtar.mhma.data.ChatLog
import com.zurtar.mhma.data.ChatMessage
import com.zurtar.mhma.util.ChatLogTopAppBar
import java.text.SimpleDateFormat
import java.util.Locale

/**
 * Composable, displays a ChatLog page that shows previously saved/completed cognitive behavioral therapy activities.
 * This page includes the top app bar and content of the log.
 *
 * @param modifier Modifier to be applied to the root composable
 * @param logId ID of the log to be displayed
 * @param onNavigateBack Lambda to handle the navigation back
 * @param viewModel ViewModel to fetch the log data
 * @param openDrawer Lambda to open the navigation drawer
 */
@Composable
fun ChatLogPage(
    modifier: Modifier = Modifier,
    logId: String,
    onNavigateBack: () -> Unit,
    viewModel: ChatbotViewModel = hiltViewModel(),
    openDrawer: () -> Unit,
) {
    val logList by viewModel.logList.observeAsState()
    val log = logList?.find {
        it.id == logId
    }
    Scaffold(modifier = modifier.fillMaxSize(),
        topBar = {
            ChatLogTopAppBar(openDrawer = openDrawer, onNavigateBack)
        }
    ) { innerPadding ->
        ChatLogPageContent(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            log = log,
            onNavigateBack = onNavigateBack,
        )
    }
}


/**
Composable function used to display previously saved/completed cognitive behavioural
therapy activities. Recreates the view the user would have originally seen of of in the
ChatbotPage.

Relies on two Composable functions for displaying content: UserMessageItemReconstruct and BotMessageItemReconstruct
to regenerate the view.

Title of the log is created using the helper function getLogTypeDisplayName

 *For the actual logic of the contents of the bot messages, handleBotMessage function is used in
 *tandem with various other handle..Branch functions*
 */


/**
 * Composes the content of the chat log page by displaying the log's details.
 *
 * @param modifier Modifier to be applied to the content
 * @param log Log data to be displayed
 * @param onNavigateBack Lambda to handle navigation back
 */
@Composable
fun ChatLogPageContent(
    modifier: Modifier = Modifier,
    log: ChatLog?,
    onNavigateBack: () -> Unit,
) {
    if (log == null) {
        Text("sad")
        return
    }

    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            log.let { log ->
                Text(
                    text = getLogTypeDisplayName(log.logType),
                    fontSize = 20.sp,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(
                    text = "Created At: ${
                        log.createdAt?.let {
                            SimpleDateFormat(
                                "HH:mm:aa, MMMM dd",
                                Locale.ENGLISH
                            ).format(it)
                        }
                    }",
                    fontSize = 14.sp,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                if (log.toBeCompleted != null) {
                    Text(
                        text = "To be completed At: ${
                            log.toBeCompleted?.let {
                                SimpleDateFormat(
                                    "EEEE, MMMM dd",
                                    Locale.getDefault()
                                ).format(it)
                            }
                        }",
                        fontSize = 14.sp,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                }

                for ((index, userMessage) in log.content.withIndex()) {
                    if (log.logType != null) {
                        BotMessageItemReconstruct(branch = log.logType!!, messageNumber = index)
                        UserMessageItemReconstruct(message = userMessage)
                    }
                }
            }
        }
    }
}

/**
 * Displays a user's message in the chat log with a timestamp.
 * The message is shown on the right side of the screen.
 *
 * @param message The chat message data to be displayed
 */
@Composable
fun UserMessageItemReconstruct(message: ChatMessage) {
    val formattedTime = SimpleDateFormat("HH:mm", Locale.getDefault()).format(message.createdAt)
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp),
        horizontalArrangement = Arrangement.End
    ) {
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(MaterialTheme.colorScheme.onSecondaryContainer)
                .padding(8.dp)
                .width(300.dp)
        ) {
            Text(
                text = message.message,
                color = MaterialTheme.colorScheme.onSecondary
            )
            Text(
                text = formattedTime,
                color = Color.White,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.align(Alignment.End)
            )
        }
    }
}

/**
 * Displays the bot's message in the chat log, based on the provided branch and message number.
 * The message is shown on the left side of the screen.
 *
 * @param branch The branch of the chat log to handle the bot's message
 * @param messageNumber The number of the message to be displayed
 */
@Composable
fun BotMessageItemReconstruct(branch: ChatBranch, messageNumber: Int) {
    val botMessage = handleBotMessage(branch, messageNumber)
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
                .width(300.dp)
        ) {
            Text(
                text = botMessage,
                color = MaterialTheme.colorScheme.onTertiary
            )
        }
    }
}


/**
 * Determines the appropriate bot message based on the branch and message number.
 * Each branch has specific messages to be shown depending on the current message number.
 *
 * @param branch The current chat branch type to determine the message.
 * @param messageNumber The index of the message to be displayed.
 * @return The appropriate message for the bot based on the given branch and message number.
 */
private fun handleBotMessage(branch: ChatBranch, messageNumber: Int): String {
    return when (branch) {
        ChatBranch.SmartGoal -> handleSmartGoalItem(messageNumber)
        ChatBranch.ThoughtRecord -> handleThoughtRecordItem(messageNumber)
        ChatBranch.AnxietyExploration -> handleAnxietyExplorationItem(messageNumber)
        ChatBranch.ActionPlan -> handleActionPlanItem(messageNumber)
        ChatBranch.CBTModeling -> handleCBTModelingItem(messageNumber)
        else -> "No message found for this branch"
    }
}

/**
 * Returns the display name for a given log type.
 *
 * @param logType The type of log (branch) to display.
 * @return The display name for the log type.
 */
fun getLogTypeDisplayName(logType: ChatBranch?): String {
    return when (logType) {
        ChatBranch.SmartGoal -> "Activity Type: Smart Goal"
        ChatBranch.ThoughtRecord -> "Activity Type: Thought Record"
        ChatBranch.AnxietyExploration -> "Activity Type: Anxiety Exploration"
        ChatBranch.ActionPlan -> "Activity Type: Action Plan"
        ChatBranch.CBTModeling -> "Activity Type: CBT Modeling"
        null -> "Error: Unknown log type"
        else -> "Error: Unknown log type"
    }
}

/**
 * Returns the bot's message for the Smart Goal activity type.
 *
 * @param messageNumber The index of the message to be displayed.
 * @return The message for the Smart Goal activity.
 */
private fun handleSmartGoalItem(messageNumber: Int): String {
    return when (messageNumber) {
        0 -> "What is it that you would like to do? Try to be as specific as you can be, to help make your goal as concrete as possible."
        1 -> "How will you measure your progress towards achieving your goal?"
        2 -> "Is this goal attainable? What will you need to do to complete it?"
        3 -> "Why is this goal relevant to you? What makes it important for you?"
        4 -> "On what day do you want to complete this goal by?"
        else -> ""
    }
}

/**
 * Returns the bot's message for the Thought Record activity type.
 *
 * @param messageNumber The index of the message to be displayed.
 * @return The message for the Thought Record activity.
 */
private fun handleThoughtRecordItem(messageNumber: Int): String {
    return when (messageNumber) {
        0 -> "Lets start by discussing what event led to the negative feelings. Could you please describe the situation to me?"
        1 -> "And how would you describe the emotional experiences you had in response to the event? How intense were those experiences?"
        2 -> "Next, lets explore the immediate thoughts you experienced during the situation. How much would you say that you actually believe in these thought?"
        3 -> "Now that we've identified the situation and how your experiences from it', lets move on to reframing the situation. Could you try to identify the immediate thought that you feel was the most significant in producing your negative feelings? We'll try to explore that thought further."
        4 -> "How would you describe the impact believing in or having this thought has on you?"
        5 -> "If you didn't have this thought during the situation, how do you think your feelings would have changed?"
        6 -> "Do you feel that there is any evidence for this thought? Could you describe it?"
        7 -> "And on the other hand, is there any evidence against this thought that you could describe?"
        8 -> "Based on what you've described, is there an alternative thought or explanation that you feel fits this evidence better?"
        9 -> "If someone you cared for were to experience a similar situation to you, and responded with the same emotional experiences and immediate thoughts, what would you tell them?"
        10 -> "Finally, if you were to go through a similar situation again in the future, how would you prepare for it? Do you think that you could formulate an alternative response to it?"
        else -> ""
    }
}

/**
 * Returns the bot's message for the Anxiety Exploration activity type.
 *
 * @param messageNumber The index of the message to be displayed.
 * @return The message for the Anxiety Exploration activity.
 */
private fun handleAnxietyExplorationItem(messageNumber: Int): String {
    return when (messageNumber) {
        0 -> "What is something that could happen to you or in your life that you're worried about?"
        1 -> "Is there any evidence that this worry will or could happen?"
        2 -> "Is there any evidence that this worry will not happen?"
        3 -> "If your worry doesn't happen, how would that impact you?"
        4 -> "If your worry does come true, what would happen? How could you handle the situation? Could you create some strategies for handling it?"
        5 -> "After answering these questions, has your worry or your perspective on it changed? If so, could you describe those changes?"
        else -> ""
    }
}

/**
 * Returns the bot's message for the Action Plan activity type.
 *
 * @param messageNumber The index of the message to be displayed.
 * @return The message for the Action Plan activity.
 */
private fun handleActionPlanItem(messageNumber: Int): String {
    return when (messageNumber) {
        0 -> "What positive activity would you like to do?."
        1 -> "What day do you want to try doing this activity on?"
        2 -> "Why do you want to do this activity? Why is it important to you?"
        3 -> "Are there any obstacles that you can think of, which could prevent you from doing this activity?"
        4 -> "Could you think of some strategies that you could use to overcome those obstacles?"
        else -> ""
    }
}

/**
 * Returns the bot's message for the CBT Modeling activity type.
 *
 * @param messageNumber The index of the message to be displayed.
 * @return The message for the CBT Modeling activity.
 */
private fun handleCBTModelingItem(messageNumber: Int): String {
    return when (messageNumber) {
        0 -> "Lets start by identifying the situation you would like to model. Could you describe the situation?"
        1 -> "Now, lets explore the thoughts, feelings, and thoughts you hand in response to the situation\n\nFirst, what were you automatic/immediate thoughts in response to this situation?"
        2 -> "Next, what feelings did you experience in response to this situation?"
        3 -> "What were your behaviours in response to the situation?"
        4 -> "Are there any relationships you see between your thoughts and your feelings?"
        5 -> "Are there any relationships you see between your feelings and your behaviours?"
        6 -> "And are there any relationships you see between your behaviours and your thoughts"
        7 -> "Finally, are there any relationships you see between your thoughts, feelings, and behaviours as a whole? Could you describe those relationships?"
        else -> ""
    }
}