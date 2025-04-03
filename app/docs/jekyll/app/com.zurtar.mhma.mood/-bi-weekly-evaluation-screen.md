---
title: BiWeeklyEvaluationScreen
---
//[app](../../index.html)/[com.zurtar.mhma.mood](index.html)/[BiWeeklyEvaluationScreen](-bi-weekly-evaluation-screen.html)



# BiWeeklyEvaluationScreen



[androidJvm]\




@[Composable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Composable.html)



fun [BiWeeklyEvaluationScreen](-bi-weekly-evaluation-screen.html)(modifier: [Modifier](https://developer.android.com/reference/kotlin/androidx/compose/ui/Modifier.html) = Modifier, viewModel: [BiWeeklyEvaluationViewModel](-bi-weekly-evaluation-view-model/index.html) = hiltViewModel(), openDrawer: () -&gt; [Unit](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-unit/index.html), onNavigateToAnalytics: ([Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)) -&gt; [Unit](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-unit/index.html))



Composable that displays the BiWeekly Evaluation screen with a series of questions about mental health over the past two weeks. The user can navigate through the questions and submit the responses at the end.



#### Parameters


androidJvm

| | |
|---|---|
| modifier | Modifier for customizations to the layout. |
| viewModel | The view model for the bi-weekly evaluation. |
| openDrawer | A function to open the app's side drawer. |
| onNavigateToAnalytics | A function to navigate to the analytics screen. |



