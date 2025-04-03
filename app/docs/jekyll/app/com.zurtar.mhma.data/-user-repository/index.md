---
title: UserRepository
---
//[app](../../../index.html)/[com.zurtar.mhma.data](../index.html)/[UserRepository](index.html)



# UserRepository



[androidJvm]\
@Singleton



class [UserRepository](index.html)@Injectconstructor(accountService: [AccountService](../../com.zurtar.mhma.auth/-account-service/index.html), firestore: FirebaseFirestore, firebaseAuth: FirebaseAuth)

Repository class that manages user-related operations such as authentication state changes, retrieving user data (like email and display name) from Firestore, and interacting with the FirebaseAuth service.



## Constructors


| | |
|---|---|
| [UserRepository](-user-repository.html) | [androidJvm]<br>@Inject<br>constructor(accountService: [AccountService](../../com.zurtar.mhma.auth/-account-service/index.html), firestore: FirebaseFirestore, firebaseAuth: FirebaseAuth) |


## Functions


| Name | Summary |
|---|---|
| [getAuthState](get-auth-state.html) | [androidJvm]<br>fun [getAuthState](get-auth-state.html)(): Flow&lt;[AuthState](../../com.zurtar.mhma.auth/-auth-state/index.html)&gt;<br>Exposes a Flow that emits the current authentication state of the user, such as whether the user is authenticated or unauthenticated. |
| [getDisplayName](get-display-name.html) | [androidJvm]<br>suspend fun [getDisplayName](get-display-name.html)(): [String](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-string/index.html)<br>Retrieves the user's display name from Firestore. The display name is fetched from the user's document. |
| [getUserEmail](get-user-email.html) | [androidJvm]<br>suspend fun [getUserEmail](get-user-email.html)(): [String](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-string/index.html)<br>Retrieves the user's email from Firestore. The email is fetched from the user's document. |
