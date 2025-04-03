---
title: addLog
---
//[app](../../../index.html)/[com.zurtar.mhma.data](../index.html)/[ChatRemoteDataSource](index.html)/[addLog](add-log.html)



# addLog



[androidJvm]\
suspend fun [addLog](add-log.html)(branch: [ChatBranch](../../com.zurtar.mhma.chatbot/-chat-branch/index.html), messageList: [List](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.collections/-list/index.html)&lt;[ChatMessage](../-chat-message/index.html)&gt;)



Adds a new chat log to the Firestore database.



#### Parameters


androidJvm

| | |
|---|---|
| branch | The chat branch associated with the log. |
| messageList | The list of user messages for this chat log. |





[androidJvm]\
suspend fun [addLog](add-log.html)(log: [ChatLog](../-chat-log/index.html))



Adds a chat log to Firestore. If the log does not have an ID, the operation is skipped.



#### Parameters


androidJvm

| | |
|---|---|
| log | The chat log to be added. |



