---
title: linkAccount
---
//[app](../../../index.html)/[com.zurtar.mhma.auth](../index.html)/[AccountServiceImplementation](index.html)/[linkAccount](link-account.html)



# linkAccount



[androidJvm]\
open override fun [linkAccount](link-account.html)(email: [String](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-string/index.html), password: [String](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-string/index.html), onResult: ([Throwable](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-throwable/index.html)?) -&gt; [Unit](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-unit/index.html))



Link the current account to a new email and password. This allows the user to associate an email and password with their account.



#### Parameters


androidJvm

| | |
|---|---|
| email | The email address to link. |
| password | The password to associate with the email. |
| onResult | The callback function to handle the result of linking the account. |



