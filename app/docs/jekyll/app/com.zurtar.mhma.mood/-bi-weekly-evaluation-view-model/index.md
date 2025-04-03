---
title: BiWeeklyEvaluationViewModel
---
//[app](../../../index.html)/[com.zurtar.mhma.mood](../index.html)/[BiWeeklyEvaluationViewModel](index.html)



# BiWeeklyEvaluationViewModel



[androidJvm]\
class [BiWeeklyEvaluationViewModel](index.html)@Injectconstructor(biWeeklyMoodRepository: [BiWeeklyMoodRepository](../../com.zurtar.mhma.data/-bi-weekly-mood-repository/index.html)) : [ViewModel](https://developer.android.com/reference/kotlin/androidx/lifecycle/ViewModel.html)

ViewModel for handling bi-weekly mood evaluation operations.



This ViewModel manages the state of the bi-weekly evaluation, including navigating through the pages, updating responses, and submitting the bi-weekly mood entry.



## Constructors


| | |
|---|---|
| [BiWeeklyEvaluationViewModel](-bi-weekly-evaluation-view-model.html) | [androidJvm]<br>@Inject<br>constructor(biWeeklyMoodRepository: [BiWeeklyMoodRepository](../../com.zurtar.mhma.data/-bi-weekly-mood-repository/index.html)) |


## Properties


| Name | Summary |
|---|---|
| [uiState](ui-state.html) | [androidJvm]<br>val [uiState](ui-state.html): StateFlow&lt;[BiWeeklyEvaluationUiState](../-bi-weekly-evaluation-ui-state/index.html)&gt; |


## Functions


| Name | Summary |
|---|---|
| [addCloseable](../../com.zurtar.mhma.util/-navigation-view-model/index.html#383812252%2FFunctions%2F-451970049) | [androidJvm]<br>open fun [addCloseable](../../com.zurtar.mhma.util/-navigation-view-model/index.html#383812252%2FFunctions%2F-451970049)(closeable: [AutoCloseable](https://developer.android.com/reference/kotlin/java/lang/AutoCloseable.html))<br>fun [addCloseable](../../com.zurtar.mhma.util/-navigation-view-model/index.html#1722490497%2FFunctions%2F-451970049)(key: [String](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-string/index.html), closeable: [AutoCloseable](https://developer.android.com/reference/kotlin/java/lang/AutoCloseable.html)) |
| [debugScore](debug-score.html) | [androidJvm]<br>fun [debugScore](debug-score.html)()<br>Debugging function that calculates and logs the depression and anxiety scores based on the responses. |
| [getCloseable](../../com.zurtar.mhma.util/-navigation-view-model/index.html#1102255800%2FFunctions%2F-451970049) | [androidJvm]<br>fun &lt;[T](../../com.zurtar.mhma.util/-navigation-view-model/index.html#1102255800%2FFunctions%2F-451970049) : [AutoCloseable](https://developer.android.com/reference/kotlin/java/lang/AutoCloseable.html)&gt; [getCloseable](../../com.zurtar.mhma.util/-navigation-view-model/index.html#1102255800%2FFunctions%2F-451970049)(key: [String](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-string/index.html)): [T](../../com.zurtar.mhma.util/-navigation-view-model/index.html#1102255800%2FFunctions%2F-451970049)? |
| [onBack](on-back.html) | [androidJvm]<br>fun [onBack](on-back.html)()<br>Decrements the page count. |
| [onNext](on-next.html) | [androidJvm]<br>fun [onNext](on-next.html)()<br>Increments the page count and triggers the submission if it's the last page. |
| [onSelect](on-select.html) | [androidJvm]<br>fun [onSelect](on-select.html)(selected: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html))<br>Updates the response for the current question. |
