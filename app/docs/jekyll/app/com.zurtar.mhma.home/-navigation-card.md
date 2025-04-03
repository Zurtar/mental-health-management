---
title: NavigationCard
---
//[app](../../index.html)/[com.zurtar.mhma.home](index.html)/[NavigationCard](-navigation-card.html)



# NavigationCard



[androidJvm]\




@[Composable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Composable.html)



fun [NavigationCard](-navigation-card.html)(text: [String](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-string/index.html), icon: [ImageVector](https://developer.android.com/reference/kotlin/androidx/compose/ui/graphics/vector/ImageVector.html), navigate: () -&gt; [Unit](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-unit/index.html))



Composable function that creates a navigation card with an icon, text, and a forward arrow button. The card is clickable and triggers the provided navigation action when clicked.



#### Parameters


androidJvm

| | |
|---|---|
| text | The text to be displayed on the card. |
| icon | The icon to be displayed next to the text. |
| navigate | The lambda function that will be invoked when the card is clicked, typically used for navigation. |



