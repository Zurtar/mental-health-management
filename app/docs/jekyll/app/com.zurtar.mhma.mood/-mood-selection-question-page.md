---
title: MoodSelectionQuestionPage
---
//[app](../../index.html)/[com.zurtar.mhma.mood](index.html)/[MoodSelectionQuestionPage](-mood-selection-question-page.html)



# MoodSelectionQuestionPage



[androidJvm]\




@[Composable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Composable.html)



fun [MoodSelectionQuestionPage](-mood-selection-question-page.html)(modifier: [Modifier](https://developer.android.com/reference/kotlin/androidx/compose/ui/Modifier.html) = Modifier, dailyEntry: [DailyEvaluationEntry](../com.zurtar.mhma.data/-daily-evaluation-entry/index.html), emotionSelect: ([String](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-string/index.html)) -&gt; [Unit](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-unit/index.html), updateEmotion: ([ImageVector](https://developer.android.com/reference/kotlin/androidx/compose/ui/graphics/vector/ImageVector.html)) -&gt; [Unit](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-unit/index.html))



Composable that represents the page where the user selects their emotion for the daily mood evaluation. Displays emotion selection cards to allow users to choose their current emotional state.



#### Parameters


androidJvm

| | |
|---|---|
| modifier | Modifier for customizations to the layout. |
| dailyEntry | The current daily evaluation entry containing user responses. |
| emotionSelect | A function to handle the selection of an emotion. |
| updateEmotion | A function to update the selected mood emoji. |



