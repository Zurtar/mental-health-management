---
title: NavigationViewModel
---
//[app](../../../index.html)/[com.zurtar.mhma.util](../index.html)/[NavigationViewModel](index.html)



# NavigationViewModel

class [NavigationViewModel](index.html)@Injectconstructor(userRepository: [UserRepository](../../com.zurtar.mhma.data/-user-repository/index.html)) : [ViewModel](https://developer.android.com/reference/kotlin/androidx/lifecycle/ViewModel.html)

ViewModel responsible for managing the navigation drawer UI state, specifically tracking the user's login state, and providing it to the UI layer.



The [NavigationViewModel](index.html) uses the [UserRepository](../../com.zurtar.mhma.data/-user-repository/index.html) to observe the user's authentication state and updates the UI state accordingly. It listens for changes in the authentication state and reflects these changes in the [NavDrawerUiState](../-nav-drawer-ui-state/index.html).



This ViewModel is scoped to the lifecycle of the navigation UI and ensures that the UI is updated with the correct login state based on the user's authentication status.



#### Parameters


androidJvm

| | |
|---|---|
| userRepository | The repository that handles user authentication and retrieval of authentication state. Injected via Hilt for dependency management. |



## Constructors


| | |
|---|---|
| [NavigationViewModel](-navigation-view-model.html) | [androidJvm]<br>@Inject<br>constructor(userRepository: [UserRepository](../../com.zurtar.mhma.data/-user-repository/index.html)) |


## Properties


| Name | Summary |
|---|---|
| [uiState](ui-state.html) | [androidJvm]<br>val [uiState](ui-state.html): StateFlow&lt;[NavDrawerUiState](../-nav-drawer-ui-state/index.html)&gt; |


## Functions


| Name | Summary |
|---|---|
| [addCloseable](index.html#383812252%2FFunctions%2F-451970049) | [androidJvm]<br>open fun [addCloseable](index.html#383812252%2FFunctions%2F-451970049)(closeable: [AutoCloseable](https://developer.android.com/reference/kotlin/java/lang/AutoCloseable.html))<br>fun [addCloseable](index.html#1722490497%2FFunctions%2F-451970049)(key: [String](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-string/index.html), closeable: [AutoCloseable](https://developer.android.com/reference/kotlin/java/lang/AutoCloseable.html)) |
| [getCloseable](index.html#1102255800%2FFunctions%2F-451970049) | [androidJvm]<br>fun &lt;[T](index.html#1102255800%2FFunctions%2F-451970049) : [AutoCloseable](https://developer.android.com/reference/kotlin/java/lang/AutoCloseable.html)&gt; [getCloseable](index.html#1102255800%2FFunctions%2F-451970049)(key: [String](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-string/index.html)): [T](index.html#1102255800%2FFunctions%2F-451970049)? |
