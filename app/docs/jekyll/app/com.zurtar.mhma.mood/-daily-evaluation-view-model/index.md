---
title: DailyEvaluationViewModel
---
//[app](../../../index.html)/[com.zurtar.mhma.mood](../index.html)/[DailyEvaluationViewModel](index.html)



# DailyEvaluationViewModel



[androidJvm]\
class [DailyEvaluationViewModel](index.html)@Injectconstructor(dailyMoodRepository: [DailyMoodRepository](../../com.zurtar.mhma.data/-daily-mood-repository/index.html)) : [ViewModel](https://developer.android.com/reference/kotlin/androidx/lifecycle/ViewModel.html)

ViewModel for handling daily mood evaluation operations.



This ViewModel manages the state of the daily evaluation, including submitting the evaluation, navigating through the pages, and updating the selected emotions and their intensities.



## Constructors


| | |
|---|---|
| [DailyEvaluationViewModel](-daily-evaluation-view-model.html) | [androidJvm]<br>@Inject<br>constructor(dailyMoodRepository: [DailyMoodRepository](../../com.zurtar.mhma.data/-daily-mood-repository/index.html)) |


## Properties


| Name | Summary |
|---|---|
| [uiState](ui-state.html) | [androidJvm]<br>val [uiState](ui-state.html): StateFlow&lt;[DailyEvaluationUiState](../-daily-evaluation-ui-state/index.html)&gt; |


## Functions


| Name | Summary |
|---|---|
| [addCloseable](../../com.zurtar.mhma.util/-navigation-view-model/index.html#383812252%2FFunctions%2F-451970049) | [androidJvm]<br>open fun [addCloseable](../../com.zurtar.mhma.util/-navigation-view-model/index.html#383812252%2FFunctions%2F-451970049)(closeable: [AutoCloseable](https://developer.android.com/reference/kotlin/java/lang/AutoCloseable.html))<br>fun [addCloseable](../../com.zurtar.mhma.util/-navigation-view-model/index.html#1722490497%2FFunctions%2F-451970049)(key: [String](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-string/index.html), closeable: [AutoCloseable](https://developer.android.com/reference/kotlin/java/lang/AutoCloseable.html)) |
| [emotionSelect](emotion-select.html) | [androidJvm]<br>fun [emotionSelect](emotion-select.html)(emotion: [String](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-string/index.html))<br>Toggles the selection of an emotion for the daily evaluation. |
| [getCloseable](../../com.zurtar.mhma.util/-navigation-view-model/index.html#1102255800%2FFunctions%2F-451970049) | [androidJvm]<br>fun &lt;[T](../../com.zurtar.mhma.util/-navigation-view-model/index.html#1102255800%2FFunctions%2F-451970049) : [AutoCloseable](https://developer.android.com/reference/kotlin/java/lang/AutoCloseable.html)&gt; [getCloseable](../../com.zurtar.mhma.util/-navigation-view-model/index.html#1102255800%2FFunctions%2F-451970049)(key: [String](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-string/index.html)): [T](../../com.zurtar.mhma.util/-navigation-view-model/index.html#1102255800%2FFunctions%2F-451970049)? |
| [onBack](on-back.html) | [androidJvm]<br>fun [onBack](on-back.html)()<br>Navigates to the previous page of the daily evaluation. |
| [onNext](on-next.html) | [androidJvm]<br>fun [onNext](on-next.html)()<br>Navigates to the next page of the daily evaluation. |
| [onSubmit](on-submit.html) | [androidJvm]<br>fun [onSubmit](on-submit.html)()<br>Submits the current daily evaluation by recording the emotions and their intensities. |
| [updateEmotion](update-emotion.html) | [androidJvm]<br>fun [updateEmotion](update-emotion.html)(emoji: [ImageVector](https://developer.android.com/reference/kotlin/androidx/compose/ui/graphics/vector/ImageVector.html))<br>Updates the stress level based on the selected emoji. |
| [updateIntensity](update-intensity.html) | [androidJvm]<br>fun [updateIntensity](update-intensity.html)(value: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html), index: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html))<br>Updates the intensity of a selected emotion. |
| [updateStrongestEmotion](update-strongest-emotion.html) | [androidJvm]<br>fun [updateStrongestEmotion](update-strongest-emotion.html)()<br>Updates the strongest emotion based on the current selection and intensity. |
