---
title: AccountService
---
//[app](../../../index.html)/[com.zurtar.mhma.auth](../index.html)/[AccountService](index.html)



# AccountService

interface [AccountService](index.html)

Interface defining the various authentication and account management services.



#### Inheritors


| |
|---|
| [AccountServiceImplementation](../-account-service-implementation/index.html) |


## Functions


| Name | Summary |
|---|---|
| [authenticate](authenticate.html) | [androidJvm]<br>abstract fun [authenticate](authenticate.html)(email: [String](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-string/index.html), password: [String](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-string/index.html), onResult: ([Throwable](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-throwable/index.html)?) -&gt; [Unit](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-unit/index.html))<br>Authenticate a user with their email and password. This logs the user in by validating their credentials against Firebase. |
| [createAccount](create-account.html) | [androidJvm]<br>abstract fun [createAccount](create-account.html)(email: [String](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-string/index.html), password: [String](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-string/index.html), onResult: ([Throwable](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-throwable/index.html)?) -&gt; [Unit](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-unit/index.html))<br>Create a new account using the provided email and password. This registers a new user in Firebase with the given credentials. |
| [createAnonymousAccount](create-anonymous-account.html) | [androidJvm]<br>abstract fun [createAnonymousAccount](create-anonymous-account.html)(onResult: ([Throwable](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-throwable/index.html)?) -&gt; [Unit](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-unit/index.html))<br>Create an anonymous account for the user. This signs the user in anonymously without requiring email or password. |
| [linkAccount](link-account.html) | [androidJvm]<br>abstract fun [linkAccount](link-account.html)(email: [String](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-string/index.html), password: [String](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-string/index.html), onResult: ([Throwable](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-throwable/index.html)?) -&gt; [Unit](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-unit/index.html))<br>Link the current account to a new email and password. This operation links an email and password to the current user account. |
| [logout](logout.html) | [androidJvm]<br>abstract fun [logout](logout.html)(onResult: ([Throwable](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-throwable/index.html)?) -&gt; [Unit](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-unit/index.html))<br>Log out the currently authenticated user. This signs the user out of Firebase and invalidates their session. |
