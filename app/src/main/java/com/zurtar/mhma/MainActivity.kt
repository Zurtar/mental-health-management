package com.zurtar.mhma

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.zurtar.mhma.ui.theme.AppTheme
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.zurtar.mhma.journal.EntryModificationScreen
import com.zurtar.mhma.journal.JournalingScreen
import com.zurtar.mhma.models.NavigationDrawerViewModel
import com.zurtar.mhma.mood.BiWeeklyEvaluationScreen
import com.zurtar.mhma.mood.MoodEvaluationScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApp()
        }
    }
}

/**
 * Nav Host Objects
 *
 * I need to centralize this
 */
@Serializable
object Login;
@Serializable
object SignUp;
@Serializable
object Account;
@Serializable
object Home;
@Serializable
object MoodEvaluation;
@Serializable
object BiWeeklyEvaluation;
@Serializable
object DailyEvaluation;

@Serializable
object Journal

@Serializable
object JournalEntryR


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyApp() {
    AppTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            val navController = rememberNavController()
            val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

            // My attempt at state for modal nav drawer
            val navDrawerViewModel: NavigationDrawerViewModel = viewModel()
            val navDrawerUiState by navDrawerViewModel.uiState.collectAsStateWithLifecycle()

            val scope = rememberCoroutineScope()

            // This is to let us get the drawer from right to left as requested in the UI mockup
            ModalNavigationDrawer(drawerState = drawerState, drawerContent = {
                ModalDrawerSheet(
                    drawerShape = customDrawerShape(),
                    content = {
                        Text("Navigation", modifier = Modifier.padding(16.dp))
                        HorizontalDivider()
                        NavigationDrawerItem(
                            label = { Text(text = "Home") },
                            selected = false,
                            onClick = {
                                Log.println(Log.INFO, "NavDrawer", "Home OnClick Fired!")
                                navController.navigate(route = Home)
                                scope.launch { drawerState.close() }

                            })
//                        HorizontalDivider()
                        if (navDrawerUiState.isLoggedIn)
                            NavigationDrawerItem(
                                label = { Text(text = "Account") },
                                selected = false,
                                onClick = {
                                    Log.println(Log.INFO, "NavDrawer", "Login OnClick Fired!")
                                    navController.navigate(route = Account)
                                    scope.launch { drawerState.close() }

                                })
                        else {
                            NavigationDrawerItem(
                                label = { Text(text = "Login") },
                                selected = false,
                                onClick = {
                                    Log.println(Log.WARN, "NavDrawer", "Login OnClick Fired!")
                                    navController.navigate(route = Login)
                                    scope.launch { drawerState.close() }

                                })
                            NavigationDrawerItem(label = { Text(text = "Sign Up") },
                                selected = false,
                                onClick = {
                                    Log.println(Log.INFO, "NavDrawer", "SignUp OnClick Fired!")
                                    navController.navigate(route = SignUp)
                                    scope.launch { drawerState.close() }
                                })
                        }
                    })
            }, content = {
                Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
                    TopAppBar(title = { Text("Mental-Health Tracker") }, colors = topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        titleContentColor = MaterialTheme.colorScheme.primary,
                    ), navigationIcon = {
                        IconButton(onClick = {
                            scope.launch {
                                if (drawerState.isClosed) {
                                    drawerState.open()
                                } else {
                                    drawerState.close()
                                }
                            }
                        }, content = {
                            Icon(
                                Icons.Default.Menu, contentDescription = "Menu"
                            )
                        })
                    })
                }, content = { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = Home,
                        modifier = Modifier.fillMaxSize(),
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
                            EmotionPickerScreen(
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
                })
            })
        }
    }
}

@Composable
fun HomeScreen(modifier: Modifier = Modifier, onNavigate: (Any) -> Unit) {
    Column(
        modifier = modifier.fillMaxWidth(0.5f), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ElevatedCard(
            modifier = Modifier
                .padding(top = 20.dp)
                .fillMaxWidth(0.75f)
                .fillMaxHeight(0.2f)
                .background(color = MaterialTheme.colorScheme.background),
            onClick = { onNavigate(MoodEvaluation) }
        ) {
            Text(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .height(150.dp)
                    .wrapContentHeight(),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineMedium,
                text = "Mood Evaluation"
            )
        }

        ElevatedCard(
            modifier = Modifier
                .padding(top = 20.dp)
                .fillMaxWidth(0.75f)
                .fillMaxHeight(0.2f)
                .background(color = MaterialTheme.colorScheme.background),
            onClick = { onNavigate(Journal) }
        ) {
            Text(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .height(150.dp)
                    .wrapContentHeight(),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineMedium,
                text = "Journal"
            )
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
