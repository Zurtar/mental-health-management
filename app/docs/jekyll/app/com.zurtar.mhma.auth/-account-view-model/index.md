---
title: AccountViewModel
---
//[app](../../../index.html)/[com.zurtar.mhma.auth](../index.html)/[AccountViewModel](index.html)



# AccountViewModel



[androidJvm]\
class [AccountViewModel](index.html)@Injectconstructor(userRepository: [UserRepository](../../com.zurtar.mhma.data/-user-repository/index.html), accountService: [AccountService](../-account-service/index.html)) : [ViewModel](https://developer.android.com/reference/kotlin/androidx/lifecycle/ViewModel.html)

ViewModel responsible for managing user account state and authentication status. It listens for authentication state changes and provides relevant user data.



## Constructors


| | |
|---|---|
| [AccountViewModel](-account-view-model.html) | [androidJvm]<br>@Inject<br>constructor(userRepository: [UserRepository](../../com.zurtar.mhma.data/-user-repository/index.html), accountService: [AccountService](../-account-service/index.html)) |


## Properties


| Name | Summary |
|---|---|
| [uiState](ui-state.html) | [androidJvm]<br>val [uiState](ui-state.html): StateFlow&lt;[AccountUiState](../-account-ui-state/index.html)&gt; |


## Functions


| Name | Summary |
|---|---|
| [addCloseable](../../com.zurtar.mhma.util/-navigation-view-model/index.html#383812252%2FFunctions%2F-451970049) | [androidJvm]<br>open fun [addCloseable](../../com.zurtar.mhma.util/-navigation-view-model/index.html#383812252%2FFunctions%2F-451970049)(closeable: [AutoCloseable](https://developer.android.com/reference/kotlin/java/lang/AutoCloseable.html))<br>fun [addCloseable](../../com.zurtar.mhma.util/-navigation-view-model/index.html#1722490497%2FFunctions%2F-451970049)(key: [String](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-string/index.html), closeable: [AutoCloseable](https://developer.android.com/reference/kotlin/java/lang/AutoCloseable.html)) |
| [getCloseable](../../com.zurtar.mhma.util/-navigation-view-model/index.html#1102255800%2FFunctions%2F-451970049) | [androidJvm]<br>fun &lt;[T](../../com.zurtar.mhma.util/-navigation-view-model/index.html#1102255800%2FFunctions%2F-451970049) : [AutoCloseable](https://developer.android.com/reference/kotlin/java/lang/AutoCloseable.html)&gt; [getCloseable](../../com.zurtar.mhma.util/-navigation-view-model/index.html#1102255800%2FFunctions%2F-451970049)(key: [String](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-string/index.html)): [T](../../com.zurtar.mhma.util/-navigation-view-model/index.html#1102255800%2FFunctions%2F-451970049)? |
| [signOut](sign-out.html) | [androidJvm]<br>fun [signOut](sign-out.html)(onResult: () -&gt; [Unit](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-unit/index.html))<br>Signs the user out and updates the UI state accordingly. |
