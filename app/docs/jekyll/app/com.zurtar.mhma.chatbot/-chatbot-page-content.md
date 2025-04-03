---
title: ChatbotPageContent
---
//[app](../../index.html)/[com.zurtar.mhma.chatbot](index.html)/[ChatbotPageContent](-chatbot-page-content.html)



# ChatbotPageContent



[androidJvm]\




@[Composable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Composable.html)



fun [ChatbotPageContent](-chatbot-page-content.html)(modifier: [Modifier](https://developer.android.com/reference/kotlin/androidx/compose/ui/Modifier.html) = Modifier, viewModel: [ChatbotViewModel](-chatbot-view-model/index.html) = viewModel(), onNavigateToChatList: () -&gt; [Unit](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-unit/index.html))



Composable function for displaying the content of the Chatbot page. It handles the layout of messages between the user and the bot, along with the display of branch selection buttons and message input. It relies on additional composables such as `UserMessageItem` and `BotMessageItem`.



The function also manages the automatic scroll to the latest message, updates based on the current branch, and handles message sending and date selection for specific branches.



#### Parameters


androidJvm

| | |
|---|---|
| modifier | The modifier to be applied to the ChatbotPageContent. |
| viewModel | The ViewModel that provides the data and logic for the chatbot. |
| onNavigateToChatList | Lambda function for navigating to the chat list. |



