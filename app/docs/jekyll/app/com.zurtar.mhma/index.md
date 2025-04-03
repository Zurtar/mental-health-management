---
title: com.zurtar.mhma
---
//[app](../../index.html)/[com.zurtar.mhma](index.html)



# Package-level declarations



## Types


| Name | Summary |
|---|---|
| [MainActivity](-main-activity/index.html) | [androidJvm]<br>class [MainActivity](-main-activity/index.html) : [ComponentActivity](https://developer.android.com/reference/kotlin/androidx/activity/ComponentActivity.html) |
| [MainApplication](-main-application/index.html) | [androidJvm]<br>class [MainApplication](-main-application/index.html) : [Application](https://developer.android.com/reference/kotlin/android/app/Application.html) |
| [Navigation](-navigation/index.html) | [androidJvm]<br>class [Navigation](-navigation/index.html)(navController: [NavHostController](https://developer.android.com/reference/kotlin/androidx/navigation/NavHostController.html))<br>A helper class for navigating between different screens in the app using `NavHostController`. This class provides various navigation methods for different routes within the app. |


## Functions


| Name | Summary |
|---|---|
| [MyApp](-my-app.html) | [androidJvm]<br>@[Composable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Composable.html)<br>fun [MyApp](-my-app.html)() |
| [NavGraph](-nav-graph.html) | [androidJvm]<br>@[Composable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Composable.html)<br>fun [NavGraph](-nav-graph.html)(modifier: [Modifier](https://developer.android.com/reference/kotlin/androidx/compose/ui/Modifier.html) = Modifier, navController: [NavHostController](https://developer.android.com/reference/kotlin/androidx/navigation/NavHostController.html) = rememberNavController(), drawerState: [DrawerState](https://developer.android.com/reference/kotlin/androidx/compose/material3/DrawerState.html) = rememberDrawerState(initialValue = DrawerValue.Closed), coroutineScope: CoroutineScope = rememberCoroutineScope(), viewModel: [NavigationViewModel](../com.zurtar.mhma.util/-navigation-view-model/index.html) = hiltViewModel(), startDestination: [Any](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-any/index.html) = &quot;Home&quot;, navActions: [Navigation](-navigation/index.html) = remember(navController) {         Navigation(navController)     }) |
