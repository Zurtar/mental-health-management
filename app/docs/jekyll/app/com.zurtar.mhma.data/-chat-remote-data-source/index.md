---
title: ChatRemoteDataSource
---
//[app](../../../index.html)/[com.zurtar.mhma.data](../index.html)/[ChatRemoteDataSource](index.html)



# ChatRemoteDataSource



[androidJvm]\
@Singleton



class [ChatRemoteDataSource](index.html)@Injectconstructor(fireStoreDatasource: FirebaseFirestore)

Data source for managing chat logs in Firestore. This class provides functions to add, delete, and retrieve chat logs from Firestore.



## Constructors


| | |
|---|---|
| [ChatRemoteDataSource](-chat-remote-data-source.html) | [androidJvm]<br>@Inject<br>constructor(fireStoreDatasource: FirebaseFirestore) |


## Functions


| Name | Summary |
|---|---|
| [addLog](add-log.html) | [androidJvm]<br>suspend fun [addLog](add-log.html)(log: [ChatLog](../-chat-log/index.html))<br>Adds a chat log to Firestore. If the log does not have an ID, the operation is skipped.<br>[androidJvm]<br>suspend fun [addLog](add-log.html)(branch: [ChatBranch](../../com.zurtar.mhma.chatbot/-chat-branch/index.html), messageList: [List](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.collections/-list/index.html)&lt;[ChatMessage](../-chat-message/index.html)&gt;)<br>Adds a new chat log to the Firestore database. |
| [deleteLog](delete-log.html) | [androidJvm]<br>suspend fun [deleteLog](delete-log.html)(log: [ChatLog](../-chat-log/index.html))<br>Deletes a chat log from Firestore. If the log does not have an ID, the operation is skipped.<br>[androidJvm]<br>suspend fun [deleteLog](delete-log.html)(id: [String](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-string/index.html))<br>Deletes a chat log from Firestore by its ID. |
| [getChatLogs](get-chat-logs.html) | [androidJvm]<br>fun [getChatLogs](get-chat-logs.html)(): Flow&lt;[List](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.collections/-list/index.html)&lt;[ChatLog](../-chat-log/index.html)&gt;&gt;<br>Retrieves chat logs from Firestore in real-time. This method listens for changes in the Firestore collection and emits updated logs. |
