---
title: DailyEvaluationEntry
---
//[app](../../../index.html)/[com.zurtar.mhma.data](../index.html)/[DailyEvaluationEntry](index.html)



# DailyEvaluationEntry



[androidJvm]\
@Serializable



data class [DailyEvaluationEntry](index.html)(val selectedEmotions: [List](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.collections/-list/index.html)&lt;[String](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-string/index.html)&gt; = listOf(), val emotionIntensities: [List](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.collections/-list/index.html)&lt;[Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)&gt; = listOf(0f, 0f, 0f), val emotionsMap: [Map](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.collections/-map/index.html)&lt;[String](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-string/index.html), [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)&gt; = mapOf(), val stressLevel: [String](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-string/index.html) = &quot;default_initial&quot;, val strongestEmotion: [Pair](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-pair/index.html)&lt;[String](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-string/index.html), [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)&gt; = Pair(&quot;&quot;, 0f), val dateCompleted: [Date](https://developer.android.com/reference/kotlin/java/util/Date.html)? = null)

Represents a daily mood evaluation entry that is used in the application. This includes the selected emotions, their intensities, a map of emotions to their values, the stress level, the strongest emotion (as a pair), and the date the entry was completed.



## Constructors


| | |
|---|---|
| [DailyEvaluationEntry](-daily-evaluation-entry.html) | [androidJvm]<br>constructor(selectedEmotions: [List](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.collections/-list/index.html)&lt;[String](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-string/index.html)&gt; = listOf(), emotionIntensities: [List](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.collections/-list/index.html)&lt;[Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)&gt; = listOf(0f, 0f, 0f), emotionsMap: [Map](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.collections/-map/index.html)&lt;[String](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-string/index.html), [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)&gt; = mapOf(), stressLevel: [String](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-string/index.html) = &quot;default_initial&quot;, strongestEmotion: [Pair](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-pair/index.html)&lt;[String](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-string/index.html), [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)&gt; = Pair(&quot;&quot;, 0f), dateCompleted: [Date](https://developer.android.com/reference/kotlin/java/util/Date.html)? = null) |


## Properties


| Name | Summary |
|---|---|
| [dateCompleted](date-completed.html) | [androidJvm]<br>@Serializable(with = [DateSerializer::class](../../com.zurtar.mhma.data.models/-date-serializer/index.html))<br>val [dateCompleted](date-completed.html): [Date](https://developer.android.com/reference/kotlin/java/util/Date.html)? = null<br>The date when the mood evaluation was completed. |
| [emotionIntensities](emotion-intensities.html) | [androidJvm]<br>val [emotionIntensities](emotion-intensities.html): [List](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.collections/-list/index.html)&lt;[Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)&gt;<br>The intensities of the selected emotions. |
| [emotionsMap](emotions-map.html) | [androidJvm]<br>val [emotionsMap](emotions-map.html): [Map](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.collections/-map/index.html)&lt;[String](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-string/index.html), [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)&gt;<br>A map containing the emotions and their respective intensities. |
| [selectedEmotions](selected-emotions.html) | [androidJvm]<br>val [selectedEmotions](selected-emotions.html): [List](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.collections/-list/index.html)&lt;[String](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-string/index.html)&gt;<br>The list of emotions selected by the user. |
| [stressLevel](stress-level.html) | [androidJvm]<br>val [stressLevel](stress-level.html): [String](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-string/index.html)<br>The user's reported stress level. |
| [strongestEmotion](strongest-emotion.html) | [androidJvm]<br>val [strongestEmotion](strongest-emotion.html): [Pair](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-pair/index.html)&lt;[String](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-string/index.html), [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)&gt;<br>The strongest emotion and its intensity as a pair. |


## Functions


| Name | Summary |
|---|---|
| [toDBSafe](../to-d-b-safe.html) | [androidJvm]<br>fun [DailyEvaluationEntry](index.html).[toDBSafe](../to-d-b-safe.html)(): [DailyEvaluationEntryDBSafe](../-daily-evaluation-entry-d-b-safe/index.html)<br>Converts a [DailyEvaluationEntry](index.html) to a [DailyEvaluationEntryDBSafe](../-daily-evaluation-entry-d-b-safe/index.html) object for safe storage in Firestore. |
