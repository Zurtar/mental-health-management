---
title: DailyMoodEvaluationScreen
---
//[app](../../index.html)/[com.zurtar.mhma.mood](index.html)/[DailyMoodEvaluationScreen](-daily-mood-evaluation-screen.html)



# DailyMoodEvaluationScreen



[androidJvm]\




@[Composable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Composable.html)



fun [DailyMoodEvaluationScreen](-daily-mood-evaluation-screen.html)(modifier: [Modifier](https://developer.android.com/reference/kotlin/androidx/compose/ui/Modifier.html) = Modifier, viewModel: [DailyEvaluationViewModel](-daily-evaluation-view-model/index.html) = hiltViewModel(), openDrawer: () -&gt; [Unit](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-unit/index.html), onNavigateToAnalytics: ([Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)) -&gt; [Unit](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-unit/index.html), onNavigateToJournal: () -&gt; [Unit](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-unit/index.html))



Composable for displaying the Daily Mood Evaluation screen with a top app bar and navigation options.



#### Parameters


androidJvm

| | |
|---|---|
| modifier | Modifier for customizations to the layout. |
| viewModel | The ViewModel to provide the UI state for the daily mood evaluation. |
| openDrawer | A function to open the navigation drawer. |
| onNavigateToAnalytics | A function to navigate to the analytics screen with an optional parameter for the selected entry. |
| onNavigateToJournal | A function to navigate to the journal screen. |



