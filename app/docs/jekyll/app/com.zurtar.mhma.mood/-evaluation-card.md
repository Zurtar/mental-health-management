---
title: EvaluationCard
---
//[app](../../index.html)/[com.zurtar.mhma.mood](index.html)/[EvaluationCard](-evaluation-card.html)



# EvaluationCard



[androidJvm]\




@[Composable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Composable.html)



fun [EvaluationCard](-evaluation-card.html)(modifier: [Modifier](https://developer.android.com/reference/kotlin/androidx/compose/ui/Modifier.html) = Modifier, title: [String](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-string/index.html), desc: [String](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-string/index.html), completed: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false, checkMark: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html) = 1, onNavigate: () -&gt; [Unit](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-unit/index.html))



Composable for an individual evaluation card, including a checkmark if completed.



#### Parameters


androidJvm

| | |
|---|---|
| modifier | Modifier for customizations to the layout. |
| title | The title of the evaluation (e.g., Daily Evaluation, Bi-Weekly Evaluation). |
| desc | A description for the evaluation (can be left empty). |
| completed | Boolean flag to indicate if the evaluation is completed. |
| checkMark | An integer to control whether a checkmark is shown. |
| onNavigate | A function to navigate when the card is clicked. |



