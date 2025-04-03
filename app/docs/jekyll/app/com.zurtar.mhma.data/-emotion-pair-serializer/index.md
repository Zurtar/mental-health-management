---
title: EmotionPairSerializer
---
//[app](../../../index.html)/[com.zurtar.mhma.data](../index.html)/[EmotionPairSerializer](index.html)



# EmotionPairSerializer



[androidJvm]\
object [EmotionPairSerializer](index.html) : KSerializer&lt;[Pair](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-pair/index.html)&lt;[String](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-string/index.html), [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)&gt;&gt; 

Serializer for the [Pair](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-pair/index.html) of emotions and their intensities, allowing serialization and deserialization. This serializer encodes the emotion name as a string and its intensity as a float.



## Properties


| Name | Summary |
|---|---|
| [descriptor](descriptor.html) | [androidJvm]<br>open override val [descriptor](descriptor.html): SerialDescriptor |


## Functions


| Name | Summary |
|---|---|
| [deserialize](deserialize.html) | [androidJvm]<br>open override fun [deserialize](deserialize.html)(decoder: Decoder): [Pair](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-pair/index.html)&lt;[String](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-string/index.html), [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)&gt;<br>Deserializes a [Pair](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-pair/index.html) of emotion and intensity from a serialized format. |
| [serialize](serialize.html) | [androidJvm]<br>open override fun [serialize](serialize.html)(encoder: Encoder, value: [Pair](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-pair/index.html)&lt;[String](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-string/index.html), [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)&gt;)<br>Serializes a [Pair](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-pair/index.html) of emotion and intensity. |
