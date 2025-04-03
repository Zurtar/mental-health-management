---
title: BiWeeklyResult
---
//[app](../../index.html)/[com.zurtar.mhma.mood](index.html)/[BiWeeklyResult](-bi-weekly-result.html)



# BiWeeklyResult



[androidJvm]\




@[Composable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Composable.html)



fun [BiWeeklyResult](-bi-weekly-result.html)(modifier: [Modifier](https://developer.android.com/reference/kotlin/androidx/compose/ui/Modifier.html) = Modifier, evaluation: [BiWeeklyEvaluationEntry](../com.zurtar.mhma.data.models/-bi-weekly-evaluation-entry/index.html), onNavigateToAnalytics: ([Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)) -&gt; [Unit](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-unit/index.html))



Composable function that displays the result of a bi-weekly evaluation.



This function shows the user's depression and anxiety scores along with their severity results based on the PHQ-9 and GAD-7 questionnaires. It also provides options to proceed to evaluation summary or analytics.



#### Parameters


androidJvm

| | |
|---|---|
| modifier | The modifier to be applied to the outer Column. |
| evaluation | The evaluation result containing the user's depression and anxiety scores     along with the severity results. |
| onNavigateToAnalytics | A lambda function to handle navigation when the user proceeds     to the evaluation summary or analytics. |



