package com.zurtar.mhma


import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.dialog
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.zurtar.mhma.analytics.AnalyticsScreen
import com.zurtar.mhma.analytics.SummaryPopup
import com.zurtar.mhma.auth.AccountScreen
import com.zurtar.mhma.auth.LoginScreen
import com.zurtar.mhma.auth.SignUpScreen
import com.zurtar.mhma.chatbot.ChatListPage
import com.zurtar.mhma.chatbot.ChatLogPage
import com.zurtar.mhma.chatbot.ChatbotPage
import com.zurtar.mhma.data.BiWeeklyEvaluationEntry
import com.zurtar.mhma.home.HomeScreen
import com.zurtar.mhma.journal.EntryModificationScreen
import com.zurtar.mhma.journal.EntryViewScreen
import com.zurtar.mhma.journal.JournalingScreen
import com.zurtar.mhma.mood.BiWeeklyEvaluationScreen
import com.zurtar.mhma.mood.DailyMoodEvaluationScreen
import com.zurtar.mhma.mood.MoodEvaluationScreen
import com.zurtar.mhma.util.AppModalDrawer
import com.zurtar.mhma.util.NavigationViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

@Composable
fun NavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    viewModel: NavigationViewModel = hiltViewModel(),
    startDestination: Any = "Home",
    navActions: Navigation = remember(navController) {
        Navigation(navController)
    },
) {
    val navViewModelState by viewModel.uiState.collectAsStateWithLifecycle()
    val currentNavBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentNavBackStackEntry?.destination?.route ?: startDestination
    val start = if (navViewModelState.isLoggedIn) "Home" else "Login"

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
            startDestination = start,
            modifier = modifier,
        ) {

//            composable<Home> {
            composable("Home") {
                HomeScreen(
                    openDrawer = { coroutineScope.launch { drawerState.open() } },
                    onNavigateToMoodEvaluation = { navActions.navigateToMoodEvaluation() },
                    onNavigateToJournal = { navActions.navigateToJournal() },
                    onNavigateToChatbot = { navActions.navigateToChatbot() },
                    onNavigateToAnalytics = { navActions.navigateToAnalytics() }
                )
            }

//            composable<Account> {
            composable("Account") {
                AccountScreen(
                    openDrawer = { coroutineScope.launch { drawerState.open() } },
                    onLogoutResult = { navActions.navigateToHome() })
            }

//            composable<Login> {
            composable("Login") {
                LoginScreen(
                    openDrawer = { coroutineScope.launch { drawerState.open() } },
                    onLoginResult = { navActions.navigateToAccount() },
                    onNavigateToSignUp = { navActions.navigateToSignup() })
            }

//            composable<SignUp> {
            composable("Signup") {
                SignUpScreen(
                    openDrawer = { coroutineScope.launch { drawerState.open() } },
                    onSignUpResult = { navActions.navigateToLogin() })
            }

//            composable<MoodEvaluation> {
            composable("MoodEvaluation") {
                MoodEvaluationScreen(
                    openDrawer = { coroutineScope.launch { drawerState.open() } },
                    onNavigateToDaily = { navActions.navigateToDailyEvaluation() },
                    onNavigateToBiWeekly = { navActions.navigateToBiWeeklyEvaluation() }
                )
            }
//            composable<BiWeeklyEvaluation> {
            composable("BiWeeklyEvaluation") {
                BiWeeklyEvaluationScreen(
                    openDrawer = { coroutineScope.launch { drawerState.open() } },
                    onNavigateToAnalytics = { navActions.navigateToAnalytics() }
                )
            }

//            composable<DailyEvaluation> {
            composable("DailyEvaluation") {
                DailyMoodEvaluationScreen(
                    openDrawer = { coroutineScope.launch { drawerState.open() } },
                    onNavigateToAnalytics = { navActions.navigateToAnalytics(0) },
                    onNavigateToJournal = { navActions.navigateToJournal() }
                )
            }

            //Added dialog navigation for biweekly summary page
//            dialog<SummaryDialog> {
            dialog(
                route = "SummaryDialog/{EntryObject}",
                arguments = listOf(navArgument("EntryObject") { type = NavType.StringType }
                )
            ) {
                val jsonString = currentNavBackStackEntry?.arguments?.getString("EntryObject")
                checkNotNull(jsonString) {
                    navController.popBackStack()
                    return@dialog
                }

                val entry: BiWeeklyEvaluationEntry =
                    Json.decodeFromString<BiWeeklyEvaluationEntry>(jsonString)
                SummaryPopup(entry)
            }


            composable("Journal") {
                JournalingScreen(
                    openDrawer = { coroutineScope.launch { drawerState.open() } },
                    onNavigateToEntryCreation = { navActions.navigateToJournalEntryModification(-1) },
                    onNavigateToEntryView = { id -> navActions.navigateToEntryView(id) },
                )
            }

            composable(route = "${Analytics}/{id}",
                arguments = listOf(
                    navArgument("id") { type = NavType.IntType }
                )
            ) { backStackEntry ->
                AnalyticsScreen(
                    id = backStackEntry.arguments?.getInt("id") ?: 0,
                    openDrawer = { coroutineScope.launch { drawerState.open() } },
                    onNavigateToSummaryDialog = navActions::navigateToSummaryDialog
                )
            }


            //Attempting to add in Chatbot navigation
            composable("ChatBot") {
                ChatbotPage(
                    openDrawer = { coroutineScope.launch { drawerState.open() } },
                    onNavigateToChatList = { navActions.navigateToChatList() }
                )
            }

            composable("ChatList") {
                ChatListPage(
                    openDrawer = { coroutineScope.launch { drawerState.open() } },
                    onNavigateToChatbot = { navActions.navigateToChatbot() },
                    onNavigateToChatLog = { logId -> navActions.navigateToChatLog(logId) }
                )
            }

            composable(
                route = "ChatLog/{logId}",
                arguments = listOf(navArgument("logId") { type = NavType.IntType })
            ) { backStackEntry ->
                val logId = backStackEntry.arguments?.getInt("logId") ?: 0
                ChatLogPage(
                    openDrawer = { coroutineScope.launch { drawerState.open() } },
                    logId = logId,
                    onNavigateBack = {
                        navController.popBackStack()
                    }
                )
            }


            // Directly copied from Journal branch, which is why its different
//

            composable(
                route = "entryView/{entryId}",
                arguments = listOf(navArgument("entryId") { type = NavType.IntType })
            ) { backStackEntry ->
                val entryId = backStackEntry.arguments?.getInt("entryId") ?: -1

                EntryViewScreen(
                    openDrawer = { coroutineScope.launch { drawerState.open() } },
                    id = entryId,
                    onNavigateToEntryEdit = { id -> navActions.navigateToEntryEdit(id) },
                    onNavigateBack = { navController.popBackStack() },
                )
            }

            composable(
                route = "entryEdit/{entryId}",
                arguments = listOf(navArgument("entryId") {
                    type = NavType.IntType
                })
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

