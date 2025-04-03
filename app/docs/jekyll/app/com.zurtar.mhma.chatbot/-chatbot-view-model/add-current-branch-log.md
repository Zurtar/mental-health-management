---
title: addCurrentBranchLog
---
//[app](../../../index.html)/[com.zurtar.mhma.chatbot](../index.html)/[ChatbotViewModel](index.html)/[addCurrentBranchLog](add-current-branch-log.html)



# addCurrentBranchLog



[androidJvm]\
fun [addCurrentBranchLog](add-current-branch-log.html)(log: [ChatLog](../../com.zurtar.mhma.data/-chat-log/index.html))



Adds a chat log to the repository and clears the current branch messages.



#### Parameters


androidJvm

| | |
|---|---|
| log | The chat log to be added. |





[androidJvm]\
fun [addCurrentBranchLog](add-current-branch-log.html)(messageList: [List](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.collections/-list/index.html)&lt;[ChatMessage](../../com.zurtar.mhma.data/-chat-message/index.html)&gt;, logType: [ChatBranch](../-chat-branch/index.html), toBeCompleted: [Date](https://developer.android.com/reference/kotlin/java/util/Date.html)? = null)



Creates and adds a new chat log based on a list of messages. It makes a copy of the provided message list before storing it.



#### Parameters


androidJvm

| | |
|---|---|
| messageList | The list of messages to be stored in the log. |
| logType | The type of chat branch associated with the log. |
| toBeCompleted | (Optional) A date indicating when the chat log should be completed. |



