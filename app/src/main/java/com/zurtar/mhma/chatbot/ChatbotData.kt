package com.zurtar.mhma.chatbot

/*
mainly used as one of the parameters for ChatMessage objects to determine if the
message was created by the bot or the user.
*/
enum class Sender {
    Null, User, Bot
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