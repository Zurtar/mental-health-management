---
title: ChatListPage
---
//[app](../../index.html)/[com.zurtar.mhma.chatbot](index.html)/[ChatListPage](-chat-list-page.html)



# ChatListPage



[androidJvm]\




@[Composable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Composable.html)



fun [ChatListPage](-chat-list-page.html)(modifier: [Modifier](https://developer.android.com/reference/kotlin/androidx/compose/ui/Modifier.html) = Modifier, viewModel: [ChatbotViewModel](-chatbot-view-model/index.html) = hiltViewModel(), onNavigateToChatbot: () -&gt; [Unit](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-unit/index.html), onNavigateToChatLog: ([String](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-string/index.html)) -&gt; [Unit](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-unit/index.html), openDrawer: () -&gt; [Unit](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-unit/index.html))



Main function for the ChatListPage, used to display a list of previously completed chatLogs or cognitive behavioural therapy activities.



Relies on helper composables ChatListPageContent and LogItem. ChatListPageContent is used for rendering the content of the page, while LogItem defines the composables that are listed on the page.



#### Parameters


androidJvm

| | |
|---|---|
| modifier | Optional modifier for the composable. |
| viewModel | The view model for accessing the list of logs. |
| onNavigateToChatbot | Callback function for navigating to the chatbot page. |
| onNavigateToChatLog | Callback function for navigating to a specific chat log. |
| openDrawer | Callback function for opening the navigation drawer. |



