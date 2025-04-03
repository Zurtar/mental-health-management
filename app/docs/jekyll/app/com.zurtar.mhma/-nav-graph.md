---
title: NavGraph
---
//[app](../../index.html)/[com.zurtar.mhma](index.html)/[NavGraph](-nav-graph.html)



# NavGraph



[androidJvm]\




@[Composable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Composable.html)



fun [NavGraph](-nav-graph.html)(modifier: [Modifier](https://developer.android.com/reference/kotlin/androidx/compose/ui/Modifier.html) = Modifier, navController: [NavHostController](https://developer.android.com/reference/kotlin/androidx/navigation/NavHostController.html) = rememberNavController(), drawerState: [DrawerState](https://developer.android.com/reference/kotlin/androidx/compose/material3/DrawerState.html) = rememberDrawerState(initialValue = DrawerValue.Closed), coroutineScope: CoroutineScope = rememberCoroutineScope(), viewModel: [NavigationViewModel](../com.zurtar.mhma.util/-navigation-view-model/index.html) = hiltViewModel(), startDestination: [Any](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-any/index.html) = &quot;Home&quot;, navActions: [Navigation](-navigation/index.html) = remember(navController) {
        Navigation(navController)
    })



