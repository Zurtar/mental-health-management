---
title: com.zurtar.mhma.home
---
//[app](../../index.html)/[com.zurtar.mhma.home](index.html)



# Package-level declarations



## Functions


| Name | Summary |
|---|---|
| [getGreetingFromDate](get-greeting-from-date.html) | [androidJvm]<br>fun [getGreetingFromDate](get-greeting-from-date.html)(date: [Date](https://developer.android.com/reference/kotlin/java/util/Date.html)): [String](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-string/index.html)<br>Returns a greeting based on the time of day. It checks the hour of the day and returns a corresponding greeting: &quot;Good Morning!&quot;, &quot;Good Afternoon!&quot;, or &quot;Good Evening!&quot;. |
| [HomeScreen](-home-screen.html) | [androidJvm]<br>@[Composable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Composable.html)<br>fun [HomeScreen](-home-screen.html)(modifier: [Modifier](https://developer.android.com/reference/kotlin/androidx/compose/ui/Modifier.html) = Modifier, openDrawer: () -&gt; [Unit](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-unit/index.html), onNavigateToMoodEvaluation: () -&gt; [Unit](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-unit/index.html), onNavigateToJournal: () -&gt; [Unit](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-unit/index.html), onNavigateToChatbot: () -&gt; [Unit](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-unit/index.html), onNavigateToAnalytics: () -&gt; [Unit](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-unit/index.html))<br>Composable for the Home screen, which serves as the main dashboard. It includes navigation options to different sections of the app such as mood evaluation, journaling, chatbot, and analytics. |
| [NavigationCard](-navigation-card.html) | [androidJvm]<br>@[Composable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Composable.html)<br>fun [NavigationCard](-navigation-card.html)(text: [String](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-string/index.html), icon: [ImageVector](https://developer.android.com/reference/kotlin/androidx/compose/ui/graphics/vector/ImageVector.html), navigate: () -&gt; [Unit](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-unit/index.html))<br>Composable function that creates a navigation card with an icon, text, and a forward arrow button. The card is clickable and triggers the provided navigation action when clicked. |
