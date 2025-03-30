package com.zurtar.mhma.analytics

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.zurtar.mhma.R
import com.zurtar.mhma.data.BiWeeklyEvaluationEntry
import com.zurtar.mhma.mood.ScoreChart
import com.zurtar.mhma.mood.findSeverity
import com.zurtar.mhma.util.DefaultTopAppBar
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date


@Composable
fun AnalyticsScreen(
    modifier: Modifier = Modifier,
    id: Int = 0,
    openDrawer: () -> Unit,
    onNavigateToSummaryDialog: (BiWeeklyEvaluationEntry?) -> Unit
) {

    Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = {
            DefaultTopAppBar(openDrawer = openDrawer)
        }
    ) { innerPadding ->
        AnalyticsScreenContent(
            id = id,
            modifier = modifier
                .padding(innerPadding)
                .fillMaxSize(),
            onNavigateToSummaryDialog = onNavigateToSummaryDialog
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnalyticsScreenContent(
    modifier: Modifier = Modifier,
    id: Int = 0,
    onNavigateToSummaryDialog: (BiWeeklyEvaluationEntry?) -> Unit
) {

    var pagerState by remember { mutableIntStateOf(id) }
    val tabs = listOf("Quick", "BiWeekly")


    Column(
        modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Top,
    ) {
        PrimaryTabRow(selectedTabIndex = pagerState) {
            tabs.forEachIndexed { index, tabs ->
                Tab(
                    selected = pagerState == index,
                    onClick = { pagerState = index },
                    text = {
                        Text(
                            text = tabs,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                    },
                    //modifier = Modifier.background(Color.LightGray)
                )
            }
        }
        when (pagerState) {
            0 -> {
                DailyAnalyticsScreenContent()
            }

            1 -> {
                BiWeeklyAnalyticsScreenContent(onNavigateToSummaryDialog = onNavigateToSummaryDialog)
            }
        }
    }
}

@Composable
fun TabbedContent(
    modifier: Modifier = Modifier,
    key: String = "",
    labelToContent: Map<String, @Composable () -> Unit>,
    wrapperComposable: (@Composable () -> Unit)? = null
) {
    var state by remember { mutableStateOf(key) }


    Column(
        modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.Center
    ) {
        Row(modifier = Modifier.padding(start = 5.dp, top = 5.dp)) {
            labelToContent.keys.forEach { key ->

                if (wrapperComposable == null)
                    SuggestionChip(
                        onClick = { state = key },
                        label = { Text(key) },
                        colors = SuggestionChipDefaults.suggestionChipColors(MaterialTheme.colorScheme.secondaryContainer)
                    )
                else {
                    wrapperComposable()
                }
            }
        }
        labelToContent[state]?.invoke() ?: Text("INVALID_STATE")
    }
}


@Composable
fun QuickEvaluationCalendar(
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        AppHorizontalCalendar()

        Text(
            modifier = Modifier.padding(start = 10.dp),
            text = "Selected Date:",
            style = MaterialTheme.typography.headlineSmall
        )
    }
}

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
fun SummaryCard(
    result: BiWeeklyEvaluationEntry,
    onNavigateToSummaryDialog: (BiWeeklyEvaluationEntry?) -> Unit
) {

    val colour = MaterialTheme.colorScheme.primaryContainer

    ElevatedCard(
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .clickable { onNavigateToSummaryDialog(result) }
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                modifier = Modifier.padding(start = 5.dp),
                text = "Mood:",
                style = MaterialTheme.typography.titleMedium
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    modifier = Modifier.padding(start = 15.dp),
                    text = result.depressionResults,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center
                )
                Text(
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                        .padding(end = 30.dp, bottom = 20.dp)
                        .drawBehind {
                            drawCircle(
                                color = colour,
                                radius = this.size.maxDimension
                            )
                        },
                    text = "${result.depressionScore}"
                )

            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    modifier = Modifier.padding(start = 15.dp),
                    text = result.anxietyResults,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center
                )
                Text(
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                        .padding(end = 30.dp, top = 20.dp, bottom = 20.dp)
                        .drawBehind {
                            drawCircle(
                                color = colour,
                                radius = this.size.maxDimension
                            )
                        },
                    text = "${result.anxietyScore}"
                )

            }
        }

    }
}


@Composable
fun SummaryPopup(entry: BiWeeklyEvaluationEntry) {

    val depressionScores: List<String> = stringArrayResource(R.array.depression_scores).toList()
    val depressionSeverities: List<String> =
        stringArrayResource(R.array.depression_severities).toList()

    val anxietyScores: List<String> = stringArrayResource(R.array.anxiety_scores).toList()
    val anxietySeverities: List<String> = stringArrayResource(R.array.anxiety_severities).toList()

    ElevatedCard() {
        Column(
            Modifier.fillMaxWidth(),
            // horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            Text(
                modifier = Modifier.padding(vertical = 10.dp, horizontal = 5.dp),
                text = "Evaluation: Completed ${entry.dateCompleted?.toLocalDate()}",
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Start
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    modifier = Modifier.padding(vertical = 5.dp, horizontal = 15.dp),
                    text = "Depression Score",
                    style = MaterialTheme.typography.bodyMedium
                )
                FilledTonalButton(
                    onClick = {},
                    enabled = true,
                    content = { Text("${entry.depressionScore}") })
            }

            Spacer(Modifier.width(15.dp))

            ScoreChart(entry.depressionScore, depressionScores, depressionSeverities)

            Spacer(Modifier.width(30.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    modifier = Modifier.padding(vertical = 5.dp, horizontal = 15.dp),
                    text = "Anxiety Score",
                    style = MaterialTheme.typography.bodyMedium
                )
                FilledTonalButton(
                    modifier = Modifier.padding(horizontal = 5.dp),
                    onClick = {},
                    enabled = true,
                    content = { Text("${entry.anxietyScore}") }
                )
            }
            ScoreChart(entry.anxietyScore, anxietyScores, anxietySeverities)
        }

    }

}

@Preview
@Composable
fun SummaryPopupScreen() {
    val results = makeCardInfo()
    Log.println(Log.DEBUG, "BiWeeklyEvalVM", "Depression Score: ${results[0].depressionScore}")

    SummaryPopup(results[0])
}

//HELPER FUNCTIONS

@Composable
fun makeCardInfo(): List<BiWeeklyEvaluationEntry> {
    val results = mutableListOf<BiWeeklyEvaluationEntry>()

    val current = LocalDate.now()
    for (i in 0..8) {

        val localDate = current.minusDays(i.toLong())
        val depressionResult = findSeverity(5 + i, "depression")
        val anxietyResult = findSeverity(2 + i, "anxiety")

        results.add(
            BiWeeklyEvaluationEntry(
                depressionScore = 5 + i,
                anxietyScore = 2 + i,
                depressionResults = depressionResult,
                anxietyResults = anxietyResult,
                dateCompleted = localDate.toDate()
            )
        )
    }

    return results.toList()
}

@Composable
fun WeekTitles(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleMedium,
        color = MaterialTheme.colorScheme.primary
    )

}
