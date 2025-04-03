---
title: MonthHeader
---
//[app](../../index.html)/[com.zurtar.mhma.analytics](index.html)/[MonthHeader](-month-header.html)



# MonthHeader



[androidJvm]\




@[Composable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Composable.html)



fun [MonthHeader](-month-header.html)(month: [YearMonth](https://developer.android.com/reference/kotlin/java/time/YearMonth.html), daysOfWeek: [List](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.collections/-list/index.html)&lt;[DayOfWeek](https://developer.android.com/reference/kotlin/java/time/DayOfWeek.html)&gt;, animateScrollToMonth: suspend ([YearMonth](https://developer.android.com/reference/kotlin/java/time/YearMonth.html)) -&gt; [Unit](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-unit/index.html))



Displays a header for the month containing navigation buttons and the name of the current month.



#### Parameters


androidJvm

| | |
|---|---|
| month | The current month to display. |
| daysOfWeek | List of days of the week to display in the header. |
| animateScrollToMonth | A function to animate scrolling to a specific month. |



