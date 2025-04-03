---
title: com.zurtar.mhma.data.models
---
//[app](../../index.html)/[com.zurtar.mhma.data.models](index.html)



# Package-level declarations



## Types


| Name | Summary |
|---|---|
| [BiWeeklyEvaluationEntry](-bi-weekly-evaluation-entry/index.html) | [androidJvm]<br>@Serializable<br>data class [BiWeeklyEvaluationEntry](-bi-weekly-evaluation-entry/index.html)(var depressionScore: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html) = -1, var anxietyScore: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html) = -1, var depressionResults: [String](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-string/index.html) = &quot;&quot;, var anxietyResults: [String](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-string/index.html) = &quot;&quot;, var dateCompleted: [Date](https://developer.android.com/reference/kotlin/java/util/Date.html)? = null)<br>Represents the result of a bi-weekly mood evaluation. |
| [DateSerializer](-date-serializer/index.html) | [androidJvm]<br>object [DateSerializer](-date-serializer/index.html) : KSerializer&lt;[Date](https://developer.android.com/reference/kotlin/java/util/Date.html)&gt; <br>Custom serializer for `Date` objects to convert them into `String` during serialization and back to `Date` during deserialization. Needed for Firestore integration |
