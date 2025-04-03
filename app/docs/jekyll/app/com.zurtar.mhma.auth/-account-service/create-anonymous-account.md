---
title: createAnonymousAccount
---
//[app](../../../index.html)/[com.zurtar.mhma.auth](../index.html)/[AccountService](index.html)/[createAnonymousAccount](create-anonymous-account.html)



# createAnonymousAccount



[androidJvm]\
abstract fun [createAnonymousAccount](create-anonymous-account.html)(onResult: ([Throwable](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-throwable/index.html)?) -&gt; [Unit](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-unit/index.html))



Create an anonymous account for the user. This signs the user in anonymously without requiring email or password.



#### Parameters


androidJvm

| | |
|---|---|
| onResult | The callback function to handle the result of the account creation.     It receives an optional [Throwable](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-throwable/index.html) indicating the error, if any. |



