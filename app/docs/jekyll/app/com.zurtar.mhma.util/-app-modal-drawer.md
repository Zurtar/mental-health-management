---
title: AppModalDrawer
---
//[app](../../index.html)/[com.zurtar.mhma.util](index.html)/[AppModalDrawer](-app-modal-drawer.html)



# AppModalDrawer



[androidJvm]\




@[Composable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Composable.html)



fun [AppModalDrawer](-app-modal-drawer.html)(drawerState: [DrawerState](https://developer.android.com/reference/kotlin/androidx/compose/material3/DrawerState.html), currentRoute: [Any](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-any/index.html), navigationActions: [Navigation](../com.zurtar.mhma/-navigation/index.html), coroutineScope: CoroutineScope = rememberCoroutineScope(), content: @[Composable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Composable.html)() -&gt; [Unit](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-unit/index.html))



Composable that displays a modal drawer for navigation.



#### Parameters


androidJvm

| | |
|---|---|
| drawerState | State of the drawer, controls whether it's open or closed. |
| currentRoute | The current navigation route to highlight active section. |
| navigationActions | Actions to navigate to different parts of the app. |
| coroutineScope | CoroutineScope to manage drawer interactions. |
| content | Composable content to be displayed inside the main area of the app. |



