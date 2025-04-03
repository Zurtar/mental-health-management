---
title: com.zurtar.mhma.auth
---
//[app](../../index.html)/[com.zurtar.mhma.auth](index.html)



# Package-level declarations



## Types


| Name | Summary |
|---|---|
| [AccountService](-account-service/index.html) | [androidJvm]<br>interface [AccountService](-account-service/index.html)<br>Interface defining the various authentication and account management services. |
| [AccountServiceImplementation](-account-service-implementation/index.html) | [androidJvm]<br>@Singleton<br>class [AccountServiceImplementation](-account-service-implementation/index.html)@Injectconstructor(firebaseAuth: FirebaseAuth, firestore: FirebaseFirestore) : [AccountService](-account-service/index.html)<br>Implementation of [AccountService](-account-service/index.html) that interacts with Firebase for user authentication and account management. |
| [AccountServiceModule](-account-service-module/index.html) | [androidJvm]<br>@Module<br>abstract class [AccountServiceModule](-account-service-module/index.html)<br>Dagger Hilt module to bind the [AccountServiceImplementation](-account-service-implementation/index.html) to [AccountService](-account-service/index.html). |
| [AccountUiState](-account-ui-state/index.html) | [androidJvm]<br>data class [AccountUiState](-account-ui-state/index.html)(val isLoggedIn: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false, val email: [String](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-string/index.html) = &quot;default_initial&quot;, val displayName: [String](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-string/index.html) = &quot;default_initial&quot;)<br>Represents the UI state for the user's account. |
| [AccountViewModel](-account-view-model/index.html) | [androidJvm]<br>class [AccountViewModel](-account-view-model/index.html)@Injectconstructor(userRepository: [UserRepository](../com.zurtar.mhma.data/-user-repository/index.html), accountService: [AccountService](-account-service/index.html)) : [ViewModel](https://developer.android.com/reference/kotlin/androidx/lifecycle/ViewModel.html)<br>ViewModel responsible for managing user account state and authentication status. It listens for authentication state changes and provides relevant user data. |
| [AuthState](-auth-state/index.html) | [androidJvm]<br>sealed class [AuthState](-auth-state/index.html)<br>Represents the authentication state of the user |
| [FireBaseModule](-fire-base-module/index.html) | [androidJvm]<br>@Module<br>object [FireBaseModule](-fire-base-module/index.html)<br>Dagger Hilt module to provide Firebase-related dependencies. |
| [LoginUiState](-login-ui-state/index.html) | [androidJvm]<br>data class [LoginUiState](-login-ui-state/index.html)(val email: [String](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-string/index.html) = &quot;&quot;, val password: [String](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-string/index.html) = &quot;&quot;)<br>Represents the UI state for the login screen. |
| [LoginViewModel](-login-view-model/index.html) | [androidJvm]<br>class [LoginViewModel](-login-view-model/index.html)@Injectconstructor(userRepository: [UserRepository](../com.zurtar.mhma.data/-user-repository/index.html), accountService: [AccountService](-account-service/index.html)) : [ViewModel](https://developer.android.com/reference/kotlin/androidx/lifecycle/ViewModel.html)<br>ViewModel responsible for managing the login process. Handles user authentication and UI state updates. |
| [SignupUiState](-signup-ui-state/index.html) | [androidJvm]<br>data class [SignupUiState](-signup-ui-state/index.html)(val email: [String](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-string/index.html) = &quot;&quot;, val password: [String](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-string/index.html) = &quot;&quot;, val password_: [String](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-string/index.html) = &quot;&quot;)<br>Represents the UI state for the signup screen. |
| [SignupViewModel](-signup-view-model/index.html) | [androidJvm]<br>class [SignupViewModel](-signup-view-model/index.html)@Injectconstructor(userRepository: [UserRepository](../com.zurtar.mhma.data/-user-repository/index.html), accountService: [AccountService](-account-service/index.html)) : [ViewModel](https://developer.android.com/reference/kotlin/androidx/lifecycle/ViewModel.html)<br>ViewModel responsible for managing the signup process. Handles account creation and UI state updates. |


## Functions


| Name | Summary |
|---|---|
| [AccountScreen](-account-screen.html) | [androidJvm]<br>@[Composable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Composable.html)<br>fun [AccountScreen](-account-screen.html)(modifier: [Modifier](https://developer.android.com/reference/kotlin/androidx/compose/ui/Modifier.html) = Modifier, viewModel: [AccountViewModel](-account-view-model/index.html) = hiltViewModel(), openDrawer: () -&gt; [Unit](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-unit/index.html), onLogoutResult: () -&gt; [Unit](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-unit/index.html))<br>Composable function for the Account screen, where users can view their account information and log out. |
| [LoginScreen](-login-screen.html) | [androidJvm]<br>@[Composable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Composable.html)<br>fun [LoginScreen](-login-screen.html)(modifier: [Modifier](https://developer.android.com/reference/kotlin/androidx/compose/ui/Modifier.html) = Modifier, viewModel: [LoginViewModel](-login-view-model/index.html) = hiltViewModel(), openDrawer: () -&gt; [Unit](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-unit/index.html), onLoginResult: () -&gt; [Unit](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-unit/index.html), onNavigateToSignUp: () -&gt; [Unit](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-unit/index.html))<br>Composable function for the Login screen that handles the layout and actions for user login and navigation to the sign-up screen. |
| [SignUpScreen](-sign-up-screen.html) | [androidJvm]<br>@[Composable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Composable.html)<br>fun [SignUpScreen](-sign-up-screen.html)(modifier: [Modifier](https://developer.android.com/reference/kotlin/androidx/compose/ui/Modifier.html) = Modifier, viewModel: [SignupViewModel](-signup-view-model/index.html) = hiltViewModel(), openDrawer: () -&gt; [Unit](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-unit/index.html), onSignUpResult: () -&gt; [Unit](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-unit/index.html))<br>Composable function representing the Sign Up screen. |
