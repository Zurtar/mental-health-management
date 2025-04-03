---
title: EmotionRating
---
//[app](../../index.html)/[com.zurtar.mhma.mood](index.html)/[EmotionRating](-emotion-rating.html)



# EmotionRating



[androidJvm]\




@[Composable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Composable.html)



fun [EmotionRating](-emotion-rating.html)(dailyEntry: [DailyEvaluationEntry](../com.zurtar.mhma.data/-daily-evaluation-entry/index.html), updateIntensity: ([Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html), [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)) -&gt; [Unit](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-unit/index.html))



Composable that displays a card allowing the user to rate the intensity of the emotions they selected. Users can slide a scale from 1-10 to indicate how strongly they are feeling each emotion.



#### Parameters


androidJvm

| | |
|---|---|
| dailyEntry | The current daily evaluation entry containing user responses. |
| updateIntensity | A function to update the intensity of a selected emotion. |



