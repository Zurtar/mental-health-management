package com.zurtar.mhma

import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.zurtar.mhma.models.NavigationDrawerViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun AppModalDrawer(
    drawerState: DrawerState,
    currentRoute: Any,
    navigationActions: NavigationActions,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    content: @Composable () -> Unit
) {
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            AppDrawer(
                currentRoute = currentRoute,
                navigateToHome = { navigationActions.navigateToHome() },
                navigateToAccount = { navigationActions.navigateToAccount() },
                navigateToLogin = { navigationActions.navigateToLogin() },
                navigateToSignup = { navigationActions.navigateToSignup() },
                closeDrawer = { coroutineScope.launch { drawerState.close() } }
            )
        }
    ) {
        content()
    }
}

@Composable
private fun AppDrawer(
    currentRoute: Any,
    viewModel: NavigationDrawerViewModel = viewModel(),
    navigateToHome: () -> Unit,
    navigateToAccount: () -> Unit,
    navigateToLogin: () -> Unit,
    navigateToSignup: () -> Unit,
    closeDrawer: () -> Unit,
    modifier: Modifier = Modifier
) {
    val navDrawerUiState by viewModel.uiState.collectAsStateWithLifecycle()

    ModalDrawerSheet(modifier = modifier, drawerShape = customDrawerShape()) {
        Text("Navigation", modifier = Modifier.padding(16.dp))
        HorizontalDivider()
        NavigationDrawerItem(
            label = { Text(text = "Home") },
            selected = false,
            onClick = {
                Log.println(Log.INFO, "NavDrawer", "Home OnClick Fired!")
                navigateToHome()
                closeDrawer()
            }
        )

        if (navDrawerUiState.isLoggedIn)
            NavigationDrawerItem(
                label = { Text(text = "Account") },
                selected = false,
                onClick = {
                    Log.println(Log.INFO, "NavDrawer", "Login OnClick Fired!")
                    navigateToAccount()
                    closeDrawer()
                })
        else {
            NavigationDrawerItem(
                label = { Text(text = "Login") },
                selected = false,
                onClick = {
                    Log.println(Log.WARN, "NavDrawer", "Login OnClick Fired!")
                    navigateToLogin()
                    closeDrawer()
                })
            NavigationDrawerItem(label = { Text(text = "Sign Up") },
                selected = false,
                onClick = {
                    Log.println(Log.INFO, "NavDrawer", "SignUp OnClick Fired!")
                    navigateToSignup()
                    closeDrawer()
                })
        }
    }

}

fun customDrawerShape() = object : Shape {
    override fun createOutline(
        size: Size, layoutDirection: LayoutDirection, density: Density
    ): Outline {
        return Outline.Rectangle(
            Rect(
                0f, 0f, size.width * 0.8f, size.height * 1f
            )
        )
    }
}
