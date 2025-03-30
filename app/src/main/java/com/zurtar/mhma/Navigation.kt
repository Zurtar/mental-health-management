package com.zurtar.mhma

import android.util.Log
import androidx.navigation.NavHostController
import com.zurtar.mhma.data.BiWeeklyEvaluationEntry
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class Navigation(private val navController: NavHostController) {

    fun navigateToHome() {
//        navController.navigate(Home) {
        navController.navigate("Home") {
            restoreState = true
        }
    }

    fun navigateToAccount() {
//        navController.navigate(Account) {
        navController.navigate("Account") {
            restoreState = true
        }
    }

    fun navigateToLogin() {
//
        navController.navigate("Login") {
            restoreState = true
        }
    }

    fun navigateToSignup() {
//        navController.navigate(SignUp) {
        navController.navigate("SignUp") {
            restoreState = true
        }
    }

    fun navigateToJournal() {
//        navController.navigate(Journal) {
        navController.navigate("Journal") {
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
//        navController.navigate(MoodEvaluation) {
        navController.navigate("MoodEvaluation") {
            restoreState = true
        }
    }

    fun navigateToDailyEvaluation() {
//        navController.navigate(DailyEvaluation) {
        navController.navigate("DailyEvaluation") {
            restoreState = true
        }
    }

    fun navigateToBiWeeklyEvaluation() {
//        navController.navigate(BiWeeklyEvaluation) {
        navController.navigate("BiWeeklyEvaluation") {
            restoreState = true
        }
    }

    //Added dialog navigation for biweekly summary page
    fun navigateToSummaryDialog(entry: BiWeeklyEvaluationEntry? = null) {
//        navController.navigate(SummaryDialog) {

        var route = "SummaryDialog"
        if (entry != null)
            route = "${route}/${Json.encodeToString(entry)}"

        Log.println(Log.INFO, "NavigateToSummaryDialog", route)

        navController.navigate(route) {
            restoreState = true
        }
    }

    fun navigateToAnalytics(id: Int = 0) {
        navController.navigate(route = "${Analytics}/${id}") {
            restoreState = true
        }
    }

    fun navigateToChatbot() {
        navController.navigate(Chatbot) {
            restoreState = true
        }
    }

    fun navigateToChatList() {
        navController.navigate(ChatList) {
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
object ChatList

@Serializable
object ChatLog

//Added Anayltics for navigation
@Serializable
object Analytics

//Added dialog navigation for biweekly summary page
@Serializable
object SummaryDialog