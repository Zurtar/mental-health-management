---
title: ScoreChart
---
//[app](../../index.html)/[com.zurtar.mhma.mood](index.html)/[ScoreChart](-score-chart.html)



# ScoreChart



[androidJvm]\




@[Composable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Composable.html)



fun [ScoreChart](-score-chart.html)(score: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html), scores: [List](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.collections/-list/index.html)&lt;[String](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-string/index.html)&gt;, severities: [List](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.collections/-list/index.html)&lt;[String](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-string/index.html)&gt;)



Composable function that displays a score chart to show the user's score and severity.



This function shows the score and severity levels for both depression and anxiety based on the provided score. The score is compared to predefined ranges to determine the severity level.



#### Parameters


androidJvm

| | |
|---|---|
| score | The score value to be displayed. |
| scores | A list of string values representing the different score ranges. |
| severities | A list of string values representing the corresponding severity levels     for each score range. |



