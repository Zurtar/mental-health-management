---
title: DailyAnalyticsUIState
---
//[app](../../../index.html)/[com.zurtar.mhma.analytics](../index.html)/[DailyAnalyticsUIState](index.html)



# DailyAnalyticsUIState



[androidJvm]\
data class [DailyAnalyticsUIState](index.html)(val pastEvaluations: [List](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.collections/-list/index.html)&lt;[DailyEvaluationEntry](../../com.zurtar.mhma.data/-daily-evaluation-entry/index.html)&gt; = listOf(), val selectedDate: [LocalDate](https://developer.android.com/reference/kotlin/java/time/LocalDate.html)? = null)

Represents the UI state for daily mood analytics. This includes historical evaluations and the currently selected date.



## Constructors


| | |
|---|---|
| [DailyAnalyticsUIState](-daily-analytics-u-i-state.html) | [androidJvm]<br>constructor(pastEvaluations: [List](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.collections/-list/index.html)&lt;[DailyEvaluationEntry](../../com.zurtar.mhma.data/-daily-evaluation-entry/index.html)&gt; = listOf(), selectedDate: [LocalDate](https://developer.android.com/reference/kotlin/java/time/LocalDate.html)? = null) |


## Properties


| Name | Summary |
|---|---|
| [pastEvaluations](past-evaluations.html) | [androidJvm]<br>val [pastEvaluations](past-evaluations.html): [List](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.collections/-list/index.html)&lt;[DailyEvaluationEntry](../../com.zurtar.mhma.data/-daily-evaluation-entry/index.html)&gt;<br>A list of past daily mood evaluations. |
| [selectedDate](selected-date.html) | [androidJvm]<br>val [selectedDate](selected-date.html): [LocalDate](https://developer.android.com/reference/kotlin/java/time/LocalDate.html)? = null<br>The date currently selected by the user. |
