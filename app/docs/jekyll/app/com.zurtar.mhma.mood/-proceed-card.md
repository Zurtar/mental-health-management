---
title: ProceedCard
---
//[app](../../index.html)/[com.zurtar.mhma.mood](index.html)/[ProceedCard](-proceed-card.html)



# ProceedCard



[androidJvm]\




@[Composable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Composable.html)



fun [ProceedCard](-proceed-card.html)(text: [String](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-string/index.html), navigate: () -&gt; [Unit](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-unit/index.html))



Composable function that displays a clickable card to navigate to another screen.



This function creates a card with a text label and an icon button. When clicked, it triggers the provided `navigate` lambda to navigate to a different screen.



#### Parameters


androidJvm

| | |
|---|---|
| text | The text to be displayed inside the card. |
| navigate | A lambda function to handle the navigation when the card is clicked. |



