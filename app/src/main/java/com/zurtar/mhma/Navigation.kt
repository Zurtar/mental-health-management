package com.zurtar.mhma

import androidx.navigation.NavHostController
import kotlinx.serialization.Serializable

class Navigation(private val navController: NavHostController) {

    fun navigateToHome() {
        navController.navigate(Home) {
            restoreState = true
        }
    }

    fun navigateToAccount() {
        navController.navigate(Account) {
            restoreState = true
        }
    }

    fun navigateToLogin() {
        navController.navigate(Login) {
            restoreState = true
        }
    }

    fun navigateToSignup() {
        navController.navigate(SignUp) {
            restoreState = true
        }
    }

    fun navigateToJournal() {
        navController.navigate(Journal) {
            restoreState = true
        }
    }

    fun navigateToEntryView(id: Int) {
        navController.navigate("entryView/$id")
    }

    fun navigateToEntryEdit(id: Int) {
        navController.navigate("entryEdit/$id")
    }

    fun navigateToJournalEntryR(){
        navController.navigate(JournalEntryR)
    }

    fun navigateToMoodEvaluation() {
        navController.navigate(MoodEvaluation) {
            restoreState = true
        }
    }


    fun navigateToDailyEvaluation() {
        navController.navigate(DailyEvaluation) {
            restoreState = true
        }
    }

    fun navigateToBiWeeklyEvaluation() {
        navController.navigate(BiWeeklyEvaluation) {
            restoreState = true
        }
    }

    fun navigateToChatbot() {
        navController.navigate(Chatbot) {
            restoreState = true
        }
    }

    fun navigateToChatList() {
        navController.navigate(ChatListPage) {
            restoreState = true
        }
    }

    fun navigateToChatLog(logId: Int) {
        navController.navigate("chatlog/$logId")
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

@Serializable
object Chatbot

@Serializable
object ChatListPage

@Serializable
object ChatLogPage