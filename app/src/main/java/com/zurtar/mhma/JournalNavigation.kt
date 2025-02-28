package com.zurtar.mhma

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun JournalNavigation(viewModel: JournalViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "journaling") {
        //Main page
        composable("journaling") {
            JournalingPage(
                viewModel = viewModel,
                onNavigateToEntryCreation = { navController.navigate("entryCreation") },
                onNavigateToEntryEdit = { id -> navController.navigate("entryEdit/$id")}
            )
        }
        //moves to EntryModificationPage using parameters for entry creation
        composable("entryCreation") {
            EntryModificationPage(
                viewModel = viewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }
        //moves to EntryModificationPage using parameters for entry editing
        composable(route = "entryEdit/{entryId}", arguments = listOf(navArgument("entryId") { type = NavType.IntType }) // Added this line
            ) { backStackEntry ->
            val entryId = backStackEntry.arguments?.getInt("entryId") ?: -1
            EntryModificationPage(
                entryId,
                viewModel = viewModel,
                onNavigateBack = {navController.popBackStack()},
            )
        }
    }
}