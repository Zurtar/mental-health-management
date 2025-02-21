package com.zurtar.mhma

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun JournalNavigation(viewModel: JournalViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "journaling") {
        composable("journaling") {
            JournalingPage(
                viewModel = viewModel,
                onNavigateToEntryModification = { navController.navigate("entryModification") }
            )
        }
        composable("entryModification") {
            EntryModificationPage(
                viewModel = viewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}