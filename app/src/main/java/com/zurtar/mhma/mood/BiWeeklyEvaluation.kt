package com.zurtar.mhma.mood

import android.util.Log
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
    openDrawer: () -> Unit,
    onNavigateToSummaryDialog:() -> Unit
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
            depressionScore = uiState.depressionScore,
            anxietyScore = uiState.anxietyScore,
            questionResponses = uiState.questionResponse,
            onSelect = viewModel::onSelect,
            onBack = viewModel::onBack,
            onNext = viewModel::onNext,
            onNavigateToSummaryDialog = onNavigateToSummaryDialog
        )
    }
}

@Composable
private fun BiWeeklyEvaluationScreenContent(
    modifier: Modifier = Modifier,
    page: Int,
    depressionScore: Int,
    anxietyScore: Int,
    questionResponses: List<Int>,
    onSelect: (Int) -> Unit,
    onBack: () -> Unit,
    onNext: () -> Unit,
    onNavigateToSummaryDialog:() -> Unit
) {
    val questions: Array<String> = stringArrayResource(R.array.phq_9_questions)

    if (page == questions.size) {
//        BiWeeklyResult(
//            modifier = modifier,
//            depressionScore = depressionScore,
//            anxietyScore = anxietyScore
//        )
        AnalyticsTab(onNavigateToSummaryDialog)
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

    val depressionScores: List<String> = stringArrayResource(R.array.depression_scores).toList()
    val depressionSeverities: List<String> =
        stringArrayResource(R.array.depression_severities).toList()

    val anxietyScores = R.array.anxiety_scores
    val anxietySeverities = R.array.anxiety_severities


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
                    text = "Depression Score",
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center
                )
                FilledTonalButton(
                    onClick = {},
                    enabled = true,
                    content = { Text("$depressionScore") })

            }
            Spacer(Modifier.width(15.dp))
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Anxiety Score",
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center
                )
                FilledTonalButton(onClick = {}, enabled = true, content = { Text("$anxietyScore") })
            }
        }
        ScoreChart(
            score = depressionScore,
            scores = depressionScores,
            severities = depressionSeverities
        )

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


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnalyticsTab(onNavigateToSummaryDialog:() -> Unit) {

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

                QuickAnalyticsScreen()
            }

            1 -> {

                BiWeeklyAnalyticsScreen(onNavigateToSummaryDialog)
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
fun BiWeeklyAnalyticsScreen(onNavigateToSummaryDialog:() -> Unit) {
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
        SummaryPage(onNavigateToSummaryDialog)
    }
}

fun makeCardInfo(): List<BiWeeklyEvalStat> {
    val results = mutableListOf<BiWeeklyEvalStat>()

    val current = LocalDate.now()
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
fun SummaryPage(onNavigateToSummaryDialog:() -> Unit) {
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
        //SummaryPopupPreview()

        WeekTitles("Current Week")
        todays.forEach { SummaryCards(it, onNavigateToSummaryDialog) }

        WeekTitles("Last Week")
        lastWeek.forEach { SummaryCards(it,onNavigateToSummaryDialog) }

        WeekTitles("Previous Weeks")
        other.forEach { SummaryCards(it,onNavigateToSummaryDialog) }
    }
}

@Composable
fun WeekTitles(title: String) {

    Text(
        text = title,
        style = MaterialTheme.typography.titleMedium,
        color = MaterialTheme.colorScheme.primary
    )

}

@Composable
fun SummaryCards(results: BiWeeklyEvalStat, onNavigateToSummaryDialog:() -> Unit) {

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
                    text = "Moderate Depression",
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
                    text = "Moderate Anxiety",
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

@Composable
fun ScoreChart(score: Int, scores: List<String>, severities: List<String>) {

    ElevatedCard(
        Modifier.padding(top = 15.dp),

        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
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

        repeat(scores.size) { i ->
            val scoreRange = scores[i].split('-')

            if (score >= scoreRange[0].toInt() && score <= scoreRange[1].toInt()) {
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

