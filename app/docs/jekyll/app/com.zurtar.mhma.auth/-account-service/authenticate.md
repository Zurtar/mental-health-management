---
title: authenticate
---
//[app](../../../index.html)/[com.zurtar.mhma.auth](../index.html)/[AccountService](index.html)/[authenticate](authenticate.html)



# authenticate



[androidJvm]\
abstract fun [authenticate](authenticate.html)(email: [String](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-string/index.html), password: [String](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-string/index.html), onResult: ([Throwable](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-throwable/index.html)?) -&gt; [Unit](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-unit/index.html))



Authenticate a user with their email and password. This logs the user in by validating their credentials against Firebase.



#### Parameters


androidJvm

| | |
|---|---|
| email | The email address of the user. |
| password | The password associated with the user's email. |
| onResult | The callback function to handle the result of the authentication.     It receives an optional [Throwable](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-throwable/index.html) indicating the error, if any. |



