---
title: getChatLogs
---
//[app](../../../index.html)/[com.zurtar.mhma.data](../index.html)/[ChatRemoteDataSource](index.html)/[getChatLogs](get-chat-logs.html)



# getChatLogs



[androidJvm]\
fun [getChatLogs](get-chat-logs.html)(): Flow&lt;[List](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.collections/-list/index.html)&lt;[ChatLog](../-chat-log/index.html)&gt;&gt;



Retrieves chat logs from Firestore in real-time. This method listens for changes in the Firestore collection and emits updated logs.



#### Return



A Flow of lists of [ChatLog](../-chat-log/index.html) objects.



