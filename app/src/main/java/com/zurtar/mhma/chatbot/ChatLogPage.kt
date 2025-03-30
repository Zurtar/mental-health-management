package com.zurtar.mhma.chatbot

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.zurtar.mhma.util.DefaultTopAppBar
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.Date
import java.util.Locale

@Composable
fun ChatLogPage(
    modifier: Modifier = Modifier,
    logId: Int,
    onNavigateBack: () -> Unit,
    viewModel: ChatbotViewModel = viewModel(),
    openDrawer: () -> Unit,
) {
    Scaffold(modifier = modifier.fillMaxSize(),
        topBar = {
            DefaultTopAppBar(openDrawer = openDrawer)
        }
    ) { innerPadding ->
        ChatLogPageContent(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            viewModel = viewModel,
            logId = logId,
            onNavigateBack = onNavigateBack,
        )
    }
}




/*
Composable function used to display previously saved/completed cognitive behavioural
therapy activities. Recreates the view the user would have originally seen of of in the
ChatbotPage.

Relies on two Composable functions for displaying content: UserMessageItemReconstruct and BotMessageItemReconstruct
to regenerate the view.

Title of the log is created using the helper function getLogTypeDisplayName

For the actual logic of the contents of the bot messages, handleBotMessage function is used in
tandem with various other handle..Branch functions
 */
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ChatLogPageContent(
    modifier: Modifier = Modifier,
    logId: Int,
    onNavigateBack: () -> Unit,
    viewModel: ChatbotViewModel = viewModel(),
) {
    val chatLog = viewModel.getLog(logId)

    Column(
        modifier = modifier
            .fillMaxSize()
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
                text = "Chat Log",
                fontSize = 30.sp,
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier
                    .padding(8.dp),
            )
            Button(onClick = onNavigateBack) {
                Text(text = "Back")
            }
        }
        Column(
            modifier = Modifier
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            chatLog?.let { log ->
                Text(
                    text = getLogTypeDisplayName(log.logType),
                    fontSize = 20.sp,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(
                    text = "Created At: ${SimpleDateFormat("HH:mm:aa, MMMM dd", Locale.ENGLISH).format(log.createdAt)}",
                    fontSize = 14.sp,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                if (log.toBeCompleted != null) {
                    Text(
                        text = "To be completed At: ${SimpleDateFormat("EEEE, MMMM dd", Locale.getDefault()).format(log.toBeCompleted)}",
                        fontSize = 14.sp,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                }

                for ((index, userMessage) in log.content.withIndex()) {
                    BotMessageItemReconstruct(branch = log.logType, messageNumber = index)
                    UserMessageItemReconstruct(message = userMessage)

                }
            } ?: Text("Log not found")
        }
    }
}


fun getLogTypeDisplayName(logType: ChatBranch): String {
    return when (logType) {
        ChatBranch.SmartGoal -> "Log Type: Smart Goal"
        ChatBranch.ThoughtRecord -> "Log Type: Thought Record"
        ChatBranch.AnxietyExploration -> "Log Type: Anxiety Exploration"
        ChatBranch.ActionPlan -> "Log Type: Action Plan"
        ChatBranch.CBTModeling -> "Log Type: CBT Modeling"
        else -> "Error: Unknown log type"
    }
}

@Composable
fun UserMessageItemReconstruct(message: ChatMessage) {
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
                color = MaterialTheme.colorScheme.onSecondary)
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
        ) {
            Text(
                text = botMessage,
                color = MaterialTheme.colorScheme.onTertiary)
        }
    }
}

/*
Determines what message should be created for the bot based on the logType/branch, and
the message number (since we know total messages based on logType, appropriate bot
message can be determined based on messageNumber. Similar to reverse process done using
branchStep
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

/*
Bellow functions are called upon by handleBotMessage, being given a messageNumber
to select the appropriate output String for that respective branch
 */

private fun handleSmartGoalItem(messageNumber: Int): String {
    return when (messageNumber) {
        0 -> "What is it that you would like to do? Try to be as specific as you can be, to help make the goal as concrete as possible."
        1 -> "How will you measure your progress towards achieving this goal?"
        2 -> "Is this goal attainable? What will you need to do to complete it?"
        3 -> "Why is this goal relevant to you? What makes it important for you?"
        4 -> "At what time do you want to complete this goal by?"
        else -> ""
    }
}

private fun handleThoughtRecordItem(messageNumber: Int): String {
    return when (messageNumber) {
        0 -> "Lets start by discussing what event(s) led to the negative feelings. Could you describe the situation to me?"
        1 -> "Could you describe what kind of emotional experiences you had in response to the event(s)? How intense were those experiences?"
        2 -> "Next, lets explore the automatic/immediate thoughts you experienced during the situation. How much would you say that you actually believe in these thought?"
        3 -> "Now that we have the situation and your response to it modelled, lets move on to reframing the situation. Could you try to identify which of the automatic thoughts you had is responsible for the greatest amount of discomfort? We'll try to explore that thought further."
        4 -> "Could you describe the impact that believing in this thought has on you?"
        5 -> "How do you think things would change if you stopped believing in this thought?"
        6 -> "Do you feel that there is any evidence for this thought? Could you describe it?"
        7 -> "Is there any evidence against this thought that you could describe?"
        8 -> "Is there an alternative thought or explanation that you feel fits this evidence better?"
        9 -> "If someone you cared for were to experience a similar situation to you, and responded with the same emotional experiences and automatic thoughts, what would you tell them?"
        10 -> "Finally, how do you think think you could formulate a better response to a similar situation in the future? Do you feel that you would be more prepared if this situation were to happen again?"
        else -> ""
    }
}

private fun handleAnxietyExplorationItem(messageNumber: Int): String {
    return when (messageNumber) {
        0 -> "What is something that could happen, which you are worried about?"
        1 -> "Is there any evidence that this worry could happen?"
        2 -> "If there any evidence that this worry will not happen?"
        3 -> "If your worry doesn't happen, what would happen?"
        4 -> "If your worry does come true, how could you handle the situation? Could you create some strategies for handling it?"
        5 -> "After answering these questions, has your worry changed? If so, could you describe those changes?"
        else -> ""
    }
}

private fun handleActionPlanItem(messageNumber: Int): String {
    return when (messageNumber) {
        0 -> "What activity would you like to do?."
        1 -> "What time do you want to do this activity? Try to set a specific time and date."
        2 -> "Why do you want to do this activity? Why is it important to you?"
        3 -> "Are there any obstacles you can think of that could prevent you from doing this activity?"
        4 -> "What strategies could you use to overcome those obstacles?"
        else -> ""
    }
}

private fun handleCBTModelingItem(messageNumber: Int): String{
    return when (messageNumber) {
        0 -> "Lets start by identifying the situation you would like to model. Could you describe the situation?"
        1 -> "Now, lets explore the thoughts, feelings, and thoughts you hand in response to the situation\n\nFirst, what were you automatic/immediate thoughts in response to this situation?"
        2 -> "Next, what feelings did you experience in response to this situation?"
        3 -> "What were your behaviours in response to the situation?"
        4 -> "Finally, are there any relationships you see between your thoughts, feelings, and behaviours? Could you describe those relationships?"
        else -> ""
    }
}