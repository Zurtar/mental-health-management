---
title: linkAccount
---
//[app](../../../index.html)/[com.zurtar.mhma.auth](../index.html)/[AccountService](index.html)/[linkAccount](link-account.html)



# linkAccount



[androidJvm]\
abstract fun [linkAccount](link-account.html)(email: [String](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-string/index.html), password: [String](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-string/index.html), onResult: ([Throwable](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-throwable/index.html)?) -&gt; [Unit](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-unit/index.html))



Link the current account to a new email and password. This operation links an email and password to the current user account.



#### Parameters


androidJvm

| | |
|---|---|
| email | The email address to link. |
| password | The password to associate with the email. |
| onResult | The callback function to handle the result of the linking.     It receives an optional [Throwable](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-throwable/index.html) indicating the error, if any. |



