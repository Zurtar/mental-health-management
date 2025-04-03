---
title: AppHorizontalCalendar
---
//[app](../../index.html)/[com.zurtar.mhma.analytics](index.html)/[AppHorizontalCalendar](-app-horizontal-calendar.html)



# AppHorizontalCalendar



[androidJvm]\




@[Composable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Composable.html)



fun [AppHorizontalCalendar](-app-horizontal-calendar.html)(modifier: [Modifier](https://developer.android.com/reference/kotlin/androidx/compose/ui/Modifier.html) = Modifier, evaluations: [List](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.collections/-list/index.html)&lt;[DailyEvaluationEntry](../com.zurtar.mhma.data/-daily-evaluation-entry/index.html)&gt;, selectedDate: [LocalDate](https://developer.android.com/reference/kotlin/java/time/LocalDate.html)? = null, onDateSelect: ([LocalDate](https://developer.android.com/reference/kotlin/java/time/LocalDate.html)?) -&gt; [Unit](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-unit/index.html))



Displays a horizontal calendar with daily evaluations. Each date can be selected, and associated mood colors are displayed based on the evaluations. Allows navigation through months and displays mood-based color indicators.



#### Parameters


androidJvm

| | |
|---|---|
| modifier | Optional modifier to customize the layout. |
| evaluations | List of daily evaluations to display on the calendar. |
| selectedDate | The currently selected date, if any. |
| onDateSelect | Callback triggered when a date is selected. |



