package com.zurtar.mhma.analytics

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.zurtar.mhma.data.BiWeeklyEvaluationEntry
import com.zurtar.mhma.mood.findSeverity
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date


@Composable
fun BiWeeklyAnalyticsScreenContent(
    modifier: Modifier = Modifier,
    viewModel: BiWeeklyAnalyticsViewModel = hiltViewModel(),
    onNavigateToSummaryDialog: (BiWeeklyEvaluationEntry?) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val labelToContent: Map<String, @Composable () -> Unit> = mapOf(
        "Mood Graph" to { MoodGraphScreen() },
        "History" to {
            BiWeeklyHistoricalAnalytics(
                biWeeklyEvaluations = uiState.pastEvaluations ?: listOf<BiWeeklyEvaluationEntry>(),
                onNavigateToSummaryDialog = onNavigateToSummaryDialog
            )
        },
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


@Composable
fun BiWeeklyHistoricalAnalytics(
    biWeeklyEvaluations: List<BiWeeklyEvaluationEntry>,
    onNavigateToSummaryDialog: (BiWeeklyEvaluationEntry?) -> Unit
) {

    // Generate results....
    val biWeeklyEvaluations_ = biWeeklyEvaluations.map { x ->
        x.copy(
            depressionResults = findSeverity(x.depressionScore, "depression"),
            anxietyResults = findSeverity(x.anxietyScore, "anxiety")
        )
    }

    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val current = LocalDate.now()
    val currentString = current.format(formatter)
    val yesterday = current.minusDays(1).format(formatter)

    val todays = biWeeklyEvaluations_.filter {
        (it.dateCompleted?.toLocalDate() ?: LocalDate.MIN).format(formatter) == currentString
    }

    val lastWeek = biWeeklyEvaluations_.filter {
        (it.dateCompleted?.toLocalDate() ?: LocalDate.MIN).format(formatter) == yesterday
    }

    val other = biWeeklyEvaluations_.filter {
        (it.dateCompleted?.toLocalDate() ?: LocalDate.MIN) < current.minusDays(2)
    }

    val state = rememberScrollState()
    Column(modifier = Modifier.verticalScroll(state)) {
        //SummaryPopupPreview()



        WeekTitles("Current Week")
        todays.forEach { SummaryCard(it, onNavigateToSummaryDialog) }

        WeekTitles("Last Week")
        lastWeek.forEach { SummaryCard(it, onNavigateToSummaryDialog) }

        WeekTitles("Previous Weeks")
        other.forEach { SummaryCard(it, onNavigateToSummaryDialog) }


    }
}

fun Date.toLocalDate(): LocalDate {
    return java.time.Instant.ofEpochMilli(this.getTime())
        .atZone(java.time.ZoneId.systemDefault())
        .toLocalDate()
}

fun LocalDate.toDate(): Date {
    return Date.from(this.atStartOfDay(ZoneId.systemDefault()).toInstant())
}