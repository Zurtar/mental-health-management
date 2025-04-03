---
title: DailyEvaluationCalendar
---
//[app](../../index.html)/[com.zurtar.mhma.analytics](index.html)/[DailyEvaluationCalendar](-daily-evaluation-calendar.html)



# DailyEvaluationCalendar



[androidJvm]\




@[Composable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Composable.html)



fun [DailyEvaluationCalendar](-daily-evaluation-calendar.html)(modifier: [Modifier](https://developer.android.com/reference/kotlin/androidx/compose/ui/Modifier.html) = Modifier, evaluations: [List](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.collections/-list/index.html)&lt;[DailyEvaluationEntry](../com.zurtar.mhma.data/-daily-evaluation-entry/index.html)&gt;, selectedDate: [LocalDate](https://developer.android.com/reference/kotlin/java/time/LocalDate.html)?, onDateSelect: ([LocalDate](https://developer.android.com/reference/kotlin/java/time/LocalDate.html)?) -&gt; [Unit](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-unit/index.html))



Displays a calendar for daily evaluations and allows the user to select a date.



#### Parameters


androidJvm

| | |
|---|---|
| modifier | A [Modifier](https://developer.android.com/reference/kotlin/androidx/compose/ui/Modifier.html) to customize the UI. |
| evaluations | A list of [DailyEvaluationEntry](../com.zurtar.mhma.data/-daily-evaluation-entry/index.html) to display on the calendar. |
| selectedDate | The currently selected date in the calendar. |
| onDateSelect | A lambda function that handles the date selection. |



