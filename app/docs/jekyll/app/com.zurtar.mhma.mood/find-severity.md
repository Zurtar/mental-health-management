---
title: findSeverity
---
//[app](../../index.html)/[com.zurtar.mhma.mood](index.html)/[findSeverity](find-severity.html)



# findSeverity



[androidJvm]\




@[Composable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Composable.html)



fun [findSeverity](find-severity.html)(score: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html), evalType: [String](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-string/index.html)): [String](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-string/index.html)



Finds the severity level based on the given score and evaluation type (anxiety or depression).



This function checks the provided score against predefined ranges for either anxiety or depression, depending on the evaluation type, and returns the corresponding severity level.



#### Return



The severity level corresponding to the given score and evaluation type.



#### Parameters


androidJvm

| | |
|---|---|
| score | The score value to be checked. |
| evalType | The type of evaluation, either &quot;anxiety&quot; or &quot;depression&quot;. |



