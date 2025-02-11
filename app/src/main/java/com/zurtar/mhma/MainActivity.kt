package com.zurtar.mhma

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.zurtar.mhma.ui.theme.AppTheme
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable

private lateinit var auth: FirebaseAuth

class MainActivity : ComponentActivity() {

    /**
     * Nav Host Objects
     */


    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Firebase Auth setup
        auth = Firebase.auth

        enableEdgeToEdge()
        setContent {
            MyApp()
        }
    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser != null) {
//            reload()
        }

    }
}

@Serializable
object Login;

@Serializable
object SignUp;

@Serializable
object Account;

@Serializable
object Home;


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyApp() {
    AppTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            val navController = rememberNavController()
            val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
            val scope = rememberCoroutineScope()
            // This is to let us get the drawer from right to left as requested in the UI mockup
            ModalNavigationDrawer(drawerState = drawerState, drawerContent = {
                ModalDrawerSheet(drawerShape = customDrawerShape(),
//                drawerContainerColor = MaterialTheme.colorScheme.secondaryContainer,
//                drawerContentColor = MaterialTheme.colorScheme.secondary,
                    content = {
                        Text("ModalNavDrawer", modifier = Modifier.padding(16.dp))
                        HorizontalDivider()

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
                                Log.println(Log.WARN, "NavDrawer", "SignUp OnClick Fired!")
                                navController.navigate(route = SignUp)
                                scope.launch { drawerState.close() }
                            })
                    })
            }, content = {
                Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
                    TopAppBar(title = { Text("TopAppBar") }, colors = topAppBarColors(
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
                        modifier = Modifier.fillMaxSize()
                    ) {
                        composable<Home> {
                            HomeScreen(
                                "VibeCheck",
                                modifier = Modifier.padding(innerPadding)
                            )
                        }
                        composable<Login> { LoginScreen(modifier = Modifier.padding(innerPadding)) }
                        composable<SignUp> { SignUpScreen(modifier = Modifier.padding(innerPadding)) }
                    }
                })
            })
        }
    }
}

@Composable
fun HomeScreen(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!", modifier = modifier
    )
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
