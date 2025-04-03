---
title: DailyAnalyticsViewModel
---
//[app](../../../index.html)/[com.zurtar.mhma.analytics](../index.html)/[DailyAnalyticsViewModel](index.html)



# DailyAnalyticsViewModel



[androidJvm]\
data class [DailyAnalyticsViewModel](index.html)@Injectconstructor(dailyMoodRepository: [DailyMoodRepository](../../com.zurtar.mhma.data/-daily-mood-repository/index.html)) : [ViewModel](https://developer.android.com/reference/kotlin/androidx/lifecycle/ViewModel.html)

ViewModel responsible for managing daily mood analytics. It handles data retrieval, state management, and user interactions.



## Constructors


| | |
|---|---|
| [DailyAnalyticsViewModel](-daily-analytics-view-model.html) | [androidJvm]<br>@Inject<br>constructor(dailyMoodRepository: [DailyMoodRepository](../../com.zurtar.mhma.data/-daily-mood-repository/index.html)) |


## Properties


| Name | Summary |
|---|---|
| [uiState](ui-state.html) | [androidJvm]<br>val [uiState](ui-state.html): StateFlow&lt;[DailyAnalyticsUIState](../-daily-analytics-u-i-state/index.html)&gt; |


## Functions


| Name | Summary |
|---|---|
| [addCloseable](../../com.zurtar.mhma.util/-navigation-view-model/index.html#383812252%2FFunctions%2F-451970049) | [androidJvm]<br>open fun [addCloseable](../../com.zurtar.mhma.util/-navigation-view-model/index.html#383812252%2FFunctions%2F-451970049)(closeable: [AutoCloseable](https://developer.android.com/reference/kotlin/java/lang/AutoCloseable.html))<br>fun [addCloseable](../../com.zurtar.mhma.util/-navigation-view-model/index.html#1722490497%2FFunctions%2F-451970049)(key: [String](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-string/index.html), closeable: [AutoCloseable](https://developer.android.com/reference/kotlin/java/lang/AutoCloseable.html)) |
| [getCloseable](../../com.zurtar.mhma.util/-navigation-view-model/index.html#1102255800%2FFunctions%2F-451970049) | [androidJvm]<br>fun &lt;[T](../../com.zurtar.mhma.util/-navigation-view-model/index.html#1102255800%2FFunctions%2F-451970049) : [AutoCloseable](https://developer.android.com/reference/kotlin/java/lang/AutoCloseable.html)&gt; [getCloseable](../../com.zurtar.mhma.util/-navigation-view-model/index.html#1102255800%2FFunctions%2F-451970049)(key: [String](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-string/index.html)): [T](../../com.zurtar.mhma.util/-navigation-view-model/index.html#1102255800%2FFunctions%2F-451970049)? |
| [selectDate](select-date.html) | [androidJvm]<br>fun [selectDate](select-date.html)(localDate: [LocalDate](https://developer.android.com/reference/kotlin/java/time/LocalDate.html)?)<br>Updates the UI state with the selected date. |
