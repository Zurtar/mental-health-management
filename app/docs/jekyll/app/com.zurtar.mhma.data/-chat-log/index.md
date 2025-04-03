---
title: ChatLog
---
//[app](../../../index.html)/[com.zurtar.mhma.data](../index.html)/[ChatLog](index.html)



# ChatLog



[androidJvm]\
@Serializable



data class [ChatLog](index.html)(var id: [String](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-string/index.html)? = null, var logType: [ChatBranch](../../com.zurtar.mhma.chatbot/-chat-branch/index.html)? = null, var content: [List](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.collections/-list/index.html)&lt;[ChatMessage](../-chat-message/index.html)&gt; = listOf(), var createdAt: [Date](https://developer.android.com/reference/kotlin/java/util/Date.html)? = null, var toBeCompleted: [Date](https://developer.android.com/reference/kotlin/java/util/Date.html)? = null)

Represents a log of a chat activity or interaction. This contains a list of user messages, the chat branch type, and metadata like creation time and completion date. The chatbot's responses are not stored in the logs to save memory, as they are inferred from the log type.



## Constructors


| | |
|---|---|
| [ChatLog](-chat-log.html) | [androidJvm]<br>constructor(id: [String](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-string/index.html)? = null, logType: [ChatBranch](../../com.zurtar.mhma.chatbot/-chat-branch/index.html)? = null, content: [List](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.collections/-list/index.html)&lt;[ChatMessage](../-chat-message/index.html)&gt; = listOf(), createdAt: [Date](https://developer.android.com/reference/kotlin/java/util/Date.html)? = null, toBeCompleted: [Date](https://developer.android.com/reference/kotlin/java/util/Date.html)? = null) |


## Properties


| Name | Summary |
|---|---|
| [content](content.html) | [androidJvm]<br>var [content](content.html): [List](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.collections/-list/index.html)&lt;[ChatMessage](../-chat-message/index.html)&gt;<br>The list of messages from the user. |
| [createdAt](created-at.html) | [androidJvm]<br>@Serializable(with = [DateSerializer::class](../../com.zurtar.mhma.data.models/-date-serializer/index.html))<br>var [createdAt](created-at.html): [Date](https://developer.android.com/reference/kotlin/java/util/Date.html)?<br>The timestamp when the chat log was created. |
| [id](id.html) | [androidJvm]<br>var [id](id.html): [String](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-string/index.html)?<br>The unique identifier for this chat log. |
| [logType](log-type.html) | [androidJvm]<br>var [logType](log-type.html): [ChatBranch](../../com.zurtar.mhma.chatbot/-chat-branch/index.html)?<br>The type of chat branch, determining the flow of the conversation. |
| [toBeCompleted](to-be-completed.html) | [androidJvm]<br>@Serializable(with = [DateSerializer::class](../../com.zurtar.mhma.data.models/-date-serializer/index.html))<br>var [toBeCompleted](to-be-completed.html): [Date](https://developer.android.com/reference/kotlin/java/util/Date.html)?<br>The date when this chat log is expected to be completed. |
