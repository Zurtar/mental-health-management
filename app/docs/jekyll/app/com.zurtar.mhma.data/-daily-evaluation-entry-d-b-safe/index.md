---
title: DailyEvaluationEntryDBSafe
---
//[app](../../../index.html)/[com.zurtar.mhma.data](../index.html)/[DailyEvaluationEntryDBSafe](index.html)



# DailyEvaluationEntryDBSafe



[androidJvm]\
@Serializable



data class [DailyEvaluationEntryDBSafe](index.html)(val selectedEmotions: [List](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.collections/-list/index.html)&lt;[String](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-string/index.html)&gt; = listOf(), val emotionIntensities: [List](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.collections/-list/index.html)&lt;[Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)&gt; = listOf(0f, 0f, 0f), val emotionsMap: [Map](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.collections/-map/index.html)&lt;[String](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-string/index.html), [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)&gt; = mapOf(), val stressLevel: [String](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-string/index.html) = &quot;default_initial&quot;, val strongestEmotionFirst: [String](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-string/index.html)? = null, val strongestEmotionSecond: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html) = 0.0f, val dateCompleted: [Date](https://developer.android.com/reference/kotlin/java/util/Date.html)? = null)

Represents a daily mood evaluation entry that is stored safely in the database. This includes the selected emotions, their intensities, a map of emotions to their values, the stress level, the strongest emotions (first and second), and the date the entry was completed.



## Constructors


| | |
|---|---|
| [DailyEvaluationEntryDBSafe](-daily-evaluation-entry-d-b-safe.html) | [androidJvm]<br>constructor(selectedEmotions: [List](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.collections/-list/index.html)&lt;[String](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-string/index.html)&gt; = listOf(), emotionIntensities: [List](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.collections/-list/index.html)&lt;[Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)&gt; = listOf(0f, 0f, 0f), emotionsMap: [Map](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.collections/-map/index.html)&lt;[String](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-string/index.html), [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)&gt; = mapOf(), stressLevel: [String](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-string/index.html) = &quot;default_initial&quot;, strongestEmotionFirst: [String](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-string/index.html)? = null, strongestEmotionSecond: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html) = 0.0f, dateCompleted: [Date](https://developer.android.com/reference/kotlin/java/util/Date.html)? = null) |


## Properties


| Name | Summary |
|---|---|
| [dateCompleted](date-completed.html) | [androidJvm]<br>@Serializable(with = [DateSerializer::class](../../com.zurtar.mhma.data.models/-date-serializer/index.html))<br>val [dateCompleted](date-completed.html): [Date](https://developer.android.com/reference/kotlin/java/util/Date.html)? = null<br>The date when the mood evaluation was completed. |
| [emotionIntensities](emotion-intensities.html) | [androidJvm]<br>val [emotionIntensities](emotion-intensities.html): [List](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.collections/-list/index.html)&lt;[Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)&gt;<br>The intensities of the selected emotions. |
| [emotionsMap](emotions-map.html) | [androidJvm]<br>val [emotionsMap](emotions-map.html): [Map](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.collections/-map/index.html)&lt;[String](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-string/index.html), [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)&gt;<br>A map containing the emotions and their respective intensities. |
| [selectedEmotions](selected-emotions.html) | [androidJvm]<br>val [selectedEmotions](selected-emotions.html): [List](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.collections/-list/index.html)&lt;[String](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-string/index.html)&gt;<br>The list of emotions selected by the user. |
| [stressLevel](stress-level.html) | [androidJvm]<br>val [stressLevel](stress-level.html): [String](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-string/index.html)<br>The user's reported stress level. |
| [strongestEmotionFirst](strongest-emotion-first.html) | [androidJvm]<br>val [strongestEmotionFirst](strongest-emotion-first.html): [String](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-string/index.html)? = null<br>The first strongest emotion (stored in Firestore). |
| [strongestEmotionSecond](strongest-emotion-second.html) | [androidJvm]<br>val [strongestEmotionSecond](strongest-emotion-second.html): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html) = 0.0f<br>The intensity of the second strongest emotion. |


## Functions


| Name | Summary |
|---|---|
| [toNormal](../to-normal.html) | [androidJvm]<br>fun [DailyEvaluationEntryDBSafe](index.html).[toNormal](../to-normal.html)(): [DailyEvaluationEntry](../-daily-evaluation-entry/index.html)<br>Converts a [DailyEvaluationEntryDBSafe](index.html) to a [DailyEvaluationEntry](../-daily-evaluation-entry/index.html). |
