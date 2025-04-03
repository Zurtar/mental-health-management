package com.zurtar.mhma.analytics

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width

import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
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
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zurtar.mhma.R
import com.zurtar.mhma.data.models.BiWeeklyEvaluationEntry
import com.zurtar.mhma.mood.ScoreChart
import com.zurtar.mhma.mood.findSeverity
import com.zurtar.mhma.util.AnalyticsTopAppBar
import java.time.LocalDate
import java.time.format.DateTimeFormatter


/**
 * Displays the main Analytics screen, which includes navigation and a top bar.
 *
 * @param modifier Modifier to be applied to the root composable.
 * @param id The current tab index, defaulting to 0.
 * @param openDrawer A lambda function to open the navigation drawer.
 * @param onNavigateToSummaryDialog A lambda function to handle navigation to the summary dialog for a selected bi-weekly evaluation entry.
 */
@Composable
fun AnalyticsScreen(
    modifier: Modifier = Modifier,
    id: Int = 0,
    openDrawer: () -> Unit,
    onNavigateToSummaryDialog: (BiWeeklyEvaluationEntry?) -> Unit
) {

    Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = {
            AnalyticsTopAppBar(openDrawer = openDrawer)
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

/**
 * Displays the content of the Analytics screen, including a tabbed interface for Quick and BiWeekly tabs.
 *
 * @param modifier Modifier to be applied to the content layout.
 * @param id The current tab index, defaulting to 0.
 * @param onNavigateToSummaryDialog A lambda function to handle navigation to the summary dialog for a selected bi-weekly evaluation entry.
 */
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

/**
 * A composable that displays tabbed content where the active tab's content is displayed below the tab options.
 *
 * @param modifier Modifier to be applied to the layout.
 * @param key The current active key to decide the content to display.
 * @param labelToContent A map where keys represent labels and values are composable content associated with each label.
 * @param wrapperComposable A wrapper composable to surround the label/content layout, optional.
 */
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
                        modifier = Modifier.padding(end = 10.dp),
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

/**
 * Displays a card with the details of a bi-weekly evaluation entry, including depression and anxiety scores.
 *
 * @param result The [BiWeeklyEvaluationEntry] whose data will be displayed in the card.
 * @param onNavigateToSummaryDialog A lambda function to handle navigation to the summary dialog for the selected entry.
 */
@Composable
fun SummaryCard(
    result: BiWeeklyEvaluationEntry,
    onNavigateToSummaryDialog: (BiWeeklyEvaluationEntry?) -> Unit
) {

    val colour = MaterialTheme.colorScheme.primaryContainer
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    ElevatedCard(
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .clickable { onNavigateToSummaryDialog(result) }
    ) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
            Text(
                modifier = Modifier.padding(top = 5.dp, end = 10.dp),
                text = result.dateCompleted?.toLocalDate()?.format(formatter) ?: "null",
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.End,
                fontSize = 12.sp
            )
        }


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp, bottom = 10.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {

            ElevatedButton(
                onClick = {},
                modifier = Modifier
                    .width(40.dp)
                    .align(Alignment.CenterVertically),
                colors = ButtonDefaults.buttonColors(),
                contentPadding = PaddingValues(0.dp)
            ) {
                Text(
                    text = "${result.depressionScore}",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Text(
                modifier = Modifier.padding(start = 25.dp),
                text = result.depressionResults,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                fontSize = 16.sp
            )

        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {

            ElevatedButton(
                onClick = {},
                modifier = Modifier
                    .width(40.dp)
                    .align(Alignment.CenterVertically),
                colors = ButtonDefaults.buttonColors(),
                contentPadding = PaddingValues(0.dp)
            ) {
                Text(
                    text = "${result.anxietyScore}",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Text(
                modifier = Modifier.padding(start = 25.dp),
                text = result.anxietyResults,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                fontSize = 16.sp
            )
        }
    }
}

/**
 * Displays a popup showing detailed information about a bi-weekly evaluation entry, including depression and anxiety scores.
 *
 * @param entry The [BiWeeklyEvaluationEntry] whose detailed information will be shown.
 */
@Composable
fun SummaryPopup(entry: BiWeeklyEvaluationEntry) {

    val depressionScores: List<String> = stringArrayResource(R.array.depression_scores).toList()
    val depressionSeverities: List<String> =
        stringArrayResource(R.array.depression_severities).toList()

    val anxietyScores: List<String> = stringArrayResource(R.array.anxiety_scores).toList()
    val anxietySeverities: List<String> = stringArrayResource(R.array.anxiety_severities).toList()

    ElevatedCard {
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
            Spacer(modifier = Modifier.height(15.dp))
        }

    }

}

/**
 * Helper function to generate mock bi-weekly evaluation entries with sequential scores.
 *
 * @return A list of mock [BiWeeklyEvaluationEntry] entries for the preview.
 */
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

/**
 * Displays a title for a given week category in the analytics view.
 *
 * @param title The title text to be displayed (e.g., "Current Week", "Last Week").
 */
@Composable
fun WeekTitles(title: String) {
    Text(
        modifier = Modifier.padding(10.dp),
        text = title,
        style = MaterialTheme.typography.titleLarge,
        color = MaterialTheme.colorScheme.primary
    )

}
