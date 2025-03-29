package com.zurtar.mhma.chatbot

import android.os.Build
import androidx.annotation.RequiresApi
import kotlinx.coroutines.delay
import java.util.Date

/*
 Manages the flow of messages (sending and generating bot replies), and the logic
 for the different activities of the chatbot.

addCurrentBranchLog Function is used to add the log the user is currently doing into the main
log list. This avoids accidentally adding all existing messages into the log, including branch
selection messages or messages from previous logs.
 */
@RequiresApi(Build.VERSION_CODES.O)
class ChatbotMessageManager (
    private val addCurrentBranchLog: (List<ChatMessage>, ChatBranch) -> Unit
){
    //Used to list of all messages in the chatbot dialogue. Used for presenting messages in the ChatbotPage
    private val _allMessages = mutableListOf<ChatMessage>()
    val allMessages: List<ChatMessage> get() = _allMessages.toList()

    //Used to store relevant messages into newly created logs
    var currentBranchMessages: MutableList<ChatMessage> = mutableListOf()
    private var currentBranch: ChatBranch = ChatBranch.Initial

    //tracks the current step in the active branch
    private var branchStep: Int = 0

    //returns active dialogue branch
    fun getCurrentBranch(): ChatBranch {
        return currentBranch
    }

    //adds new message to total message list by taking a ChatMessage object
    fun addMessage(message: ChatMessage) {
        _allMessages.add(message)
    }

    /*
    Used to simulate bot response to the user. Currently there is a delay of 500 ms
    It may need to be removed, as could be possible for the user to send messages too quickly,
    interrupting dialogue branch logic (this has not occurred to me while testing, so I don't know
    if this is actually something that can happen)
     */
    suspend fun simulateBotResponse(userMessage: String) {
        val botResponse = getBotDialogue(userMessage)
        delay(500)
        addMessage(ChatMessage(Sender.Bot, botResponse, Date()))
    }

    /*
    Retrieves the appropriate response from the bot given a message from the user.
    In ChatbotPage, there are buttons that the user presses which send specific strings
    that activate different branches.
     */
    private fun getBotDialogue(userMessage: String): String {
        if (currentBranch == ChatBranch.Initial && branchStep != 0){
            currentBranchMessages.clear()
            getBranch(userMessage)
        }
        return when (currentBranch) {
            ChatBranch.Initial -> {
                getBranch(userMessage)
                branchStep = 1
                currentBranchMessages.clear()
                "Hello! Welcome back. I'm Vibecheck, and I'm here to support you. What would you like to do today?"
            }

            ChatBranch.SmartGoal -> {
                handleSmartGoalBranch()
            }

            ChatBranch.ThoughtRecord -> {
                handleThoughtRecordBranch()
            }

            ChatBranch.AnxietyExploration -> {
                handleAnxietyExplorationBranch()
            }

            ChatBranch.ActionPlan -> {
                handleActionPlanBranch()
            }

            ChatBranch.CBTModeling -> {
                handleCBTModelingBranch()
            }

            ChatBranch.Explanation -> {
                handleExplanationBranch(userMessage)
            }
        }
    }

    //Returns list of all available branches
    fun getBranchList(): List<ChatBranch> {
        return listOf(
            ChatBranch.SmartGoal,
            ChatBranch.ThoughtRecord,
            ChatBranch.AnxietyExploration,
            ChatBranch.ActionPlan,
            ChatBranch.CBTModeling,
            ChatBranch.Explanation
        )
    }

    //updates current branch/activity based on string value
     fun getBranch(userMessage: String) {
        when {
            userMessage.contains("thought record", ignoreCase = true) -> {
                currentBranch = ChatBranch.ThoughtRecord
                branchStep = 0
            }

            userMessage.contains("anxiety exploration", ignoreCase = true) -> {
                currentBranch = ChatBranch.AnxietyExploration
                branchStep = 0
            }

            userMessage.contains("smart goal", ignoreCase = true) -> {
                currentBranch = ChatBranch.SmartGoal
                branchStep = 0
            }

            userMessage.contains("action plan", ignoreCase = true) -> {
                currentBranch = ChatBranch.ActionPlan
                branchStep = 0
            }

            userMessage.contains("CBT modeling", ignoreCase = true) -> {
                currentBranch = ChatBranch.CBTModeling
                branchStep = 0
            }

            userMessage.contains("What are these different activities for?", ignoreCase = true) -> {
                currentBranch = ChatBranch.Explanation
                branchStep = 0
            }
        }
    }

    /*
    Handles logic for explanation branch. Explains different branches based on string
    value passed to it. In ChatbotPage, string values are determined based on buttons
    inputs.
     */
    private fun handleExplanationBranch(userMessage: String): String {
        if (branchStep == 0) {
            branchStep++
            return "Which of the activities would you like me to explain?"
        }
        return when {
            userMessage.contains("thought record", ignoreCase = true) -> {
                "A Thought Record helps you identify and challenge negative thought patterns in response to events in your life. You'll explore the event, your emotional responses, automatic/immediate thoughts, and look for alternative perspectives.\n\nWould you like me to explain any of the other activities?"
            }

            userMessage.contains("anxiety exploration", ignoreCase = true) -> {
                "Anxiety exploration is an activity used to help people understand their worries better, and how they can be manages. You'll examine a worry that is troubling you, examine evidence for that worry, and develop strategies for handling it.\n\nWould you like me to explain any of the other activities?"
            }

            userMessage.contains("smart goal", ignoreCase = true) -> {
                "Setting a SMART Goal helps you create achievable objectives. You'll define a goal that's Specific, Measurable, Attainable, Relevant, and Time-bound.\n\nWould you like me to explain any of the other activities?"
            }

            userMessage.contains("action plan", ignoreCase = true) -> {
                "An action plan is used to help plan out an activity you would like to do. You will set a time for the activity, address potential obstacles, and plan how to overcome them.\n\nWould you like me to explain any of the other activities?"
            }

            userMessage.contains("cbt modeling", ignoreCase = true) -> {
                "CBT Modeling helps you understand the interplay between your thoughts, feelings, and behaviors. You'll model a situation to examine these relationships in depth.\n\nWould you like me to explain any of the other activities?"
            }

            else -> {
                currentBranch = ChatBranch.Initial
                "I hope that helped clear things up! Now, would you like to select one of the activities to do?"
            }
        }
    }

    /*
    The following handle..Branch functions all work based on the branchStep value.
    Using the branch step value, they return the appropriate string.
     */
    private fun handleSmartGoalBranch(): String {
        return when (branchStep) {
            0 -> {
                branchStep++
                "What is it that you would like to do? Try to be as specific as you can be, to help make the goal as concrete as possible."
            }

            1 -> {
                branchStep++
                "How will you measure your progress towards achieving this goal?"
            }

            2 -> {
                branchStep++
                "Is this goal attainable? What will you need to do to complete it?"
            }

            3 -> {
                branchStep++
                "Why is this goal relevant to you? What makes it important for you?"
            }

            4 -> {
                branchStep++
                "At what time do you want to complete this goal by?"
            }

            else -> {
                currentBranch = ChatBranch.Initial
                addCurrentBranchLog(currentBranchMessages, ChatBranch.SmartGoal)
                currentBranchMessages.clear()
                "Great work [user]! Lets save that goal now. Is there anything else you would like to do today?"
            }
        }
    }

    private fun handleThoughtRecordBranch(): String{
        return when (branchStep) {
            0 -> {
                branchStep++
                "Lets start by discussing what event(s) led to the negative feelings. Could you describe the situation to me?"
            }

            1 -> {
                branchStep++
                "Could you describe what kind of emotional experiences you had in response to the event(s)? How intense were those experiences?"
            }

            2 -> {
                branchStep++
                "Next, lets explore the automatic/immediate thoughts you experienced during the situation. How much would you say that you actually believe in these thought?"
            }

            3 -> {
                branchStep++
                "Now that we have the situation and your response to it modelled, lets move on to reframing the situation. Could you try to identify which of the automatic thoughts you had is responsible for the greatest amount of discomfort? We'll try to explore that thought further."
            }

            4 -> {
                branchStep++
                "Could you describe the impact that believing in this thought has on you?"
            }

            5 -> {
                branchStep++
                "How do you think things would change if you stopped believing in this thought?"
            }

            6 -> {
                branchStep++
                "Do you feel that there is any evidence for this thought? Could you describe it?"
            }

            7 -> {
                branchStep++
                "Is there any evidence against this thought that you could describe?"
            }

            8 -> {
                branchStep++
                "Is there an alternative thought or explanation that you feel fits this evidence better?"
            }

            9 -> {
                branchStep++
                "If someone you cared for were to experience a similar situation to you, and responded with the same emotional experiences and automatic thoughts, what would you tell them?"
            }

            10 -> {
                branchStep++
                "Finally, how do you think think you could formulate a better response to a similar situation in the future? Do you feel that you would be more prepared if this situation were to happen again?"
            }

            else -> {
                currentBranch = ChatBranch.Initial
                addCurrentBranchLog(currentBranchMessages, ChatBranch.ThoughtRecord)
                currentBranchMessages.clear()
                "Amazing work, [user]!. Lets save that thought record now. Is there anything else you'd like to do?"
            }
        }
    }

    private fun handleAnxietyExplorationBranch(): String{
        return when (branchStep) {
            0 -> {
                branchStep++
                "What is something that could happen, which you are worried about?"
            }

            1 -> {
                branchStep++
                "Is there any evidence that this worry could happen?"
            }

            2 -> {
                branchStep++
                "If there any evidence that this worry will not happen?"
            }

            3 -> {
                branchStep++
                "If your worry doesn't happen, what would happen?"
            }

            4 -> {
                branchStep++
                "If your worry does come true, how could you handle the situation? Could you create some strategies for handling it?"
            }

            5 -> {
                branchStep++
                "After answering these questions, has your worry changed? If so, could you describe those changes?"
            }

            else -> {
                currentBranch = ChatBranch.Initial
                addCurrentBranchLog(currentBranchMessages, ChatBranch.AnxietyExploration)
                currentBranchMessages.clear()
                "Great work, [user]! Lets save this log for now. Is there anything else you would like to do?"
            }
        }
    }

    private fun handleActionPlanBranch(): String {
        return when (branchStep) {
            0 -> {
                branchStep++
                "What activity would you like to do?."
            }

            1 -> {
                branchStep++
                "What time do you want to do this activity? Try to set a specific time and date."
            }

            2 -> {
                branchStep++
                "Why do you want to do this activity? Why is it important to you?"
            }

            3 -> {
                branchStep++
                "Are there any obstacles you can think of that could prevent you from doing this activity?"
            }

            4 -> {
                branchStep++
                "What strategies could you use to overcome those obstacles?"
            }

            else -> {
                currentBranch = ChatBranch.Initial
                addCurrentBranchLog(currentBranchMessages, ChatBranch.ActionPlan)
                currentBranchMessages.clear()
                "Great work [user]! Lets save that action plan now. Is there anything else you would like to do today?"
            }
        }
    }

    private fun handleCBTModelingBranch(): String{
        return when (branchStep) {
            0 -> {
                branchStep++
                "Lets start by identifying the situation you would like to model. Could you describe the situation?"
            }

            1 -> {
                branchStep++
                "Now, lets explore the thoughts, feelings, and thoughts you hand in response to the situation\n\nFirst, what were you automatic/immediate thoughts in response to this situation?"
            }

            2 -> {
                branchStep++
                "Next, what feelings did you experience in response to this situation?"
            }

            3 -> {
                branchStep++
                "What were your behaviours in response to the situation?"
            }

            4 -> {
                branchStep++
                "Finally, are there any relationships you see between your thoughts, feelings, and behaviours? Could you describe those relationships?"
            }

            else -> {
                currentBranch = ChatBranch.Initial
                addCurrentBranchLog(currentBranchMessages, ChatBranch.CBTModeling)
                currentBranchMessages.clear()
                "Great work, [user]! Lets save this log for now. Is there anything else you would like to do?"
            }
        }
    }
}