---
title: BiWeeklyEvaluationEntry
---
//[app](../../../index.html)/[com.zurtar.mhma.data.models](../index.html)/[BiWeeklyEvaluationEntry](index.html)



# BiWeeklyEvaluationEntry



[androidJvm]\
@Serializable



data class [BiWeeklyEvaluationEntry](index.html)(var depressionScore: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html) = -1, var anxietyScore: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html) = -1, var depressionResults: [String](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-string/index.html) = &quot;&quot;, var anxietyResults: [String](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-string/index.html) = &quot;&quot;, var dateCompleted: [Date](https://developer.android.com/reference/kotlin/java/util/Date.html)? = null)

Represents the result of a bi-weekly mood evaluation.



This data class holds the depression and anxiety scores, along with their corresponding results and the date the evaluation was completed. It is used for tracking mood evaluations over a bi-weekly period.



## Constructors


| | |
|---|---|
| [BiWeeklyEvaluationEntry](-bi-weekly-evaluation-entry.html) | [androidJvm]<br>constructor(depressionScore: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html) = -1, anxietyScore: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html) = -1, depressionResults: [String](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-string/index.html) = &quot;&quot;, anxietyResults: [String](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-string/index.html) = &quot;&quot;, dateCompleted: [Date](https://developer.android.com/reference/kotlin/java/util/Date.html)? = null) |


## Properties


| Name | Summary |
|---|---|
| [anxietyResults](anxiety-results.html) | [androidJvm]<br>var [anxietyResults](anxiety-results.html): [String](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-string/index.html)<br>The results or message related to the anxiety evaluation. |
| [anxietyScore](anxiety-score.html) | [androidJvm]<br>var [anxietyScore](anxiety-score.html): [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)<br>The score for the anxiety evaluation. Default is -1 if not set. |
| [dateCompleted](date-completed.html) | [androidJvm]<br>@Serializable(with = [DateSerializer::class](../-date-serializer/index.html))<br>var [dateCompleted](date-completed.html): [Date](https://developer.android.com/reference/kotlin/java/util/Date.html)?<br>The date the evaluation was completed, represented as a nullable `Date` object. |
| [depressionResults](depression-results.html) | [androidJvm]<br>var [depressionResults](depression-results.html): [String](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-string/index.html)<br>The results or message related to the depression evaluation. |
| [depressionScore](depression-score.html) | [androidJvm]<br>var [depressionScore](depression-score.html): [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)<br>The score for the depression evaluation. Default is -1 if not set. |
