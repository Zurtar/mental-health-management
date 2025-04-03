---
title: AuthState
---
//[app](../../../index.html)/[com.zurtar.mhma.auth](../index.html)/[AuthState](index.html)



# AuthState

sealed class [AuthState](index.html)

Represents the authentication state of the user



#### Inheritors


| |
|---|
| [Unauthenticated](-unauthenticated/index.html) |
| [Authenticated](-authenticated/index.html) |


## Types


| Name | Summary |
|---|---|
| [Authenticated](-authenticated/index.html) | [androidJvm]<br>data class [Authenticated](-authenticated/index.html)(val user: FirebaseUser) : [AuthState](index.html)<br>Represents the state when the user is authenticated, containing the user details. |
| [Unauthenticated](-unauthenticated/index.html) | [androidJvm]<br>object [Unauthenticated](-unauthenticated/index.html) : [AuthState](index.html)<br>Represents the state when the user is unauthenticated. |
