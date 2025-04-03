---
title: BiWeeklyHistoricalAnalytics
---
//[app](../../index.html)/[com.zurtar.mhma.analytics](index.html)/[BiWeeklyHistoricalAnalytics](-bi-weekly-historical-analytics.html)



# BiWeeklyHistoricalAnalytics



[androidJvm]\




@[Composable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Composable.html)



fun [BiWeeklyHistoricalAnalytics](-bi-weekly-historical-analytics.html)(biWeeklyEvaluations: [List](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.collections/-list/index.html)&lt;[BiWeeklyEvaluationEntry](../com.zurtar.mhma.data.models/-bi-weekly-evaluation-entry/index.html)&gt;, onNavigateToSummaryDialog: ([BiWeeklyEvaluationEntry](../com.zurtar.mhma.data.models/-bi-weekly-evaluation-entry/index.html)?) -&gt; [Unit](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-unit/index.html))



A composable function that displays a list of bi-weekly evaluations, categorized by the current week, last week, and previous weeks. It also computes the severity for depression and anxiety scores and displays them accordingly.



#### Parameters


androidJvm

| | |
|---|---|
| biWeeklyEvaluations | The list of bi-weekly evaluation entries to display. |
| onNavigateToSummaryDialog | A lambda function that handles navigation to a summary dialog for each evaluation. |



