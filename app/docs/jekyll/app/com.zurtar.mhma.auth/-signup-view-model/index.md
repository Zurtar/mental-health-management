---
title: SignupViewModel
---
//[app](../../../index.html)/[com.zurtar.mhma.auth](../index.html)/[SignupViewModel](index.html)



# SignupViewModel



[androidJvm]\
class [SignupViewModel](index.html)@Injectconstructor(userRepository: [UserRepository](../../com.zurtar.mhma.data/-user-repository/index.html), accountService: [AccountService](../-account-service/index.html)) : [ViewModel](https://developer.android.com/reference/kotlin/androidx/lifecycle/ViewModel.html)

ViewModel responsible for managing the signup process. Handles account creation and UI state updates.



## Constructors


| | |
|---|---|
| [SignupViewModel](-signup-view-model.html) | [androidJvm]<br>@Inject<br>constructor(userRepository: [UserRepository](../../com.zurtar.mhma.data/-user-repository/index.html), accountService: [AccountService](../-account-service/index.html)) |


## Properties


| Name | Summary |
|---|---|
| [uiState](ui-state.html) | [androidJvm]<br>val [uiState](ui-state.html): StateFlow&lt;[SignupUiState](../-signup-ui-state/index.html)&gt; |


## Functions


| Name | Summary |
|---|---|
| [addCloseable](../../com.zurtar.mhma.util/-navigation-view-model/index.html#383812252%2FFunctions%2F-451970049) | [androidJvm]<br>open fun [addCloseable](../../com.zurtar.mhma.util/-navigation-view-model/index.html#383812252%2FFunctions%2F-451970049)(closeable: [AutoCloseable](https://developer.android.com/reference/kotlin/java/lang/AutoCloseable.html))<br>fun [addCloseable](../../com.zurtar.mhma.util/-navigation-view-model/index.html#1722490497%2FFunctions%2F-451970049)(key: [String](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-string/index.html), closeable: [AutoCloseable](https://developer.android.com/reference/kotlin/java/lang/AutoCloseable.html)) |
| [createAccount](create-account.html) | [androidJvm]<br>fun [createAccount](create-account.html)(onResult: () -&gt; [Unit](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-unit/index.html))<br>Attempts to create a new user account. |
| [getCloseable](../../com.zurtar.mhma.util/-navigation-view-model/index.html#1102255800%2FFunctions%2F-451970049) | [androidJvm]<br>fun &lt;[T](../../com.zurtar.mhma.util/-navigation-view-model/index.html#1102255800%2FFunctions%2F-451970049) : [AutoCloseable](https://developer.android.com/reference/kotlin/java/lang/AutoCloseable.html)&gt; [getCloseable](../../com.zurtar.mhma.util/-navigation-view-model/index.html#1102255800%2FFunctions%2F-451970049)(key: [String](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-string/index.html)): [T](../../com.zurtar.mhma.util/-navigation-view-model/index.html#1102255800%2FFunctions%2F-451970049)? |
| [onEmailChange](on-email-change.html) | [androidJvm]<br>fun [onEmailChange](on-email-change.html)(newValue: [String](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-string/index.html))<br>Updates the email field in the UI state. |
| [onPasswordChange](on-password-change.html) | [androidJvm]<br>fun [onPasswordChange](on-password-change.html)(newValue: [String](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-string/index.html))<br>Updates the password field in the UI state. |
| [onVerifyPasswordChange](on-verify-password-change.html) | [androidJvm]<br>fun [onVerifyPasswordChange](on-verify-password-change.html)(newValue: [String](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-string/index.html))<br>Updates the verification password field in the UI state. |
