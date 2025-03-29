package com.zurtar.mhma

import java.util.Date

/*
ChatMessage is used to sore individual messages in chat logs. Lists of messages
are used for displaying different chats.

information about whether the message was generated by the bot or the user
the content of the message, and the time the message was created.
 */
data class ChatMessage(
    val sender: Sender,
    val message: String,
    var createdAt: Date
)

/*
Represents a log of a specific chat activity or interaction. Using the logType variable, different
outputs for ChatLogPage are determined. ChatLog objects that get stored do not contain any messages
sent from the bot. They only contain messages from the user--since we already know all of the
chatbot's output based on the logType--to save memory.
 */
data class ChatLog(
    var id: Int,
    var logType: ChatBranch,
    var content : List<ChatMessage>,
    var createdAt: Date,
)

/*
mainly used as one of the parameters for ChatMessage objects to determine if the
message was created by the bot or the user.
*/
enum class Sender {
    User, Bot
}

/*
Most significant use of ChatBranch is for storing the logType, and also for
assisting in determining which branch of chat dialogue to activate.

Initial is for the default starting point of a conversation.
Explanation is a chat for explaining what other branches are.

Remaining branches are for actual cognitive behavioural therapy activities
*/
enum class ChatBranch {
    Initial, ThoughtRecord, AnxietyExploration, SmartGoal, ActionPlan, CBTModeling, Explanation
}