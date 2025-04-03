---
title: QuestionResponse
---
//[app](../../index.html)/[com.zurtar.mhma.mood](index.html)/[QuestionResponse](-question-response.html)



# QuestionResponse



[androidJvm]\




@[Composable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Composable.html)



fun [QuestionResponse](-question-response.html)(radioOptions: [List](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.collections/-list/index.html)&lt;[String](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-string/index.html)&gt;, selectedOption: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html), onSelect: ([Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)) -&gt; [Unit](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-unit/index.html))



Composable function to display a list of radio button options for a question.



displays a list of radio options and allows the user to select one. selected option is visually highlighted, and the `onSelect` lambda is called whenever an option is clicked.



#### Parameters


androidJvm

| | |
|---|---|
| radioOptions | A list of strings representing the options available to the user. |
| selectedOption | The index of the currently selected option. This will determine     which option is visually highlighted. |
| onSelect | A lambda function to handle the selection of an option. It takes the     index of the selected option as a parameter. |



