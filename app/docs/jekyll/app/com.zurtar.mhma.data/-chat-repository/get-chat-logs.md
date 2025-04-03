---
title: getChatLogs
---
//[app](../../../index.html)/[com.zurtar.mhma.data](../index.html)/[ChatRepository](index.html)/[getChatLogs](get-chat-logs.html)



# getChatLogs



[androidJvm]\
fun [getChatLogs](get-chat-logs.html)(): Flow&lt;[List](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.collections/-list/index.html)&lt;[ChatLog](../-chat-log/index.html)&gt;&gt;



Retrieves all chat logs from the remote data source as a flow. The logs are emitted in real-time as changes occur in the Firestore collection.



#### Return



A Flow of lists of [ChatLog](../-chat-log/index.html) objects.



