package com.zurtar.mhma.util

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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.zurtar.mhma.Navigation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * Composable that displays a modal drawer for navigation.
 *
 * @param drawerState State of the drawer, controls whether it's open or closed.
 * @param currentRoute The current navigation route to highlight active section.
 * @param navigationActions Actions to navigate to different parts of the app.
 * @param coroutineScope CoroutineScope to manage drawer interactions.
 * @param content Composable content to be displayed inside the main area of the app.
 */
@Composable
fun AppModalDrawer(
    drawerState: DrawerState,
    currentRoute: Any,
    navigationActions: Navigation,
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

/**
 * Composable representing the navigation drawer content.
 *
 * @param currentRoute The current route to determine what to highlight in the drawer.
 * @param viewModel Navigation view model to track user login state.
 * @param navigateToHome Function to navigate to the Home screen.
 * @param navigateToAccount Function to navigate to the Account screen.
 * @param navigateToLogin Function to navigate to the Login screen.
 * @param navigateToSignup Function to navigate to the Signup screen.
 * @param closeDrawer Function to close the drawer when an item is selected.
 * @param modifier Modifier for customizing the drawer appearance.
 */
@Composable
private fun AppDrawer(
    currentRoute: Any,
    viewModel: NavigationViewModel = hiltViewModel(),
    navigateToHome: () -> Unit,
    navigateToAccount: () -> Unit,
    navigateToLogin: () -> Unit,
    navigateToSignup: () -> Unit,
    closeDrawer: () -> Unit,
    modifier: Modifier = Modifier
) {

    val navViewModelState by viewModel.uiState.collectAsStateWithLifecycle()

    ModalDrawerSheet(modifier = modifier, drawerShape = customDrawerShape()) {
        Text("Navigation", modifier = Modifier.padding(16.dp))
        HorizontalDivider()

        if (navViewModelState.isLoggedIn) {
            NavigationDrawerItem(
                label = { Text(text = "Home") },
                selected = false,
                onClick = {
                    Log.println(Log.INFO, "NavDrawer", "Home OnClick Fired!")
                    navigateToHome()
                    closeDrawer()
                }
            )

            NavigationDrawerItem(
                label = { Text(text = "Account") },
                selected = false,
                onClick = {
                    Log.println(Log.INFO, "NavDrawer", "Login OnClick Fired!")
                    navigateToAccount()
                    closeDrawer()
                })
        } else {
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

/**
 * Creates a custom shape for the navigation drawer.
 *
 * @return A custom shape used to define the drawer's outline.
 */
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
