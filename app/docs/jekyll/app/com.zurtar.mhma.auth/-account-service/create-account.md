---
title: createAccount
---
//[app](../../../index.html)/[com.zurtar.mhma.auth](../index.html)/[AccountService](index.html)/[createAccount](create-account.html)



# createAccount



[androidJvm]\
abstract fun [createAccount](create-account.html)(email: [String](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-string/index.html), password: [String](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-string/index.html), onResult: ([Throwable](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-throwable/index.html)?) -&gt; [Unit](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-unit/index.html))



Create a new account using the provided email and password. This registers a new user in Firebase with the given credentials.



#### Parameters


androidJvm

| | |
|---|---|
| email | The email address of the user. |
| password | The password to associate with the account. |
| onResult | The callback function to handle the result of account creation.     It receives an optional [Throwable](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-throwable/index.html) indicating the error, if any. |



