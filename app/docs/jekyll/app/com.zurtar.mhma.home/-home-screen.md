---
title: HomeScreen
---
//[app](../../index.html)/[com.zurtar.mhma.home](index.html)/[HomeScreen](-home-screen.html)



# HomeScreen



[androidJvm]\




@[Composable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Composable.html)



fun [HomeScreen](-home-screen.html)(modifier: [Modifier](https://developer.android.com/reference/kotlin/androidx/compose/ui/Modifier.html) = Modifier, openDrawer: () -&gt; [Unit](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-unit/index.html), onNavigateToMoodEvaluation: () -&gt; [Unit](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-unit/index.html), onNavigateToJournal: () -&gt; [Unit](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-unit/index.html), onNavigateToChatbot: () -&gt; [Unit](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-unit/index.html), onNavigateToAnalytics: () -&gt; [Unit](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-unit/index.html))



Composable for the Home screen, which serves as the main dashboard. It includes navigation options to different sections of the app such as mood evaluation, journaling, chatbot, and analytics.



#### Parameters


androidJvm

| | |
|---|---|
| modifier | Modifier to be applied to the composable. |
| openDrawer | A lambda function to open the navigation drawer. |
| onNavigateToMoodEvaluation | A lambda function for navigating to the mood evaluation screen. |
| onNavigateToJournal | A lambda function for navigating to the journal screen. |
| onNavigateToChatbot | A lambda function for navigating to the chatbot screen. |
| onNavigateToAnalytics | A lambda function for navigating to the analytics screen. |



