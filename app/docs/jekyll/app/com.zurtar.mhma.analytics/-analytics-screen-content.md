---
title: AnalyticsScreenContent
---
//[app](../../index.html)/[com.zurtar.mhma.analytics](index.html)/[AnalyticsScreenContent](-analytics-screen-content.html)



# AnalyticsScreenContent



[androidJvm]\




@[Composable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Composable.html)



fun [AnalyticsScreenContent](-analytics-screen-content.html)(modifier: [Modifier](https://developer.android.com/reference/kotlin/androidx/compose/ui/Modifier.html) = Modifier, id: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html) = 0, onNavigateToSummaryDialog: ([BiWeeklyEvaluationEntry](../com.zurtar.mhma.data.models/-bi-weekly-evaluation-entry/index.html)?) -&gt; [Unit](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-unit/index.html))



Displays the content of the Analytics screen, including a tabbed interface for Quick and BiWeekly tabs.



#### Parameters


androidJvm

| | |
|---|---|
| modifier | Modifier to be applied to the content layout. |
| id | The current tab index, defaulting to 0. |
| onNavigateToSummaryDialog | A lambda function to handle navigation to the summary dialog for a selected bi-weekly evaluation entry. |



