---
title: toDate
---
//[app](../../index.html)/[com.zurtar.mhma.analytics](index.html)/[toDate](to-date.html)



# toDate



[androidJvm]\
fun [LocalDate](https://developer.android.com/reference/kotlin/java/time/LocalDate.html).[toDate](to-date.html)(): [Date](https://developer.android.com/reference/kotlin/java/util/Date.html)



Extension function to convert a [LocalDate](https://developer.android.com/reference/kotlin/java/time/LocalDate.html) to a [Date](https://developer.android.com/reference/kotlin/java/util/Date.html). This function converts the [LocalDate](https://developer.android.com/reference/kotlin/java/time/LocalDate.html) to a [Date](https://developer.android.com/reference/kotlin/java/util/Date.html) by first setting it to the start of the day, then converting it to an Instant and finally to a [Date](https://developer.android.com/reference/kotlin/java/util/Date.html) object.



#### Return



The corresponding [Date](https://developer.android.com/reference/kotlin/java/util/Date.html) representation of the [LocalDate](https://developer.android.com/reference/kotlin/java/time/LocalDate.html).



