---
title: DailyEvaluationUiState
---
//[app](../../../index.html)/[com.zurtar.mhma.mood](../index.html)/[DailyEvaluationUiState](index.html)



# DailyEvaluationUiState



[androidJvm]\
data class [DailyEvaluationUiState](index.html)(val dailyEntry: [DailyEvaluationEntry](../../com.zurtar.mhma.data/-daily-evaluation-entry/index.html) = DailyEvaluationEntry(), val isSubmitted: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html) = 0, val page: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html) = 0)

UI state for Daily Evaluation.



## Constructors


| | |
|---|---|
| [DailyEvaluationUiState](-daily-evaluation-ui-state.html) | [androidJvm]<br>constructor(dailyEntry: [DailyEvaluationEntry](../../com.zurtar.mhma.data/-daily-evaluation-entry/index.html) = DailyEvaluationEntry(), isSubmitted: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html) = 0, page: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html) = 0) |


## Properties


| Name | Summary |
|---|---|
| [dailyEntry](daily-entry.html) | [androidJvm]<br>val [dailyEntry](daily-entry.html): [DailyEvaluationEntry](../../com.zurtar.mhma.data/-daily-evaluation-entry/index.html)<br>The current [DailyEvaluationEntry](../../com.zurtar.mhma.data/-daily-evaluation-entry/index.html) for the user. |
| [isSubmitted](is-submitted.html) | [androidJvm]<br>val [isSubmitted](is-submitted.html): [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html) = 0<br>Flag indicating if the daily entry has been submitted. |
| [page](page.html) | [androidJvm]<br>val [page](page.html): [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html) = 0<br>The current page of the evaluation. |
