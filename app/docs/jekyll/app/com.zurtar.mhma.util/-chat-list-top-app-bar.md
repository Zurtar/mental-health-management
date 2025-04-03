---
title: ChatListTopAppBar
---
//[app](../../index.html)/[com.zurtar.mhma.util](index.html)/[ChatListTopAppBar](-chat-list-top-app-bar.html)



# ChatListTopAppBar



[androidJvm]\




@[Composable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Composable.html)



fun [ChatListTopAppBar](-chat-list-top-app-bar.html)(openDrawer: () -&gt; [Unit](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-unit/index.html), onNavigateToChatbot: () -&gt; [Unit](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-unit/index.html))



Composable function for the top app bar on the chat list screen.



This app bar includes:



- 
   A title &quot;Chat List&quot;.
- 
   A menu icon that invokes [openDrawer](-chat-list-top-app-bar.html).
- 
   An action button that navigates back to the chatbot screen using [onNavigateToChatbot](-chat-list-top-app-bar.html).




#### Parameters


androidJvm

| | |
|---|---|
| openDrawer | Lambda function to open the navigation drawer when the menu icon is clicked. |
| onNavigateToChatbot | Lambda function to navigate to the chatbot screen when the button is clicked. |



