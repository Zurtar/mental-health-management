---
title: ChatRepository
---
//[app](../../../index.html)/[com.zurtar.mhma.data](../index.html)/[ChatRepository](index.html)



# ChatRepository



[androidJvm]\
@Singleton



class [ChatRepository](index.html)@Injectconstructor(chatRemoteDataSource: [ChatRemoteDataSource](../-chat-remote-data-source/index.html))

Repository for managing chat logs. This class acts as a middle layer between the data source and the rest of the application.



## Constructors


| | |
|---|---|
| [ChatRepository](-chat-repository.html) | [androidJvm]<br>@Inject<br>constructor(chatRemoteDataSource: [ChatRemoteDataSource](../-chat-remote-data-source/index.html)) |


## Functions


| Name | Summary |
|---|---|
| [addLog](add-log.html) | [androidJvm]<br>suspend fun [addLog](add-log.html)(log: [ChatLog](../-chat-log/index.html))<br>Adds a new chat log to the remote data source. |
| [deleteLog](delete-log.html) | [androidJvm]<br>suspend fun [deleteLog](delete-log.html)(log: [ChatLog](../-chat-log/index.html))<br>Deletes a chat log from the remote data source.<br>[androidJvm]<br>suspend fun [deleteLog](delete-log.html)(id: [String](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-string/index.html))<br>Deletes a chat log from the remote data source by its ID. |
| [getChatLogs](get-chat-logs.html) | [androidJvm]<br>fun [getChatLogs](get-chat-logs.html)(): Flow&lt;[List](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.collections/-list/index.html)&lt;[ChatLog](../-chat-log/index.html)&gt;&gt;<br>Retrieves all chat logs from the remote data source as a flow. The logs are emitted in real-time as changes occur in the Firestore collection. |
