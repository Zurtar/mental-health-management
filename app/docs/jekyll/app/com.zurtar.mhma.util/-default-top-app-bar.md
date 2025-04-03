---
title: DefaultTopAppBar
---
//[app](../../index.html)/[com.zurtar.mhma.util](index.html)/[DefaultTopAppBar](-default-top-app-bar.html)



# DefaultTopAppBar



[androidJvm]\




@[Composable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Composable.html)



fun [DefaultTopAppBar](-default-top-app-bar.html)(openDrawer: () -&gt; [Unit](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-unit/index.html))



Composable function that displays the top app bar for the home screen.



This app bar includes:



- 
   A title &quot;VibeCheck: Home&quot;.
- 
   A navigation menu icon that invokes [openDrawer](-default-top-app-bar.html) when clicked.




Styled using the primary color scheme for visual consistency.



#### Parameters


androidJvm

| | |
|---|---|
| openDrawer | Lambda function to open the navigation drawer when the menu icon is clicked. |



