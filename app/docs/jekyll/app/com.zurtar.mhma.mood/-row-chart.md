---
title: RowChart
---
//[app](../../index.html)/[com.zurtar.mhma.mood](index.html)/[RowChart](-row-chart.html)



# RowChart



[androidJvm]\




@[Composable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Composable.html)



fun [RowChart](-row-chart.html)(modifier: [Modifier](https://developer.android.com/reference/kotlin/androidx/compose/ui/Modifier.html) = Modifier, score: [String](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-string/index.html), severity: [String](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-string/index.html))



Composable function that displays a row with a score and its corresponding severity.



This function creates a horizontal row containing the score and its corresponding severity. It is used within the `ScoreChart` to display individual score ranges.



#### Parameters


androidJvm

| | |
|---|---|
| modifier | The modifier to be applied to the Row. |
| score | The score value to be displayed. |
| severity | The severity level corresponding to the score. |



