---
title: QuestionCard
---
//[app](../../index.html)/[com.zurtar.mhma.mood](index.html)/[QuestionCard](-question-card.html)



# QuestionCard



[androidJvm]\




@[Composable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Composable.html)



fun [QuestionCard](-question-card.html)(modifier: [Modifier](https://developer.android.com/reference/kotlin/androidx/compose/ui/Modifier.html) = Modifier, num: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html), question: [String](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-string/index.html), selectedOption: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html), onSelect: ([Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)) -&gt; [Unit](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-unit/index.html))



Composable that displays a question card for a specific question, allowing the user to select an answer.



#### Parameters


androidJvm

| | |
|---|---|
| modifier | Modifier for customizations to the layout. |
| num | The index of the current question. |
| question | The text of the question. |
| selectedOption | The current selected option for the question. |
| onSelect | Function to handle selecting an answer. |



