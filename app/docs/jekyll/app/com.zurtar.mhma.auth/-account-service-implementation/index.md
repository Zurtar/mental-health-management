---
title: AccountServiceImplementation
---
//[app](../../../index.html)/[com.zurtar.mhma.auth](../index.html)/[AccountServiceImplementation](index.html)



# AccountServiceImplementation



[androidJvm]\
@Singleton



class [AccountServiceImplementation](index.html)@Injectconstructor(firebaseAuth: FirebaseAuth, firestore: FirebaseFirestore) : [AccountService](../-account-service/index.html)

Implementation of [AccountService](../-account-service/index.html) that interacts with Firebase for user authentication and account management.



## Constructors


| | |
|---|---|
| [AccountServiceImplementation](-account-service-implementation.html) | [androidJvm]<br>@Inject<br>constructor(firebaseAuth: FirebaseAuth, firestore: FirebaseFirestore) |


## Functions


| Name | Summary |
|---|---|
| [authenticate](authenticate.html) | [androidJvm]<br>open override fun [authenticate](authenticate.html)(email: [String](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-string/index.html), password: [String](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-string/index.html), onResult: ([Throwable](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-throwable/index.html)?) -&gt; [Unit](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-unit/index.html))<br>Authenticate a user with their email and password. This logs the user into their account. |
| [createAccount](create-account.html) | [androidJvm]<br>open override fun [createAccount](create-account.html)(email: [String](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-string/index.html), password: [String](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-string/index.html), onResult: ([Throwable](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-throwable/index.html)?) -&gt; [Unit](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-unit/index.html))<br>Create a new account using the provided email and password. This registers a new user with the specified email and password. |
| [createAnonymousAccount](create-anonymous-account.html) | [androidJvm]<br>open override fun [createAnonymousAccount](create-anonymous-account.html)(onResult: ([Throwable](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-throwable/index.html)?) -&gt; [Unit](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-unit/index.html))<br>Create an anonymous account for the user. This signs the user in anonymously. |
| [linkAccount](link-account.html) | [androidJvm]<br>open override fun [linkAccount](link-account.html)(email: [String](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-string/index.html), password: [String](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-string/index.html), onResult: ([Throwable](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-throwable/index.html)?) -&gt; [Unit](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-unit/index.html))<br>Link the current account to a new email and password. This allows the user to associate an email and password with their account. |
| [logout](logout.html) | [androidJvm]<br>open override fun [logout](logout.html)(onResult: ([Throwable](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-throwable/index.html)?) -&gt; [Unit](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-unit/index.html))<br>Log out the currently authenticated user. This signs the user out and clears their session. |
