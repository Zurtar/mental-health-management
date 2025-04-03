---
title: MoodEvaluationScreen
---
//[app](../../index.html)/[com.zurtar.mhma.mood](index.html)/[MoodEvaluationScreen](-mood-evaluation-screen.html)



# MoodEvaluationScreen



[androidJvm]\




@[Composable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Composable.html)



fun [MoodEvaluationScreen](-mood-evaluation-screen.html)(modifier: [Modifier](https://developer.android.com/reference/kotlin/androidx/compose/ui/Modifier.html) = Modifier, viewModel: [EvaluationMenuViewModel](-evaluation-menu-view-model/index.html) = hiltViewModel(), openDrawer: () -&gt; [Unit](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-unit/index.html), onNavigateToDaily: () -&gt; [Unit](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-unit/index.html), onNavigateToBiWeekly: () -&gt; [Unit](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-unit/index.html))



Composable that displays the main Mood Evaluation screen with a top app bar and navigation options.



#### Parameters


androidJvm

| | |
|---|---|
| modifier | Modifier for customizations to the layout. |
| viewModel | The ViewModel to provide the UI state for mood evaluations. |
| openDrawer | A function to open the navigation drawer. |
| onNavigateToDaily | A function to navigate to the daily evaluation screen. |
| onNavigateToBiWeekly | A function to navigate to the bi-weekly evaluation screen. |



