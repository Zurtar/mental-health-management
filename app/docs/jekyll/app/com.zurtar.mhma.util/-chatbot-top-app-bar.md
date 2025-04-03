---
title: ChatbotTopAppBar
---
//[app](../../index.html)/[com.zurtar.mhma.util](index.html)/[ChatbotTopAppBar](-chatbot-top-app-bar.html)



# ChatbotTopAppBar



[androidJvm]\




@[Composable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Composable.html)



fun [ChatbotTopAppBar](-chatbot-top-app-bar.html)(openDrawer: () -&gt; [Unit](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-unit/index.html), onNavigateToChatList: () -&gt; [Unit](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-unit/index.html))



Composable function for the top app bar on the chatbot screen.



This app bar includes:



- 
   A title &quot;Chat With Zeke&quot;.
- 
   A menu icon that invokes [openDrawer](-chatbot-top-app-bar.html).
- 
   An action button that navigates to the chat list screen using [onNavigateToChatList](-chatbot-top-app-bar.html).




#### Parameters


androidJvm

| | |
|---|---|
| openDrawer | Lambda function to open the navigation drawer when the menu icon is clicked. |
| onNavigateToChatList | Lambda function to navigate to the chat list screen when the button is clicked. |



