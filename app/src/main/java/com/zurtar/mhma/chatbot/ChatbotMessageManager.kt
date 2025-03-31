package com.zurtar.mhma.chatbot

import android.os.Build
import androidx.annotation.RequiresApi
import com.zurtar.mhma.data.ChatMessage
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
    private val addCurrentBranchLog: (List<ChatMessage>, ChatBranch) -> Unit,
    private val addCurrentBranchLogWithDate: (List<ChatMessage>, ChatBranch, Date?) -> Unit
){
    //Used to list of all messages in the chatbot dialogue. Used for presenting messages in the ChatbotPage
    private val _allMessages = mutableListOf<ChatMessage>()
    val allMessages: List<ChatMessage> get() = _allMessages.toList()

    //Used to store relevant messages into newly created logs
    var currentBranchMessages: MutableList<ChatMessage> = mutableListOf()
    private var currentBranch: ChatBranch = ChatBranch.Initial

    private var completionDate: Date? = null

    //tracks the current step in the active branch
    private var branchStep: Int = 0

    fun getBranchStep(): Int {
        return branchStep
    }

    //returns active dialogue branch
    fun getCurrentBranch(): ChatBranch {
        return currentBranch
    }

    //adds new message to total message list by taking a ChatMessage object
    fun addMessage(message: ChatMessage) {
        _allMessages.add(message)
    }

    fun sendCompletionDate(selectedDate: Date?){
        completionDate = selectedDate
    }

    private fun resetCompletionDate() {
        completionDate = null
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
                "Hello! Welcome back. I'm Zeke, and I'm here to support you as best I can. What activity would you like to do today?"
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
                "A thought record is used to help identify and challenge negative thought responses to events in your life. In this activity, you'll choose an event that happened to you and explore it. You'll then identify your emotional responses and your automatic/immediate thoughts to the event. Afterwards, you'll be guided through questions to help you reframe your thoughts and look for alternative perspectives.\n\nWould you like me to explain any of the other activities?"
            }

            userMessage.contains("anxiety exploration", ignoreCase = true) -> {
                "Anxiety exploration, or worry exploration, is an activity used to help people analyze their worries, and ways to manage them, to challenge catastrophization. You'll identify a worry that you believe may happen to you or in your live, examine evidence for and against that worry, and develop strategies for handling it..\n\nWould you like me to explain any of the other activities?"
            }

            userMessage.contains("smart goal", ignoreCase = true) -> {
                "SMART goals are used to help create objectives that are more easily realisable. You will create a goal that is Specific, Measurable, Attainable, Relevant, and Time-bound.\n\nWould you like me to explain any of the other activities?"
            }

            userMessage.contains("action plan", ignoreCase = true) -> {
                "An action plan is used to help plan out a positive activity you would like to do for yourself. You'll set out a date to do the activity, address potential obstacles that my prevent you from doing it, and then create plans to overcome those obstacles.\n\nWould you like me to explain any of the other activities?"
            }

            userMessage.contains("cbt modeling", ignoreCase = true) -> {
                "In cognitive behavioural therapy modeling, you will apply the CBT triangle onto a situation you have experienced, analyzing the intimate relations between your thoughts, feelings, and behaviours, for the purpose of disrupting any negative cycles.\n\nWould you like me to explain any of the other activities?"
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
                "What is it that you would like to do? Try to be as specific as you can be, to help make your goal as concrete as possible."
            }

            1 -> {
                branchStep++
                "How will you measure your progress towards achieving your goal?"
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
                "On what day do you want to complete this goal by?"
            }

            else -> {
                currentBranch = ChatBranch.Initial
                addCurrentBranchLogWithDate(currentBranchMessages, ChatBranch.SmartGoal, completionDate)
                resetCompletionDate()
                currentBranchMessages.clear()
                "Great work! Lets save that goal now. Is there anything else you would like to do today?"
            }
        }
    }

    private fun handleThoughtRecordBranch(): String{
        return when (branchStep) {
            0 -> {
                branchStep++
                "Lets start by discussing what event(s) led to the negative feelings. Could you please describe the situation to me?"
            }

            1 -> {
                branchStep++
                "And how would you describe the emotional experiences you had in response to the event(s)? How intense were those experiences?"
            }

            2 -> {
                branchStep++
                "Next, lets explore the immediate thoughts you experienced during the situation. How much would you say that you actually believe in these thought?"
            }

            3 -> {
                branchStep++
                "Now that we've identified the situation and how your experiences from it', lets move on to reframing the situation. Could you try to identify the immediate thought that you feel was the most significant in producing your negative feelings? We'll try to explore that thought further."
            }

            4 -> {
                branchStep++
                "How would you describe the impact believing in or having this thought has on you?"
            }

            5 -> {
                branchStep++
                "If you didn't have this thought during the situation, how do you think your feelings would have changed?"
            }

            6 -> {
                branchStep++
                "Do you feel that there is any evidence for this thought? Could you describe it?"
            }

            7 -> {
                branchStep++
                "And on the other hand, is there any evidence against this thought that you could describe?"
            }

            8 -> {
                branchStep++
                "Based on what you've described, is there an alternative thought or explanation that you feel fits this evidence better?"
            }

            9 -> {
                branchStep++
                "If someone you cared for were to experience a similar situation to you, and responded with the same emotional experiences and immediate thoughts, what would you tell them?"
            }

            10 -> {
                branchStep++
                "Finally, if you were to go through a similar situation again in the future, how would you prepare for it? Do you think that you could formulate an alternative response to it?"
            }

            else -> {
                currentBranch = ChatBranch.Initial
                addCurrentBranchLog(currentBranchMessages, ChatBranch.ThoughtRecord)
                currentBranchMessages.clear()
                "Amazing work!. Lets save that thought record now. Is there anything else you'd like to do?"
            }
        }
    }

    private fun handleAnxietyExplorationBranch(): String{
        return when (branchStep) {
            0 -> {
                branchStep++
                "What is something that could happen to you or in your life that you're worried about?"
            }

            1 -> {
                branchStep++
                "Is there any evidence that this worry will or could happen?"
            }

            2 -> {
                branchStep++
                "Is there any evidence that this worry will not happen?"
            }

            3 -> {
                branchStep++
                "If your worry doesn't happen, how would that impact you?"
            }

            4 -> {
                branchStep++
                "If your worry does come true, what would happen? How could you handle the situation? Could you create some strategies for handling it?"
            }

            5 -> {
                branchStep++
                "After answering these questions, has your worry or your perspective on it changed? If so, could you describe those changes?"
            }

            else -> {
                currentBranch = ChatBranch.Initial
                addCurrentBranchLog(currentBranchMessages, ChatBranch.AnxietyExploration)
                currentBranchMessages.clear()
                "Great work! Lets save this log for now. Is there anything else you would like to do?"
            }
        }
    }

    private fun handleActionPlanBranch(): String {
        return when (branchStep) {
            0 -> {
                branchStep++
                "What positive activity would you like to do?."
            }

            1 -> {
                branchStep++
                "What day do you want to try doing this activity on?"
            }

            2 -> {
                branchStep++
                "Why do you want to do this activity? Why is it important to you?"
            }

            3 -> {
                branchStep++
                "Are there any obstacles that you can think of, which could prevent you from doing this activity?"
            }

            4 -> {
                branchStep++
                "Could you think of some strategies that you could use to overcome those obstacles?"
            }

            else -> {
                currentBranch = ChatBranch.Initial
                addCurrentBranchLogWithDate(currentBranchMessages, ChatBranch.SmartGoal, completionDate)
                resetCompletionDate()
                currentBranchMessages.clear()
                "Great work! Lets save that action plan now. Is there anything else you would like to do today?"
            }
        }
    }

    private fun handleCBTModelingBranch(): String{
        return when (branchStep) {
            0 -> {
                branchStep++
                "To start, lets identify the situation you want to model using the CBT triangle. Could you describe the situation?"
            }

            1 -> {
                branchStep++
                "Now, lets explore the thoughts, feelings, and thoughts you hand in response to the situation\n\nFirst, what were you immediate thoughts in response to this situation?"
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
            //Are there any relationships you see betw

            else -> {
                currentBranch = ChatBranch.Initial
                addCurrentBranchLog(currentBranchMessages, ChatBranch.CBTModeling)
                currentBranchMessages.clear()
                "Great work! Lets save this log for now. Is there anything else you would like to do?"
            }
        }
    }
}