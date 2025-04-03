---
title: AccountScreen
---
//[app](../../index.html)/[com.zurtar.mhma.auth](index.html)/[AccountScreen](-account-screen.html)



# AccountScreen



[androidJvm]\




@[Composable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Composable.html)



fun [AccountScreen](-account-screen.html)(modifier: [Modifier](https://developer.android.com/reference/kotlin/androidx/compose/ui/Modifier.html) = Modifier, viewModel: [AccountViewModel](-account-view-model/index.html) = hiltViewModel(), openDrawer: () -&gt; [Unit](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-unit/index.html), onLogoutResult: () -&gt; [Unit](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-unit/index.html))



Composable function for the Account screen, where users can view their account information and log out.



#### Parameters


androidJvm

| | |
|---|---|
| modifier | The modifier to be applied to the layout of the screen content. |
| viewModel | The ViewModel that holds the UI state and business logic for the account. |
| openDrawer | A lambda function triggered to open the navigation drawer. |
| onLogoutResult | A lambda function triggered when the logout process is complete. |



