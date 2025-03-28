package com.zurtar.mhma.mood

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
import androidx.compose.material3.SecondaryTabRow
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
import com.zurtar.mhma.R
import com.zurtar.mhma.util.DefaultTopAppBar
import java.time.LocalDate
import java.time.format.DateTimeFormatter


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnalyticsScreen(
    modifier: Modifier = Modifier,
    id: Int = 0,
    openDrawer: () -> Unit,
    onNavigateToSummaryDialog: () -> Unit
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
    onNavigateToSummaryDialog: () -> Unit
) {

    var pagerState by remember { mutableIntStateOf(id) }

    val tabs = listOf("Quick", "BiWeekly")
    Column(
        modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Top
    ) {
        PrimaryTabRow(selectedTabIndex = pagerState) {
            tabs.forEachIndexed { index, tabs ->
                Tab(
                    selected = pagerState == index,
                    onClick = { pagerState = index },
                    text = { Text(text = tabs, maxLines = 2, overflow = TextOverflow.Ellipsis) },
                    //modifier = Modifier.background(Color.LightGray)
                )
            }
        }
        when (pagerState) {
            0 -> {
                QuickAnalyticsScreenContent()
            }

            1 -> {
                BiWeeklyAnalyticsScreenContent(onNavigateToSummaryDialog)
            }
        }
    }
}

@Composable
fun QuickAnalyticsScreenContent() {
    Column(Modifier.fillMaxWidth(), verticalArrangement = Arrangement.Center) {
        Text(
            text = "Quick analytics",
            textAlign = TextAlign.Center
        )
    }

}

@Composable
fun BiWeeklyAnalyticsScreenContent(onNavigateToSummaryDialog: () -> Unit) {

    var state by remember { mutableStateOf(0) }
    val tabLabels = listOf("Mood Graph", "Insights", "History")

    Column(
        Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.Center
    ) {
        Row() {
            tabLabels.forEachIndexed { index, label ->
                CustomTab(
                    onClick = { state = index },
                    text = label
                )
            }
        }
        when (state) {
            0 -> {
                MoodGraphScreen()
            }

            1 -> {
                InsightsScreen()
            }

            2 -> {
                BiWeeklySummaryPage(onNavigateToSummaryDialog)
            }
        }
    }
}

@Composable
fun CustomTab(text: String, onClick: () -> Unit) {

    SuggestionChip(
        onClick = onClick,
        label = { Text("${text}") },
        colors = SuggestionChipDefaults.suggestionChipColors(MaterialTheme.colorScheme.secondaryContainer)
    )
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
fun BiWeeklySummaryPage(onNavigateToSummaryDialog: () -> Unit) {
    val results = makeCardInfo()

    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val current = LocalDate.now()
    val currentString = current.format(formatter)
    val yesterday = current.minusDays(1).format(formatter)

    val todays = results.filter {
        it.dateCompleted.format(formatter) == currentString
    }
    val lastWeek = results.filter {
        it.dateCompleted.format(formatter) == yesterday
    }
    val other = results.filter {
        it.dateCompleted < current.minusDays(2)
    }
    val state = rememberScrollState()
    Column(modifier = Modifier.verticalScroll(state)) {
        //SummaryPopupPreview()

        WeekTitles("Current Week")
        todays.forEach { SummaryCards(it, onNavigateToSummaryDialog) }

        WeekTitles("Last Week")
        lastWeek.forEach { SummaryCards(it, onNavigateToSummaryDialog) }

        WeekTitles("Previous Weeks")
        other.forEach { SummaryCards(it, onNavigateToSummaryDialog) }
    }
}


fun onSummaryCard(results: BiWeeklyEvalStat, onNavigateToSummaryDialog: () -> Unit) {

}

@Composable
fun SummaryCards(results: BiWeeklyEvalStat, onNavigateToSummaryDialog: () -> Unit) {

    val colour = MaterialTheme.colorScheme.primaryContainer

    ElevatedCard(
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .clickable { onNavigateToSummaryDialog() }
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
                    text = results.depressionResults,
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
                    text = "${results.depressionScore}"
                )

            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    modifier = Modifier.padding(start = 15.dp),
                    text = results.anxietyResults,
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
                    text = "${results.anxietyScore}"
                )

            }
        }

    }
}


@Composable
fun SummaryPopup(results: BiWeeklyEvalStat) {

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
                text = "Evaluation: Completed ${results.dateCompleted}",
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
                    content = { Text("${results.depressionScore}") })
            }

            Spacer(Modifier.width(15.dp))

            ScoreChart(results.depressionScore, depressionScores, depressionSeverities)

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
                    content = { Text("${results.anxietyScore}") }
                )
            }
            ScoreChart(results.anxietyScore, anxietyScores, anxietySeverities)
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
fun makeCardInfo(): List<BiWeeklyEvalStat> {
    val results = mutableListOf<BiWeeklyEvalStat>()

    val current = LocalDate.now()
    for (i in 0..8) {

        val date = current.minusDays(i.toLong())
        val depressionResult = findSeverity(5 + i, "depression")
        val anxietyResult = findSeverity(2 + i, "anxiety")

        results.add(
            BiWeeklyEvalStat(
                depressionScore = 5 + i,
                anxietyScore = 2 + i,
                depressionResults = depressionResult,
                anxietyResults = anxietyResult,
                dateCompleted = date
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