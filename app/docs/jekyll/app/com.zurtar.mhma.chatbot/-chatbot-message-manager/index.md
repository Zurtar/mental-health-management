---
title: ChatbotMessageManager
---
//[app](../../../index.html)/[com.zurtar.mhma.chatbot](../index.html)/[ChatbotMessageManager](index.html)



# ChatbotMessageManager

class [ChatbotMessageManager](index.html)(addCurrentBranchLog: ([List](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.collections/-list/index.html)&lt;[ChatMessage](../../com.zurtar.mhma.data/-chat-message/index.html)&gt;, [ChatBranch](../-chat-branch/index.html)) -&gt; [Unit](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-unit/index.html), addCurrentBranchLogWithDate: ([List](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.collections/-list/index.html)&lt;[ChatMessage](../../com.zurtar.mhma.data/-chat-message/index.html)&gt;, [ChatBranch](../-chat-branch/index.html), [Date](https://developer.android.com/reference/kotlin/java/util/Date.html)?) -&gt; [Unit](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-unit/index.html))

Manages the flow of messages (sending and generating bot replies) and the logic for different activities in the chatbot.



This class is responsible for maintaining the list of all messages, handling different branches (like Smart Goal, Thought Record, etc.), and simulating bot responses based on the user's input.



#### Parameters


androidJvm

| | |
|---|---|
| addCurrentBranchLog | A function that adds the current branch log to the main log list. |
| addCurrentBranchLogWithDate | A function that adds the current branch log with a date to the main log list. |



## Constructors


| | |
|---|---|
| [ChatbotMessageManager](-chatbot-message-manager.html) | [androidJvm]<br>constructor(addCurrentBranchLog: ([List](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.collections/-list/index.html)&lt;[ChatMessage](../../com.zurtar.mhma.data/-chat-message/index.html)&gt;, [ChatBranch](../-chat-branch/index.html)) -&gt; [Unit](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-unit/index.html), addCurrentBranchLogWithDate: ([List](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.collections/-list/index.html)&lt;[ChatMessage](../../com.zurtar.mhma.data/-chat-message/index.html)&gt;, [ChatBranch](../-chat-branch/index.html), [Date](https://developer.android.com/reference/kotlin/java/util/Date.html)?) -&gt; [Unit](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-unit/index.html)) |


## Properties


| Name | Summary |
|---|---|
| [allMessages](all-messages.html) | [androidJvm]<br>val [allMessages](all-messages.html): [List](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.collections/-list/index.html)&lt;[ChatMessage](../../com.zurtar.mhma.data/-chat-message/index.html)&gt; |
| [currentBranchMessages](current-branch-messages.html) | [androidJvm]<br>var [currentBranchMessages](current-branch-messages.html): [MutableList](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.collections/-mutable-list/index.html)&lt;[ChatMessage](../../com.zurtar.mhma.data/-chat-message/index.html)&gt; |


## Functions


| Name | Summary |
|---|---|
| [addMessage](add-message.html) | [androidJvm]<br>fun [addMessage](add-message.html)(message: [ChatMessage](../../com.zurtar.mhma.data/-chat-message/index.html))<br>Adds a new message to the total message list by taking a [ChatMessage](../../com.zurtar.mhma.data/-chat-message/index.html) object. |
| [getBranch](get-branch.html) | [androidJvm]<br>fun [getBranch](get-branch.html)(userMessage: [String](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-string/index.html))<br>Updates the current branch/activity based on the user's input message. |
| [getBranchList](get-branch-list.html) | [androidJvm]<br>fun [getBranchList](get-branch-list.html)(): [List](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.collections/-list/index.html)&lt;[ChatBranch](../-chat-branch/index.html)&gt;<br>Returns a list of all available branches for the chatbot. |
| [getBranchStep](get-branch-step.html) | [androidJvm]<br>fun [getBranchStep](get-branch-step.html)(): [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html) |
| [getCurrentBranch](get-current-branch.html) | [androidJvm]<br>fun [getCurrentBranch](get-current-branch.html)(): [ChatBranch](../-chat-branch/index.html) |
| [sendCompletionDate](send-completion-date.html) | [androidJvm]<br>fun [sendCompletionDate](send-completion-date.html)(selectedDate: [Date](https://developer.android.com/reference/kotlin/java/util/Date.html)?)<br>Sends the selected completion date to the chatbot manager. |
| [simulateBotResponse](simulate-bot-response.html) | [androidJvm]<br>suspend fun [simulateBotResponse](simulate-bot-response.html)(userMessage: [String](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-string/index.html))<br>Simulates the bot's response to the user's input. This introduces a small delay before the bot replies to make the interaction feel more natural. |
