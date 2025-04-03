---
title: SignUpScreen
---
//[app](../../index.html)/[com.zurtar.mhma.auth](index.html)/[SignUpScreen](-sign-up-screen.html)



# SignUpScreen



[androidJvm]\




@[Composable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Composable.html)



fun [SignUpScreen](-sign-up-screen.html)(modifier: [Modifier](https://developer.android.com/reference/kotlin/androidx/compose/ui/Modifier.html) = Modifier, viewModel: [SignupViewModel](-signup-view-model/index.html) = hiltViewModel(), openDrawer: () -&gt; [Unit](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-unit/index.html), onSignUpResult: () -&gt; [Unit](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-unit/index.html))



Composable function representing the Sign Up screen.



This screen allows the user to sign up for an account by providing their email, password, and confirming the password. It calls the provided [onSignUpResult](-sign-up-screen.html) callback after a successful sign-up.



#### Parameters


androidJvm

| | |
|---|---|
| modifier | The modifier to be applied to the [Scaffold](https://developer.android.com/reference/kotlin/androidx/compose/material3/package-summary.html) layout. |
| viewModel | The [SignupViewModel](-signup-view-model/index.html) instance used to manage UI state and actions. |
| openDrawer | A lambda function that triggers the opening of a navigation drawer. |
| onSignUpResult | A lambda function that is invoked when the sign-up process finishes successfully. |



