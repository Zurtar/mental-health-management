---
title: BiWeeklyEvaluationUiState
---
//[app](../../../index.html)/[com.zurtar.mhma.mood](../index.html)/[BiWeeklyEvaluationUiState](index.html)



# BiWeeklyEvaluationUiState



[androidJvm]\
data class [BiWeeklyEvaluationUiState](index.html)(val biWeeklyEntry: [BiWeeklyEvaluationEntry](../../com.zurtar.mhma.data.models/-bi-weekly-evaluation-entry/index.html) = BiWeeklyEvaluationEntry(), val questionResponse: [MutableList](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.collections/-mutable-list/index.html)&lt;[Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)&gt; = (0..16).map { x -&gt; x * 0 }.toMutableList(), val page: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html) = 0)

UI state for Bi-Weekly Evaluation.



## Constructors


| | |
|---|---|
| [BiWeeklyEvaluationUiState](-bi-weekly-evaluation-ui-state.html) | [androidJvm]<br>constructor(biWeeklyEntry: [BiWeeklyEvaluationEntry](../../com.zurtar.mhma.data.models/-bi-weekly-evaluation-entry/index.html) = BiWeeklyEvaluationEntry(), questionResponse: [MutableList](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.collections/-mutable-list/index.html)&lt;[Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)&gt; = (0..16).map { x -&gt; x * 0 }.toMutableList(), page: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html) = 0) |


## Properties


| Name | Summary |
|---|---|
| [biWeeklyEntry](bi-weekly-entry.html) | [androidJvm]<br>val [biWeeklyEntry](bi-weekly-entry.html): [BiWeeklyEvaluationEntry](../../com.zurtar.mhma.data.models/-bi-weekly-evaluation-entry/index.html)<br>The current [BiWeeklyEvaluationEntry](../../com.zurtar.mhma.data.models/-bi-weekly-evaluation-entry/index.html) for the user. |
| [page](page.html) | [androidJvm]<br>val [page](page.html): [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html) = 0<br>The current page of the bi-weekly evaluation. |
| [questionResponse](question-response.html) | [androidJvm]<br>val [questionResponse](question-response.html): [MutableList](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.collections/-mutable-list/index.html)&lt;[Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)&gt;<br>The list of responses to the evaluation questions. |
