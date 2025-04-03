---
title: BiWeeklyAnalyticsViewModel
---
//[app](../../../index.html)/[com.zurtar.mhma.analytics](../index.html)/[BiWeeklyAnalyticsViewModel](index.html)



# BiWeeklyAnalyticsViewModel



[androidJvm]\
class [BiWeeklyAnalyticsViewModel](index.html)@Injectconstructor(biWeeklyMoodRepository: [BiWeeklyMoodRepository](../../com.zurtar.mhma.data/-bi-weekly-mood-repository/index.html)) : [ViewModel](https://developer.android.com/reference/kotlin/androidx/lifecycle/ViewModel.html)

ViewModel responsible for managing BiWeekly mood analytics. It fetches BiWeekly mood data, processes it, and provides it to the UI.



## Constructors


| | |
|---|---|
| [BiWeeklyAnalyticsViewModel](-bi-weekly-analytics-view-model.html) | [androidJvm]<br>@Inject<br>constructor(biWeeklyMoodRepository: [BiWeeklyMoodRepository](../../com.zurtar.mhma.data/-bi-weekly-mood-repository/index.html)) |


## Properties


| Name | Summary |
|---|---|
| [uiState](ui-state.html) | [androidJvm]<br>val [uiState](ui-state.html): StateFlow&lt;[BiWeeklyAnalyticsUIState](../-bi-weekly-analytics-u-i-state/index.html)&gt; |


## Functions


| Name | Summary |
|---|---|
| [addCloseable](../../com.zurtar.mhma.util/-navigation-view-model/index.html#383812252%2FFunctions%2F-451970049) | [androidJvm]<br>open fun [addCloseable](../../com.zurtar.mhma.util/-navigation-view-model/index.html#383812252%2FFunctions%2F-451970049)(closeable: [AutoCloseable](https://developer.android.com/reference/kotlin/java/lang/AutoCloseable.html))<br>fun [addCloseable](../../com.zurtar.mhma.util/-navigation-view-model/index.html#1722490497%2FFunctions%2F-451970049)(key: [String](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-string/index.html), closeable: [AutoCloseable](https://developer.android.com/reference/kotlin/java/lang/AutoCloseable.html)) |
| [getCloseable](../../com.zurtar.mhma.util/-navigation-view-model/index.html#1102255800%2FFunctions%2F-451970049) | [androidJvm]<br>fun &lt;[T](../../com.zurtar.mhma.util/-navigation-view-model/index.html#1102255800%2FFunctions%2F-451970049) : [AutoCloseable](https://developer.android.com/reference/kotlin/java/lang/AutoCloseable.html)&gt; [getCloseable](../../com.zurtar.mhma.util/-navigation-view-model/index.html#1102255800%2FFunctions%2F-451970049)(key: [String](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-string/index.html)): [T](../../com.zurtar.mhma.util/-navigation-view-model/index.html#1102255800%2FFunctions%2F-451970049)? |
