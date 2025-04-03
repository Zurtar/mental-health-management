---
title: AccountUiState
---
//[app](../../../index.html)/[com.zurtar.mhma.auth](../index.html)/[AccountUiState](index.html)



# AccountUiState



[androidJvm]\
data class [AccountUiState](index.html)(val isLoggedIn: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false, val email: [String](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-string/index.html) = &quot;default_initial&quot;, val displayName: [String](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-string/index.html) = &quot;default_initial&quot;)

Represents the UI state for the user's account.



## Constructors


| | |
|---|---|
| [AccountUiState](-account-ui-state.html) | [androidJvm]<br>constructor(isLoggedIn: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false, email: [String](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-string/index.html) = &quot;default_initial&quot;, displayName: [String](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-string/index.html) = &quot;default_initial&quot;) |


## Properties


| Name | Summary |
|---|---|
| [displayName](display-name.html) | [androidJvm]<br>val [displayName](display-name.html): [String](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-string/index.html)<br>The display name of the authenticated user. |
| [email](email.html) | [androidJvm]<br>val [email](email.html): [String](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-string/index.html)<br>The email associated with the logged-in account. |
| [isLoggedIn](is-logged-in.html) | [androidJvm]<br>val [isLoggedIn](is-logged-in.html): [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false<br>Whether the user is currently authenticated. |
