---
title: ChatMessage
---
//[app](../../../index.html)/[com.zurtar.mhma.data](../index.html)/[ChatMessage](index.html)



# ChatMessage



[androidJvm]\
@Serializable



data class [ChatMessage](index.html)(val sender: [Sender](../../com.zurtar.mhma.chatbot/-sender/index.html) = Sender.Null, val message: [String](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-string/index.html) = &quot;&quot;, var createdAt: [Date](https://developer.android.com/reference/kotlin/java/util/Date.html)? = null)

Represents a single chat message in a conversation. A [ChatMessage](index.html) consists of the sender (either bot or user), the message content, and the timestamp of when the message was created.



## Constructors


| | |
|---|---|
| [ChatMessage](-chat-message.html) | [androidJvm]<br>constructor(sender: [Sender](../../com.zurtar.mhma.chatbot/-sender/index.html) = Sender.Null, message: [String](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-string/index.html) = &quot;&quot;, createdAt: [Date](https://developer.android.com/reference/kotlin/java/util/Date.html)? = null) |


## Properties


| Name | Summary |
|---|---|
| [createdAt](created-at.html) | [androidJvm]<br>@Serializable(with = [DateSerializer::class](../../com.zurtar.mhma.data.models/-date-serializer/index.html))<br>var [createdAt](created-at.html): [Date](https://developer.android.com/reference/kotlin/java/util/Date.html)?<br>The timestamp when the message was created. |
| [message](message.html) | [androidJvm]<br>val [message](message.html): [String](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-string/index.html)<br>The actual content of the message. |
| [sender](sender.html) | [androidJvm]<br>val [sender](sender.html): [Sender](../../com.zurtar.mhma.chatbot/-sender/index.html)<br>Indicates whether the message is from the user or the bot. |
