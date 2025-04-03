---
title: DateSerializer
---
//[app](../../../index.html)/[com.zurtar.mhma.data.models](../index.html)/[DateSerializer](index.html)



# DateSerializer



[androidJvm]\
object [DateSerializer](index.html) : KSerializer&lt;[Date](https://developer.android.com/reference/kotlin/java/util/Date.html)&gt; 

Custom serializer for `Date` objects to convert them into `String` during serialization and back to `Date` during deserialization. Needed for Firestore integration



The serializer stores the `Date` as a string representing the time in milliseconds since the epoch.



Example: `Date("2021-12-31T12:59:59")` will be serialized to `"1638364799000"`.



This serializer is used by `BiWeeklyEvaluationEntry` to handle the `dateCompleted` property.



## Properties


| Name | Summary |
|---|---|
| [descriptor](descriptor.html) | [androidJvm]<br>open override val [descriptor](descriptor.html): SerialDescriptor |


## Functions


| Name | Summary |
|---|---|
| [deserialize](deserialize.html) | [androidJvm]<br>open override fun [deserialize](deserialize.html)(decoder: Decoder): [Date](https://developer.android.com/reference/kotlin/java/util/Date.html) |
| [serialize](serialize.html) | [androidJvm]<br>open override fun [serialize](serialize.html)(encoder: Encoder, value: [Date](https://developer.android.com/reference/kotlin/java/util/Date.html)) |
