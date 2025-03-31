package com.zurtar.mhma.analytics

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Label
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.himanshoe.charty.common.ChartColor
import com.himanshoe.charty.common.LabelConfig
import com.himanshoe.charty.line.LineChart
import com.himanshoe.charty.line.config.LineChartAxisConfig
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


@Composable
fun BiWeeklyAnalyticsScreenContent(
    modifier: Modifier = Modifier,
    viewModel: BiWeeklyAnalyticsViewModel = hiltViewModel(),
    onNavigateToSummaryDialog: (BiWeeklyEvaluationEntry?) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val labelToContent: Map<String, @Composable () -> Unit> = mapOf(
        //MoodGraphScreen(uiState.graphData!!)
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
        // "Insights" to { }
    )

    TabbedContent(labelToContent = labelToContent, key = labelToContent.keys.first())
}

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
    return Instant.ofEpochMilli(this.getTime())
        .atZone(ZoneId.systemDefault())
        .toLocalDate()
}

fun LocalDate.toDate(): Date {
    return Date.from(this.atStartOfDay(ZoneId.systemDefault()).toInstant())
}