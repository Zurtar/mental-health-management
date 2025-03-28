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

    //Added dialog navigation for biweekly summary page
    fun navigateToSummaryDialog() {
        navController.navigate(SummaryDialog) {
            restoreState = true
        }
    }

    fun navigateToAnalytics(id: Int = 0) {
        navController.navigate(route = "${Analytics}/${id}") {
            restoreState = true
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

//Added Anayltics for navigation
@Serializable
object Analytics

//Added dialog navigation for biweekly summary page
@Serializable
object SummaryDialog