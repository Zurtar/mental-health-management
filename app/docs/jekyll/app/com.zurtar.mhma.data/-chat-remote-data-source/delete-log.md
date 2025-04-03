---
title: deleteLog
---
//[app](../../../index.html)/[com.zurtar.mhma.data](../index.html)/[ChatRemoteDataSource](index.html)/[deleteLog](delete-log.html)



# deleteLog



[androidJvm]\
suspend fun [deleteLog](delete-log.html)(log: [ChatLog](../-chat-log/index.html))



Deletes a chat log from Firestore. If the log does not have an ID, the operation is skipped.



#### Parameters


androidJvm

| | |
|---|---|
| log | The chat log to be deleted. |





[androidJvm]\
suspend fun [deleteLog](delete-log.html)(id: [String](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-string/index.html))



Deletes a chat log from Firestore by its ID.



#### Parameters


androidJvm

| | |
|---|---|
| id | The unique ID of the chat log to be deleted. |



