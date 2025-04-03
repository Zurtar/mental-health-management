package com.zurtar.mhma

import android.util.Log
import androidx.navigation.NavHostController
import com.zurtar.mhma.data.models.BiWeeklyEvaluationEntry
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

/**
 * A helper class for navigating between different screens in the app using `NavHostController`.
 * This class provides various navigation methods for different routes within the app.
 *
 * @property navController The `NavHostController` instance used for navigation between composable screens.
 */
class Navigation(private val navController: NavHostController) {

    /**
     * Navigates to the Home screen.
     *
     * This method navigates to the "Home" route and restores the previous state when possible.
     */
    fun navigateToHome() {
        navController.navigate("Home") {
            restoreState = true
        }
    }

    /**
     * Navigates to the Account screen.
     *
     * This method navigates to the "Account" route and restores the previous state when possible.
     */
    fun navigateToAccount() {
        navController.navigate("Account") {
            restoreState = true
        }
    }

    /**
     * Navigates to the Login screen.
     *
     * This method navigates to the "Login" route and restores the previous state when possible.
     */
    fun navigateToLogin() {
        navController.navigate("Login") {
            restoreState = true
        }
    }

    /**
     * Navigates to the Signup screen.
     *
     * This method navigates to the "SignUp" route and restores the previous state when possible.
     */
    fun navigateToSignup() {
        navController.navigate("SignUp") {
            restoreState = true
        }
    }

    /**
     * Navigates to the Journal screen.
     *
     * This method navigates to the "Journal" route and restores the previous state when possible.
     */
    fun navigateToJournal() {
        navController.navigate("Journal") {
            restoreState = true
        }
    }

    /**
     * Navigates to the Entry View screen.
     *
     * This method navigates to the "entryView/{id}" route, where `{id}` is the unique identifier of the entry.
     *
     * @param id The unique identifier for the journal entry to view.
     */
    fun navigateToEntryView(id: String) {
        navController.navigate("entryView/$id")
    }

    /**
     * Navigates to the Entry Edit screen.
     *
     * This method navigates to the "entryEdit/{id}" route, where `{id}` is the unique identifier of the entry to edit.
     *
     * @param id The unique identifier for the journal entry to edit.
     */
    fun navigateToEntryEdit(id: String) {
        navController.navigate("entryEdit/$id")
    }

    /**
     * Navigates to the Journal Entry Modification screen.
     *
     * This method navigates to the "entryEdit/{id}" route, allowing modification of the specified journal entry.
     * If no ID is provided, a new entry is assumed.
     *
     * @param id The unique identifier for the journal entry to modify. Default is `null`.
     */
    fun navigateToJournalEntryModification(id: String? = null) {
        navController.navigate("entryEdit/$id")
    }

    /**
     * Navigates to the Mood Evaluation screen.
     *
     * This method navigates to the "MoodEvaluation" route and restores the previous state when possible.
     */
    fun navigateToMoodEvaluation() {
        navController.navigate("MoodEvaluation") {
            restoreState = true
        }
    }

    /**
     * Navigates to the Daily Evaluation screen.
     *
     * This method navigates to the "DailyEvaluation" route and restores the previous state when possible.
     */
    fun navigateToDailyEvaluation() {
        navController.navigate("DailyEvaluation") {
            restoreState = true
        }
    }

    /**
     * Navigates to the Bi-Weekly Evaluation screen.
     *
     * This method navigates to the "BiWeeklyEvaluation" route and restores the previous state when possible.
     */
    fun navigateToBiWeeklyEvaluation() {
        navController.navigate("BiWeeklyEvaluation") {
            restoreState = true
        }
    }

    /**
     * Navigates to the Bi-Weekly Evaluation Summary Dialog.
     *
     * This method navigates to the "SummaryDialog" route, optionally passing a `BiWeeklyEvaluationEntry` object
     * as part of the route to display specific evaluation details.
     *
     * @param entry The optional `BiWeeklyEvaluationEntry` to display in the dialog.
     */
    fun navigateToSummaryDialog(entry: BiWeeklyEvaluationEntry? = null) {
        var route = "SummaryDialog"
        if (entry != null)
            route = "${route}/${Json.encodeToString(entry)}"

        Log.println(Log.INFO, "NavigateToSummaryDialog", route)

        navController.navigate(route) {
            restoreState = true
        }
    }

    /**
     * Navigates to the Analytics screen.
     *
     * This method navigates to the "Analytics/{id}" route, where `{id}` is an optional parameter that can be used
     * to pass specific data to the analytics screen.
     *
     * @param id The optional identifier for the analytics data. Default is `0`.
     */
    fun navigateToAnalytics(id: Int = 0) {
        navController.navigate(route = "${Analytics}/${id}") {
            restoreState = true
        }
    }

    /**
     * Navigates to the Chatbot screen.
     *
     * This method navigates to the "Chatbot" route and restores the previous state when possible.
     */
    fun navigateToChatbot() {
        navController.navigate("Chatbot") {
            restoreState = true
        }
    }

    /**
     * Navigates to the Chat List screen.
     *
     * This method navigates to the "ChatList" route and restores the previous state when possible.
     */
    fun navigateToChatList() {
        navController.navigate("ChatList") {
            restoreState = true
        }
    }

    /**
     * Navigates to the Chat Log screen.
     *
     * This method navigates to the "ChatLog/{logId}" route, where `{logId}` is the unique identifier of the chat log.
     *
     * @param logId The unique identifier for the chat log to view.
     */
    fun navigateToChatLog(logId: String) {
        navController.navigate("ChatLog/$logId")
    }
}

/**
 * Nav Host Objects
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