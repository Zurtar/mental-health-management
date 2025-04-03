---
title: ChatLogPage
---
//[app](../../index.html)/[com.zurtar.mhma.chatbot](index.html)/[ChatLogPage](-chat-log-page.html)



# ChatLogPage



[androidJvm]\




@[Composable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Composable.html)



fun [ChatLogPage](-chat-log-page.html)(modifier: [Modifier](https://developer.android.com/reference/kotlin/androidx/compose/ui/Modifier.html) = Modifier, logId: [String](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-string/index.html), onNavigateBack: () -&gt; [Unit](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-unit/index.html), viewModel: [ChatbotViewModel](-chatbot-view-model/index.html) = hiltViewModel(), openDrawer: () -&gt; [Unit](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-unit/index.html))



Composable, displays a ChatLog page that shows previously saved/completed cognitive behavioral therapy activities. This page includes the top app bar and content of the log.



#### Parameters


androidJvm

| | |
|---|---|
| modifier | Modifier to be applied to the root composable |
| logId | ID of the log to be displayed |
| onNavigateBack | Lambda to handle the navigation back |
| viewModel | ViewModel to fetch the log data |
| openDrawer | Lambda to open the navigation drawer |



