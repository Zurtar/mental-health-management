---
title: BiWeeklyAnalyticsUIState
---
//[app](../../../index.html)/[com.zurtar.mhma.analytics](../index.html)/[BiWeeklyAnalyticsUIState](index.html)



# BiWeeklyAnalyticsUIState



[androidJvm]\
data class [BiWeeklyAnalyticsUIState](index.html)(val pastEvaluations: [List](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.collections/-list/index.html)&lt;[BiWeeklyEvaluationEntry](../../com.zurtar.mhma.data.models/-bi-weekly-evaluation-entry/index.html)&gt;? = null, val graphData: [List](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.collections/-list/index.html)&lt;LineData&gt;? = null)

Represents the UI state for BiWeekly mood analytics. This includes past BiWeekly evaluations and corresponding graph data.



## Constructors


| | |
|---|---|
| [BiWeeklyAnalyticsUIState](-bi-weekly-analytics-u-i-state.html) | [androidJvm]<br>constructor(pastEvaluations: [List](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.collections/-list/index.html)&lt;[BiWeeklyEvaluationEntry](../../com.zurtar.mhma.data.models/-bi-weekly-evaluation-entry/index.html)&gt;? = null, graphData: [List](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.collections/-list/index.html)&lt;LineData&gt;? = null) |


## Properties


| Name | Summary |
|---|---|
| [graphData](graph-data.html) | [androidJvm]<br>val [graphData](graph-data.html): [List](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.collections/-list/index.html)&lt;LineData&gt;? = null<br>A list of data points representing mood trends over time. |
| [pastEvaluations](past-evaluations.html) | [androidJvm]<br>val [pastEvaluations](past-evaluations.html): [List](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.collections/-list/index.html)&lt;[BiWeeklyEvaluationEntry](../../com.zurtar.mhma.data.models/-bi-weekly-evaluation-entry/index.html)&gt;? = null<br>A list of past BiWeekly mood evaluations. |
