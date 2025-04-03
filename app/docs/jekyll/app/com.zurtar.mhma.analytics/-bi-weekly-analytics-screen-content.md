---
title: BiWeeklyAnalyticsScreenContent
---
//[app](../../index.html)/[com.zurtar.mhma.analytics](index.html)/[BiWeeklyAnalyticsScreenContent](-bi-weekly-analytics-screen-content.html)



# BiWeeklyAnalyticsScreenContent



[androidJvm]\




@[Composable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Composable.html)



fun [BiWeeklyAnalyticsScreenContent](-bi-weekly-analytics-screen-content.html)(modifier: [Modifier](https://developer.android.com/reference/kotlin/androidx/compose/ui/Modifier.html) = Modifier, viewModel: [BiWeeklyAnalyticsViewModel](-bi-weekly-analytics-view-model/index.html) = hiltViewModel(), onNavigateToSummaryDialog: ([BiWeeklyEvaluationEntry](../com.zurtar.mhma.data.models/-bi-weekly-evaluation-entry/index.html)?) -&gt; [Unit](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-unit/index.html))



A composable function that displays the Bi-Weekly Analytics screen. It uses `TabbedContent` to present different sections such as the mood graph and evaluation history. The content is dynamically updated based on the data from the ViewModel.



#### Parameters


androidJvm

| | |
|---|---|
| modifier | The modifier to be applied to the root composable. |
| viewModel | The ViewModel that provides the UI state and manages the bi-weekly evaluations. |
| onNavigateToSummaryDialog | A lambda function that handles navigation to a summary dialog for each evaluation entry. |



