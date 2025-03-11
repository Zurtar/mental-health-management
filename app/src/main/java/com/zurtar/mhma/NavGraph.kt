package com.zurtar.mhma

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.zurtar.mhma.journal.EntryModificationScreen
import com.zurtar.mhma.journal.JournalingScreen
import com.zurtar.mhma.mood.BiWeeklyEvaluationScreen
import com.zurtar.mhma.mood.MoodEvaluationScreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.serialization.Serializable

@Composable
fun NavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    startDestination: Any = Home,
    navActions: NavigationActions = remember(navController) {
        NavigationActions(navController)
    },
    innerPadding: PaddingValues
) {
    val currentNavBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentNavBackStackEntry?.destination?.route ?: startDestination


    /** this wraps around nav host to provide a shared modal drawer across all pages
     *
     * The alternative is to have it within each composable<> call, wrapping the specific screen
     *
     * Im doing this way because if we have it within the composable call the view model I use to check user state and modify the drawer
     * will recompile every single time we swap screens, firing a listener, that does not need to be fired. We end up recomposing the drawer
     * every time we transition.
     *
     * So we can switch if we're either okay with that or when we eventually add dependency injection with Hilt, which we will likely end up doing at some point
     *
     * 2025-03-11 Ethan
     * */


    AppModalDrawer(drawerState, currentRoute, navActions) {
        NavHost(
            navController = navController,
            startDestination = Home,
            modifier = modifier,
            enterTransition = {
                fadeIn(animationSpec = tween(100))
            },
            exitTransition = {
                fadeOut(animationSpec = tween(100))
            }
        ) {
            composable<Home> {
                HomeScreen(
                    modifier = Modifier.padding(innerPadding),
                    navController::navigate
                )

            }
            composable<Account> {
                AccountScreen(
                    modifier = Modifier.padding(innerPadding),
                    onLogout = { navController.navigate(Home) })
            }
            composable<Login> {
                LoginScreen(
                    modifier = Modifier.padding(innerPadding),
                    onLoginSuccess = { navController.navigate(Account) },
                    onNavigate = { navController.navigate(SignUp) })
            }
            composable<SignUp> {
                SignUpScreen(
                    modifier = Modifier.padding(innerPadding),
                    onSignUp = { navController.navigate(Login) })
            }
            composable<MoodEvaluation> {
                MoodEvaluationScreen(
                    modifier = Modifier.padding(innerPadding),
                    onNavigate = navController::navigate
                )
            }
            composable<BiWeeklyEvaluation> {
                BiWeeklyEvaluationScreen(
                    modifier = Modifier.padding(innerPadding),
                )
            }
            composable<Journal> {
                JournalingScreen(
                    modifier = Modifier.padding(innerPadding),
                    onNavigateToEntryCreation = { navController.navigate(JournalEntryR) },
                    onNavigateToEntryEdit = { id -> navController.navigate("entryEdit/$id") }
                )
            }
            composable<DailyEvaluation> {
                DailyMoodEvaluationScreen(
                    modifier = Modifier.padding(innerPadding),
                    onNavigate = { navController.navigate(Home) }
                )
            }
            // Directly copied from Journal branch, which is why its different
            composable<JournalEntryR> {
                EntryModificationScreen(
                    modifier = Modifier.padding(innerPadding),
                    onNavigateBack = { navController.popBackStack() }
                )
            }

            composable(
                route = "entryEdit/{entryId}",
                arguments = listOf(navArgument("entryId") {
                    type = NavType.IntType
                }) // Added this line
            ) { backStackEntry ->
                val entryId = backStackEntry.arguments?.getInt("entryId") ?: -1

                EntryModificationScreen(
                    modifier = Modifier.padding(innerPadding),
                    id = entryId,
                    onNavigateBack = { navController.popBackStack() },
                )
            }
        }
    }
}

/**
 * Nav Host Objects
 *
 * I need to centralize this
 */
@Serializable
object Login

@Serializable
object SignUp

@Serializable
object Account

@Serializable
object Home

@Serializable
object MoodEvaluation

@Serializable
object BiWeeklyEvaluation

@Serializable
object DailyEvaluation

@Serializable
object Journal

@Serializable
object JournalEntryR