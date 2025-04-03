---
title: com.zurtar.mhma.util
---
//[app](../../index.html)/[com.zurtar.mhma.util](index.html)



# Package-level declarations



## Types


| Name | Summary |
|---|---|
| [NavDrawerUiState](-nav-drawer-ui-state/index.html) | [androidJvm]<br>data class [NavDrawerUiState](-nav-drawer-ui-state/index.html)(val isLoggedIn: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false)<br>Represents the UI state for the navigation drawer. |
| [NavigationViewModel](-navigation-view-model/index.html) | [androidJvm]<br>class [NavigationViewModel](-navigation-view-model/index.html)@Injectconstructor(userRepository: [UserRepository](../com.zurtar.mhma.data/-user-repository/index.html)) : [ViewModel](https://developer.android.com/reference/kotlin/androidx/lifecycle/ViewModel.html)<br>ViewModel responsible for managing the navigation drawer UI state, specifically tracking the user's login state, and providing it to the UI layer. |


## Functions


| Name | Summary |
|---|---|
| [AnalyticsTopAppBar](-analytics-top-app-bar.html) | [androidJvm]<br>@[Composable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Composable.html)<br>fun [AnalyticsTopAppBar](-analytics-top-app-bar.html)(openDrawer: () -&gt; [Unit](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-unit/index.html)) |
| [AppModalDrawer](-app-modal-drawer.html) | [androidJvm]<br>@[Composable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Composable.html)<br>fun [AppModalDrawer](-app-modal-drawer.html)(drawerState: [DrawerState](https://developer.android.com/reference/kotlin/androidx/compose/material3/DrawerState.html), currentRoute: [Any](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-any/index.html), navigationActions: [Navigation](../com.zurtar.mhma/-navigation/index.html), coroutineScope: CoroutineScope = rememberCoroutineScope(), content: @[Composable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Composable.html)() -&gt; [Unit](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-unit/index.html))<br>Composable that displays a modal drawer for navigation. |
| [ChatbotTopAppBar](-chatbot-top-app-bar.html) | [androidJvm]<br>@[Composable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Composable.html)<br>fun [ChatbotTopAppBar](-chatbot-top-app-bar.html)(openDrawer: () -&gt; [Unit](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-unit/index.html), onNavigateToChatList: () -&gt; [Unit](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-unit/index.html))<br>Composable function for the top app bar on the chatbot screen. |
| [ChatListTopAppBar](-chat-list-top-app-bar.html) | [androidJvm]<br>@[Composable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Composable.html)<br>fun [ChatListTopAppBar](-chat-list-top-app-bar.html)(openDrawer: () -&gt; [Unit](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-unit/index.html), onNavigateToChatbot: () -&gt; [Unit](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-unit/index.html))<br>Composable function for the top app bar on the chat list screen. |
| [ChatLogTopAppBar](-chat-log-top-app-bar.html) | [androidJvm]<br>@[Composable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Composable.html)<br>fun [ChatLogTopAppBar](-chat-log-top-app-bar.html)(openDrawer: () -&gt; [Unit](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-unit/index.html), onNavigateBack: () -&gt; [Unit](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-unit/index.html)) |
| [customDrawerShape](custom-drawer-shape.html) | [androidJvm]<br>fun [customDrawerShape](custom-drawer-shape.html)(): [Shape](https://developer.android.com/reference/kotlin/androidx/compose/ui/graphics/Shape.html)<br>Creates a custom shape for the navigation drawer. |
| [DefaultTopAppBar](-default-top-app-bar.html) | [androidJvm]<br>@[Composable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Composable.html)<br>fun [DefaultTopAppBar](-default-top-app-bar.html)(openDrawer: () -&gt; [Unit](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-unit/index.html))<br>Composable function that displays the top app bar for the home screen. |
| [JournalingTopAppBar](-journaling-top-app-bar.html) | [androidJvm]<br>@[Composable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Composable.html)<br>fun [JournalingTopAppBar](-journaling-top-app-bar.html)(openDrawer: () -&gt; [Unit](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-unit/index.html)) |
| [MoodEvaluationTopAppBar](-mood-evaluation-top-app-bar.html) | [androidJvm]<br>@[Composable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Composable.html)<br>fun [MoodEvaluationTopAppBar](-mood-evaluation-top-app-bar.html)(openDrawer: () -&gt; [Unit](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-unit/index.html)) |
