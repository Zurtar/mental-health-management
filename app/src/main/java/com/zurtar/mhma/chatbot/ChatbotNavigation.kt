package com.zurtar.mhma.chatbot

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

/*
Composable function for navigation between different chatbot pages

The start page is ChatbotPage: used for dialogue between user and chatbot
ChatlistPage: used to display to user a list of the logs that they have finished.
ChatLogPage: used to display the contents of previously created logs.
 */
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ChatBotNavigation(
    viewModel: ChatbotViewModel,
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "chatbot",
        modifier = modifier
    ) {
        composable("chatbot") {
            ChatbotPage(
                viewModel = viewModel,
                onNavigateToChatList = { navController.navigate("chatlist") }
            )
        }
        composable("chatlist") {
            ChatListPage(
                viewModel = viewModel,
                onNavigateToChatbot = { navController.navigate("chatbot")},
                onNavigateToChatLog = { logId -> navController.navigate("chatlog/$logId") }
            )
        }
        //Destination log to display is determined from {logId}
        composable(
            "chatlog/{logId}",
            arguments = listOf(navArgument("logId") { type = NavType.IntType })
        ) { backStackEntry ->
            val logId = backStackEntry.arguments?.getInt("logId") ?: 0
            ChatLogPage(
                logId = logId,
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}