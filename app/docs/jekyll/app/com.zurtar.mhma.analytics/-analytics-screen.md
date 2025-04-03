---
title: AnalyticsScreen
---
//[app](../../index.html)/[com.zurtar.mhma.analytics](index.html)/[AnalyticsScreen](-analytics-screen.html)



# AnalyticsScreen



[androidJvm]\




@[Composable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Composable.html)



fun [AnalyticsScreen](-analytics-screen.html)(modifier: [Modifier](https://developer.android.com/reference/kotlin/androidx/compose/ui/Modifier.html) = Modifier, id: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html) = 0, openDrawer: () -&gt; [Unit](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-unit/index.html), onNavigateToSummaryDialog: ([BiWeeklyEvaluationEntry](../com.zurtar.mhma.data.models/-bi-weekly-evaluation-entry/index.html)?) -&gt; [Unit](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-unit/index.html))



Displays the main Analytics screen, which includes navigation and a top bar.



#### Parameters


androidJvm

| | |
|---|---|
| modifier | Modifier to be applied to the root composable. |
| id | The current tab index, defaulting to 0. |
| openDrawer | A lambda function to open the navigation drawer. |
| onNavigateToSummaryDialog | A lambda function to handle navigation to the summary dialog for a selected bi-weekly evaluation entry. |



