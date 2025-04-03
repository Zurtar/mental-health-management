package com.zurtar.mhma.chatbot

/**
 * Represents the sender of a chat message, which can be either the user or the bot.
 *
 * @property Null A placeholder value when no sender is specified.
 * @property User Represents the user who sent the message.
 * @property Bot Represents the bot that sent the message.
 */
enum class Sender {
    Null, User, Bot
}

/**
 * Represents the different branches or types of conversation in the chatbot dialogue.
 * Each branch corresponds to a specific stage or activity in the cognitive behavioral therapy (CBT) process.
 *
 * @property Initial The starting point of the conversation, used as the default state.
 * @property ThoughtRecord The branch used for guiding the user through the Thought Record process.
 * @property AnxietyExploration The branch used for exploring the user's anxiety and worries.
 * @property SmartGoal The branch for setting and working on SMART (Specific, Measurable, Achievable, Relevant, Time-bound) goals.
 * @property ActionPlan The branch where the user creates a plan for positive action.
 * @property CBTModeling The branch used to model thoughts, feelings, and behaviors using the CBT triangle.
 * @property Explanation The branch for explaining the other branches and their functions.
 */
enum class ChatBranch {
    Initial, ThoughtRecord, AnxietyExploration, SmartGoal, ActionPlan, CBTModeling, Explanation
}
