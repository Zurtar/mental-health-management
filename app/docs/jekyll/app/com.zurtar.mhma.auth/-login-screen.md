---
title: LoginScreen
---
//[app](../../index.html)/[com.zurtar.mhma.auth](index.html)/[LoginScreen](-login-screen.html)



# LoginScreen



[androidJvm]\




@[Composable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Composable.html)



fun [LoginScreen](-login-screen.html)(modifier: [Modifier](https://developer.android.com/reference/kotlin/androidx/compose/ui/Modifier.html) = Modifier, viewModel: [LoginViewModel](-login-view-model/index.html) = hiltViewModel(), openDrawer: () -&gt; [Unit](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-unit/index.html), onLoginResult: () -&gt; [Unit](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-unit/index.html), onNavigateToSignUp: () -&gt; [Unit](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-unit/index.html))



Composable function for the Login screen that handles the layout and actions for user login and navigation to the sign-up screen.



#### Parameters


androidJvm

| | |
|---|---|
| modifier | The modifier to be applied to the layout of the screen content. |
| viewModel | The ViewModel that holds the UI state and business logic for login. |
| openDrawer | A lambda function triggered to open the navigation drawer. |
| onLoginResult | A lambda function triggered when the login process is complete. |
| onNavigateToSignUp | A lambda function triggered when the user wants to navigate to the sign-up screen. |



