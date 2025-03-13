package com.zurtar.mhma

import androidx.navigation.NavHostController

class NavigationActions(private val navController: NavHostController) {

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
}