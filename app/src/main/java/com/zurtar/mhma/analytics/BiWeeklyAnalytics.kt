package com.zurtar.mhma.analytics

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.himanshoe.charty.common.ChartColor
import com.himanshoe.charty.common.LabelConfig
import com.himanshoe.charty.line.LineChart
import com.himanshoe.charty.line.config.LineChartColorConfig
import com.himanshoe.charty.line.config.LineChartConfig
import com.himanshoe.charty.line.config.LineConfig
import com.himanshoe.charty.line.model.LineData
import com.zurtar.mhma.data.models.BiWeeklyEvaluationEntry
import com.zurtar.mhma.mood.findSeverity
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date

/**
 * A composable function that displays the Bi-Weekly Analytics screen.
 * It uses `TabbedContent` to present different sections such as the mood graph and evaluation history.
 * The content is dynamically updated based on the data from the ViewModel.
 *
 * @param modifier The modifier to be applied to the root composable.
 * @param viewModel The ViewModel that provides the UI state and manages the bi-weekly evaluations.
 * @param onNavigateToSummaryDialog A lambda function that handles navigation to a summary dialog for each evaluation entry.
 */
@Composable
fun BiWeeklyAnalyticsScreenContent(
    modifier: Modifier = Modifier,
    viewModel: BiWeeklyAnalyticsViewModel = hiltViewModel(),
    onNavigateToSummaryDialog: (BiWeeklyEvaluationEntry?) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val labelToContent: Map<String, @Composable () -> Unit> = mapOf(
        "Mood Graph" to {
            MoodGraphScreen(
                uiState.pastEvaluations ?: listOf<BiWeeklyEvaluationEntry>()
            )
        },
        "History" to {
            BiWeeklyHistoricalAnalytics(
                biWeeklyEvaluations = uiState.pastEvaluations ?: listOf<BiWeeklyEvaluationEntry>(),
                onNavigateToSummaryDialog = onNavigateToSummaryDialog
            )
        },
    )
    TabbedContent(labelToContent = labelToContent, key = labelToContent.keys.first())
}

/**
 * A composable function that displays the mood graph screen for bi-weekly evaluations.
 * It generates line charts showing depression and anxiety scores over time, grouped by evaluation entries.
 *
 * @param biWeeklyEvaluations The list of bi-weekly evaluation entries used to plot the graph.
 */
@Composable
fun MoodGraphScreen(biWeeklyEvaluations: List<BiWeeklyEvaluationEntry>) {

    var lineData: MutableList<LineData> = mutableListOf()


    biWeeklyEvaluations.forEach { entries ->

        lineData.add(
            LineData(
                yValue = entries.depressionScore.toFloat(),
                xValue = entries.dateCompleted!!.toLocalDate()
                    .format(DateTimeFormatter.ofPattern("d MMM")) + ' '
            )
        )
    }

    val depressionDate = lineData.toList()

    lineData.clear()
    biWeeklyEvaluations.forEach { entries ->
        lineData.add(
            LineData(
                yValue = entries.anxietyScore.toFloat(),
                xValue = entries.dateCompleted!!.toLocalDate()
                    .format(DateTimeFormatter.ofPattern("d MMM")) + ' '
            )
        )
    }
    val anxietyData = lineData.toList()

    Column(modifier = Modifier.fillMaxWidth()) {

        Text("Depression Scores Over Time")
        DepressionGraph(depressionDate)
        Spacer(Modifier.height((15.dp)))
        Text("Anxiety Scores Over Time")
        DepressionGraph(anxietyData)
    }

}

/**
 * A composable function that renders a line graph for either depression or anxiety scores over time.
 *
 * @param data The list of [LineData] representing the scores and dates.
 */
@Composable
fun DepressionGraph(data: List<LineData>) {

    val labelConfig = LabelConfig(
        showXLabel = true,
        showYLabel = true,
        textColor = ChartColor.Solid(MaterialTheme.colorScheme.primary),
        xAxisCharCount = 10,
        labelTextStyle = MaterialTheme.typography.bodyMedium
    )

    ElevatedCard(modifier = Modifier.height(200.dp)) {
        LineChart(
            data = { data },
            smoothLineCurve = true,
            colorConfig = LineChartColorConfig.default(),
            labelConfig = labelConfig,
            chartConfig = LineChartConfig(
                lineConfig = LineConfig(showValueOnLine = true)
            )
        )
    }
}

/**
 * A composable function that displays a list of bi-weekly evaluations, categorized by the current week,
 * last week, and previous weeks. It also computes the severity for depression and anxiety scores and
 * displays them accordingly.
 *
 * @param biWeeklyEvaluations The list of bi-weekly evaluation entries to display.
 * @param onNavigateToSummaryDialog A lambda function that handles navigation to a summary dialog for each evaluation.
 */
@Composable
fun BiWeeklyHistoricalAnalytics(
    biWeeklyEvaluations: List<BiWeeklyEvaluationEntry>,
    onNavigateToSummaryDialog: (BiWeeklyEvaluationEntry?) -> Unit
) {

    // Generate results....
    var biWeeklyEvaluations_ = biWeeklyEvaluations.map { x ->
        x.copy(
            depressionResults = findSeverity(x.depressionScore, "depression"),
            anxietyResults = findSeverity(x.anxietyScore, "anxiety")
        )
    }

    biWeeklyEvaluations_ = biWeeklyEvaluations_.sortedByDescending { ele ->
        ele.dateCompleted?.toLocalDate() ?: LocalDate.MIN
    }


    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val current = LocalDate.now()
    val currentDay = current.dayOfWeek.ordinal

    val currentWeek = current.minusDays((currentDay - 1).toLong())
    val currentString = current.format(formatter)
    val previous = currentWeek.minusWeeks(2)

    val todays = biWeeklyEvaluations_.filter {
        (it.dateCompleted?.toLocalDate() ?: LocalDate.MIN) >= currentWeek
    }

    val lastWeek = biWeeklyEvaluations_.filter {
        var d = (it.dateCompleted?.toLocalDate() ?: LocalDate.MIN)

        d >= previous && d < currentWeek
    }

    val other = biWeeklyEvaluations_.filter {
        (it.dateCompleted?.toLocalDate() ?: LocalDate.MIN) < previous
    }

    val state = rememberScrollState()
    Column(modifier = Modifier.verticalScroll(state)) {
        WeekTitles("Current Week")
        todays.forEach { SummaryCard(it, onNavigateToSummaryDialog) }

        WeekTitles("Last Week")
        lastWeek.forEach { SummaryCard(it, onNavigateToSummaryDialog) }

        WeekTitles("Previous Weeks")
        other.forEach { SummaryCard(it, onNavigateToSummaryDialog) }
    }
}

/**
 * Extension function to convert a [Date] object to a [LocalDate].
 * This function converts the date's epoch time to an Instant, then uses the system's default time zone
 * to convert it to a [LocalDate].
 *
 * @return The corresponding [LocalDate] representation of the [Date] object.
 */
fun Date.toLocalDate(): LocalDate {
    return Instant.ofEpochMilli(this.getTime())
        .atZone(ZoneId.systemDefault())
        .toLocalDate()
}

/**
 * Extension function to convert a [LocalDate] to a [Date].
 * This function converts the [LocalDate] to a [Date] by first setting it to the start of the day,
 * then converting it to an Instant and finally to a [Date] object.
 *
 * @return The corresponding [Date] representation of the [LocalDate].
 */
fun LocalDate.toDate(): Date {
    return Date.from(this.atStartOfDay(ZoneId.systemDefault()).toInstant())
}