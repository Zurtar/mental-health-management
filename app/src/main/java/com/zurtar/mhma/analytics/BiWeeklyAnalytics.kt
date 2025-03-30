package com.zurtar.mhma.analytics

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import java.time.LocalDate
import java.time.format.DateTimeFormatter


@Composable
fun BiWeeklyAnalyticsScreenContent(onNavigateToSummaryDialog: () -> Unit) {
    val labelToContent: Map<String, @Composable () -> Unit> = mapOf(
        "Mood Graph" to { MoodGraphScreen() },
        "History" to { InsightsScreen() },
        "Insights" to { }
    )

    TabbedContent(labelToContent = labelToContent, key = labelToContent.keys.first())
}

@Composable
fun MoodGraphScreen() {
    Text("Mood Graph")
}

@Composable
fun InsightsScreen() {
    Text("Insights")
}


//@Composable
//fun BiWeeklySummaryPage(onNavigateToSummaryDialog: () -> Unit) {
//    val results = makeCardInfo()
//
//    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
//    val current = LocalDate.now()
//    val currentString = current.format(formatter)
//    val yesterday = current.minusDays(1).format(formatter)
//
//    val todays = results.filter {
//        (it.dateCompleted?.toLocalDate()?.format(formatter) ?: "") == currentString
//    }
//    val lastWeek = results.filter {
//        (it.dateCompleted?.toLocalDate()?.format(formatter) ?: "") == yesterday
//    }
//    val other = results.filter {
//        (it.dateCompleted?.toLocalDate() ?: "") < current.minusDays(2)
//    }
//    val state = rememberScrollState()
//    Column(modifier = Modifier.verticalScroll(state)) {
//        //SummaryPopupPreview()
//
//        WeekTitles("Current Week")
//        todays.forEach { SummaryCards(it, onNavigateToSummaryDialog) }
//
//        WeekTitles("Last Week")
//        lastWeek.forEach { SummaryCards(it, onNavigateToSummaryDialog) }
//
//        WeekTitles("Previous Weeks")
//        other.forEach { SummaryCards(it, onNavigateToSummaryDialog) }
//    }
//}