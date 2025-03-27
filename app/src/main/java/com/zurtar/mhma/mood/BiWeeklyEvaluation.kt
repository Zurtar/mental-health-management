package com.zurtar.mhma.mood

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.zurtar.mhma.util.DefaultTopAppBar
import com.zurtar.mhma.R
import java.time.LocalDate
import java.time.format.DateTimeFormatter


data class BiWeeklyEvalStat(
        var depressionScore: Int,
        var anxietyScore: Int,
        var dateCompleted: LocalDate,
        var depressionResults: String = "",
        var anxietyResults: String = ""

    )
@Composable
fun BiWeeklyEvaluationScreen(
    modifier: Modifier = Modifier,
    viewModel: BiWeeklyEvaluationViewModel = viewModel(),
    openDrawer: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(modifier = modifier.fillMaxSize(),
        topBar = {
            DefaultTopAppBar(openDrawer = openDrawer)
        }
    ) { innerPadding ->
        BiWeeklyEvaluationScreenContent(
            modifier = modifier
                .padding(innerPadding)
                .fillMaxSize(),
            page = uiState.page,
            score = uiState.depressionScore,
            questionResponses = uiState.questionResponse,
            onSelect = viewModel::onSelect,
            onBack = viewModel::onBack,
            onNext = viewModel::onNext,
        )
    }
}

@Composable
private fun BiWeeklyEvaluationScreenContent(
    modifier: Modifier = Modifier,
    page: Int,
    score: Int,
    questionResponses: List<Int>,
    onSelect: (Int) -> Unit,
    onBack: () -> Unit,
    onNext: () -> Unit,
) {
    val questions: Array<String> = stringArrayResource(R.array.phq_9_questions)

    if (page == questions.size) {
//        BiWeeklyResult(modifier = modifier, score)
        AnalyticsTab()
        return
    }

    Column(
        modifier = modifier.background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Over the last 2 weeks, have you felt...",
            modifier = Modifier
                .align(Alignment.Start)
                .padding(start = 5.dp, top = 15.dp),
            style = MaterialTheme.typography.headlineSmall
        )

        QuestionCard(
            num = page,
            question = questions[page],
            selectedOption = questionResponses[page],
            onSelect = onSelect
        )


        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            if (page > 0)
                FilledTonalButton(
                    onClick = { onBack() },
                    content = { Text("Back") })

            var text = "Next"
            if (page == questions.size - 1) text = "Submit"
            FilledTonalButton(onClick = { onNext() }, content = { Text(text) })
        }
    }
}

@Composable
fun QuestionCard(
    modifier: Modifier = Modifier,
    num: Int,
    question: String,
    selectedOption: Int,
    onSelect: (Int) -> Unit
) {
    val radioOptions =
        listOf("Not at all", "Several days", "More than half the days", "Nearly every day")

    ElevatedCard(
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        modifier = modifier
            .padding(bottom = 15.dp, top = 15.dp)
            .fillMaxWidth(0.85f)
    ) {
        Column(modifier = Modifier.padding(all = 15.dp)) {
            Text(text = question)
            QuestionResponse(radioOptions, selectedOption, onSelect)

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(all = 8.dp)
            ) {
                Text(
                    modifier = Modifier.align(Alignment.BottomCenter),
                    textAlign = TextAlign.Center,
                    color = Color.Gray,
                    text = "Question ${num + 1}"
                )
            }
        }
    }
}

@Composable
fun QuestionResponse(radioOptions: List<String>, selectedOption: Int, onSelect: (Int) -> Unit) {
    Column(
        modifier = Modifier
            .selectableGroup()
            .fillMaxWidth()
            .padding(start = 10.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        radioOptions.forEachIndexed { index, text ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .height(90.dp)
                    .padding(all = 8.dp)
                    .clip(
                        shape = RoundedCornerShape(
                            size = 12.dp,
                        )
                    )
                    .clickable {
                        onSelect(index)
                    }
                    .background(
                        if (index == selectedOption) MaterialTheme.colorScheme.inversePrimary
                        else MaterialTheme.colorScheme.onSecondary
                    ),
            ) {
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    textAlign = TextAlign.Center,
                    text = text
                )
            }
        }
    }
}


@Composable
fun BiWeeklyResult(
    modifier: Modifier = Modifier, depressionScore: Int, anxietyScore: Int
) {

    val scores = listOf("1-4", "5-9", "10-14", "15-19", "20-27")
    val severities = listOf(
        "Minimal depression",
        "Mild depression",
        "Moderate depression",
        "Moderately severe depression",
        "Severe depression"
    )

    val text = if (depressionScore < 10 || anxietyScore < 10) {
        "unlikely"
    } else {
        "likely"
    }


    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "You have completed the Bi-Weekly evaluation!",
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(25.dp))

        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Depression Score:",
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center
                )
                FilledTonalButton(onClick = {}, enabled = true, content = { Text("0") })

            }
            Spacer(Modifier.width(15.dp))
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Anxiety Score",
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center
                )
                FilledTonalButton(onClick = {}, enabled = true, content = { Text("0") })
            }
        }
        ScoreChart(depressionScore, scores, severities)

        Spacer(Modifier.height(15.dp))

        Text(
            text = "Based on your given score it is ${text} that you may be experiencing depression",
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.height(15.dp))

        ProceedCard("Proceed to Evaluation Summary")
        ProceedCard("Proceed to Evaluation Analytics")

        FilledTonalButton(onClick = {},
            enabled = true,
            colors = ButtonColors(
                containerColor = MaterialTheme.colorScheme.inversePrimary,
                contentColor = Color.Black,
                disabledContainerColor = MaterialTheme.colorScheme.inversePrimary,
                disabledContentColor = MaterialTheme.colorScheme.inversePrimary
            ),
            content = { Text("Exit") })

    }
}

@Composable
fun ProceedCard(text: String) {

    OutlinedCard(
        modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        border = BorderStroke(1.dp, Color.Black)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(
                text = text
            )
            FilledTonalButton(
                onClick = { },
                colors = ButtonColors(
                    containerColor = Color.Transparent,
                    contentColor = Color.Black,
                    disabledContainerColor = Color.Transparent,
                    disabledContentColor = Color.Transparent
                )
            ) {

                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = "Favorite",
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }

}


@Composable
fun BiWeeklyResultsPrev() {
    Column(modifier = Modifier.background(MaterialTheme.colorScheme.background)) {
        BiWeeklyResult(modifier = Modifier.padding(1.dp), depressionScore = 13, anxietyScore = 12)

    }
}


@Preview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnalyticsTab() {

    var pagerState by remember { mutableIntStateOf(1) }

    val tabs = listOf("Quick", "BiWeekly")
    Column(
        Modifier
            .fillMaxWidth()
            .padding(top = 90.dp), verticalArrangement = Arrangement.Top
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
                // Content for the first tab (e.g., HomeScreen)
                QuickAnalyticsScreen()
            }

            1 -> {
                // Content for the second tab (e.g., SearchScreen)
                BiWeeklyAnalyticsScreen()
            }
        }
    }
}

@Composable
fun QuickAnalyticsScreen() {
    Column(Modifier.fillMaxWidth(), verticalArrangement = Arrangement.Center) {
        Text(
            text = "Quick analytics",
            textAlign = TextAlign.Center
        )
    }

}

@Composable
fun BiWeeklyAnalyticsScreen() {
    Column(
        Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.Center
    ) {

        Row(
            Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {

            SuggestionChip(
                onClick = {},
                label = { Text("Mood Graph") },
                colors = SuggestionChipDefaults.suggestionChipColors(MaterialTheme.colorScheme.secondaryContainer)
            )
            SuggestionChip(
                onClick = {},
                label = { Text("Summary") },
                colors = SuggestionChipDefaults.suggestionChipColors(MaterialTheme.colorScheme.secondaryContainer)
            )
        }
        SummaryPage()
    }
}

fun makeCardInfo(): List<BiWeeklyEvalStat> {
    var results = mutableListOf<BiWeeklyEvalStat>()

    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val current = LocalDate.now()

    val currentString = current.format(formatter)

    for (i in 0..6) {

        val date = current.minusDays(i.toLong())

        results.add(
            BiWeeklyEvalStat(
                depressionScore = 5 + i,
                anxietyScore = 2 + i,
                dateCompleted = date
            )
        )
    }

    return results.toList()
}


@Composable
fun SummaryPage() {
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

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Current Week:",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary
        )
        todays.forEach { SummaryCards(it) }

        Text(
            text = "Last Week:",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary
        )
        lastWeek.forEach { SummaryCards(it) }

    }
}

@Composable
fun SummaryCards(results: BiWeeklyEvalStat) {

    val colour = MaterialTheme.colorScheme.primaryContainer

    ElevatedCard(
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "Mood:",
                style = MaterialTheme.typography.titleSmall
            )

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                Text(
                    text = "Moderate Depression",
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center
                )
                Text(
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(20.dp)
                        .drawBehind { drawCircle(
                            color = colour,
                            radius = this.size.maxDimension
                        ) },
                    text = "${results.depressionScore}"
                )

            }

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                Text(
                    text = "Moderate Anxiety"
                )
                FilledTonalButton(onClick = {}, enabled = true, elevation = ButtonDefaults.buttonElevation(3.dp), content = {
                    Text(
                        text = "${results.anxietyScore}",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
                )

            }
        }


    }
}


@Composable
fun ScoreChart(score: Int, scores: List<String>, severities: List<String>) {

    ElevatedCard(
        Modifier.padding(top = 15.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(
                    text = "SCORE",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier
                        .width(100.dp)
                        .padding(end = 40.dp),
                    textAlign = TextAlign.Start
                )

                Text(
                    text = "SEVERITY",
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Right,
                    modifier = Modifier
                        .width(100.dp)
                        .padding(end = 20.dp)
                )
            }

            repeat(4) { i ->
                val scoreRange = scores[i].split('-')

                if (score > scoreRange[0].toInt() && score < scoreRange[1].toInt()) {
                    RowChart(
                        modifier = Modifier.background(MaterialTheme.colorScheme.inversePrimary),
                        scores[i],
                        severities[i]
                    )
                } else {
                    RowChart(modifier = Modifier, scores[i], severities[i])
                }


            }
        }

    }
}


@Composable
fun ScoreColumn(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleMedium,
        textAlign = TextAlign.Center,
        modifier = Modifier.padding(start = 15.dp)
    )

}

@Composable
fun RowChart(modifier: Modifier = Modifier, score: String, severity: String) {
    Row(modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
        Text(
            text = score,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .padding(start = 5.dp)
                .width(50.dp),
            textAlign = TextAlign.Left
        )
        Text(
            text = severity,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier
                .padding(start = 5.dp)
                .width(180.dp),
            textAlign = TextAlign.Left
        )
    }
}
