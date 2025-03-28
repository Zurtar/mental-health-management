package com.zurtar.mhma

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.dialog
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.zurtar.mhma.auth.AccountScreen
import com.zurtar.mhma.auth.LoginScreen
import com.zurtar.mhma.auth.SignUpScreen
import com.zurtar.mhma.home.HomeScreen
import com.zurtar.mhma.journal.EntryModificationScreen
import com.zurtar.mhma.journal.JournalingScreen
import com.zurtar.mhma.mood.AnalyticsScreen
import com.zurtar.mhma.mood.BiWeeklyEvaluationScreen
import com.zurtar.mhma.mood.DailyMoodEvaluationScreen
import com.zurtar.mhma.mood.MoodEvaluationScreen
import com.zurtar.mhma.mood.SummaryPopupScreen
import com.zurtar.mhma.util.AppModalDrawer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun NavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    startDestination: Any = Home,
    navActions: Navigation = remember(navController) {
        Navigation(navController)
    },
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
     * Also the viewModel that's causing the problem needs a rework. It's a hacky implementation meant to function for the v1 demo
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
                    openDrawer = { coroutineScope.launch { drawerState.open() } },
                    onNavigateToMoodEvaluation = { navActions.navigateToMoodEvaluation() },
                    onNavigateToJournal = { navActions.navigateToJournal() },
                    onNavigateToAnalytics = {navActions.navigateToAnalytics()}
                )
            }

            composable<Account> {
                AccountScreen(
                    openDrawer = { coroutineScope.launch { drawerState.open() } },
                    onLogoutResult = { navActions.navigateToHome() })
            }

            composable<Login> {
                LoginScreen(
                    openDrawer = { coroutineScope.launch { drawerState.open() } },
                    onLoginResult = { navActions.navigateToAccount() },
                    onNavigateToSignUp = { navActions.navigateToSignup() })
            }

            composable<SignUp> {
                SignUpScreen(
                    openDrawer = { coroutineScope.launch { drawerState.open() } },
                    onSignUpResult = { navActions.navigateToLogin() })
            }

            composable<MoodEvaluation> {
                MoodEvaluationScreen(
                    openDrawer = { coroutineScope.launch { drawerState.open() } },
                    onNavigateToDaily = { navActions.navigateToDailyEvaluation() },
                    onNavigateToBiWeekly = { navActions.navigateToBiWeeklyEvaluation() }
                )
            }

            composable<BiWeeklyEvaluation> {
                BiWeeklyEvaluationScreen(
                    openDrawer = { coroutineScope.launch { drawerState.open() } },
                    onNavigateToAnalytics = { navActions.navigateToAnalytics() }
                )
            }

            composable<DailyEvaluation> {
                DailyMoodEvaluationScreen(
                    openDrawer = { coroutineScope.launch { drawerState.open() } },
                )
            }

            //Added dialog navigation for biweekly summary page
            dialog<SummaryDialog>{
                SummaryPopupScreen()
            }
            composable<Analytics> {
                AnalyticsScreen(
                    openDrawer = { coroutineScope.launch { drawerState.open() } },
                    onNavigateToSummaryDialog = { navActions.navigateToSummaryDialog() }
                )
            }

            composable<Journal> {
                JournalingScreen(
                    openDrawer = { coroutineScope.launch { drawerState.open() } },
                    onNavigateToEntryCreation = { navController.navigate(JournalEntryR) },
                    onNavigateToEntryEdit = { id -> navController.navigate("entryEdit/$id") }
                )
            }

            // Directly copied from Journal branch, which is why its different
            composable<JournalEntryR> {
                EntryModificationScreen(
                    openDrawer = { coroutineScope.launch { drawerState.open() } },
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
                    openDrawer = { coroutineScope.launch { drawerState.open() } },
                    id = entryId,
                    onNavigateBack = { navController.popBackStack() },
                )
            }
        }
    }
}

