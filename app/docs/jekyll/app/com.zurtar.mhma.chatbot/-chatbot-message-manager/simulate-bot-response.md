---
title: simulateBotResponse
---
//[app](../../../index.html)/[com.zurtar.mhma.chatbot](../index.html)/[ChatbotMessageManager](index.html)/[simulateBotResponse](simulate-bot-response.html)



# simulateBotResponse



[androidJvm]\
suspend fun [simulateBotResponse](simulate-bot-response.html)(userMessage: [String](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-string/index.html))



Simulates the bot's response to the user's input. This introduces a small delay before the bot replies to make the interaction feel more natural.



Currently there is a delay of 500 ms, It may need to be removed, as could be possible for the user to send messages too quickly, interrupting dialogue branch logic (this has not occurred to me while testing, so I don't know if this is actually something that can happen)



#### Parameters


androidJvm

| | |
|---|---|
| userMessage | The user's input message. |



