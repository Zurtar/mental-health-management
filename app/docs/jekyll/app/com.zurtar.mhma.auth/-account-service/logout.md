---
title: logout
---
//[app](../../../index.html)/[com.zurtar.mhma.auth](../index.html)/[AccountService](index.html)/[logout](logout.html)



# logout



[androidJvm]\
abstract fun [logout](logout.html)(onResult: ([Throwable](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-throwable/index.html)?) -&gt; [Unit](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-unit/index.html))



Log out the currently authenticated user. This signs the user out of Firebase and invalidates their session.



#### Parameters


androidJvm

| | |
|---|---|
| onResult | The callback function to handle the result of the logout.     It receives an optional [Throwable](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-throwable/index.html) indicating the error, if any. |



