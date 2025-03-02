package com.zurtar.mhma.journal

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.zurtar.mhma.JournalEntryR

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun JournalNavigation(viewModel: JournalViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "journaling") {
        //Main page
        composable("journaling") {
            JournalingScreen(
                viewModel = viewModel,
                onNavigateToEntryCreation = { navController.navigate(JournalEntryR) },
                onNavigateToEntryEdit = { id -> navController.navigate("entryEdit/$id")}
            )
        }
        //moves to EntryModificationPage using parameters for entry creation
        composable("entryCreation") {
            EntryModificationScreen(
                viewModel = viewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }
        //moves to EntryModificationPage using parameters for entry editing
        composable(route = "entryEdit/{entryId}", arguments = listOf(navArgument("entryId") { type = NavType.IntType }) // Added this line
            ) { backStackEntry ->
            val entryId = backStackEntry.arguments?.getInt("entryId") ?: -1
            EntryModificationScreen(
                id = entryId,
                viewModel = viewModel,
                onNavigateBack = {navController.popBackStack()},
            )
        }
    }
}