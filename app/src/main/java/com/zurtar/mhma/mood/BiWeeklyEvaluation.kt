package com.zurtar.mhma.mood

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
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.zurtar.mhma.util.DefaultTopAppBar
import com.zurtar.mhma.R
import com.zurtar.mhma.data.BiWeeklyEvaluationEntry
import java.time.Instant
import java.time.LocalDate
import java.util.Date


@Composable
fun BiWeeklyEvaluationScreen(
    modifier: Modifier = Modifier,
    viewModel: BiWeeklyEvaluationViewModel = hiltViewModel(),
    openDrawer: () -> Unit,
    onNavigateToAnalytics: (Int) -> Unit
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
            biWeeklyEntry = uiState.biWeeklyEntry,
            questionResponse = uiState.questionResponse,
            onSelect = viewModel::onSelect,
            onBack = viewModel::onBack,
            onNext = viewModel::onNext,
            onNavigateToAnalytics = onNavigateToAnalytics
        )
    }
}

@Composable
private fun BiWeeklyEvaluationScreenContent(
    modifier: Modifier = Modifier,
    page: Int,
    biWeeklyEntry: BiWeeklyEvaluationEntry,
    questionResponse: MutableList<Int>,
    onSelect: (Int) -> Unit,
    onBack: () -> Unit,
    onNext: () -> Unit,
    onNavigateToAnalytics: (Int) -> Unit
) {
    val questions: Array<String> = stringArrayResource(R.array.phq_9_questions)

    if (page == questions.size) {

        BiWeeklyResult(
            modifier = modifier,
            evaluation = biWeeklyEntry,
            onNavigateToAnalytics = onNavigateToAnalytics
        )
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
            selectedOption = questionResponse[page],
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
        Spacer(modifier = Modifier.height(20.dp))
        LinearProgressIndicator(
            progress = { page.toFloat() / questions.size },
            modifier = Modifier.fillMaxWidth(.75f),
        )
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
    modifier: Modifier = Modifier,
    evaluation: BiWeeklyEvaluationEntry,
    onNavigateToAnalytics: (Int) -> Unit
) {

    val depressionScores: List<String> = stringArrayResource(R.array.depression_scores).toList()
    val depressionSeverities: List<String> =
        stringArrayResource(R.array.depression_severities).toList()

    val anxietyScores = stringArrayResource(R.array.anxiety_scores).toList()
    val anxietySeverities = stringArrayResource(R.array.anxiety_severities).toList()


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
                    content = { Text("${evaluation.depressionScore}") })

            }
            Spacer(Modifier.width(15.dp))
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Anxiety Score",
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center
                )
                FilledTonalButton(
                    onClick = {},
                    enabled = true,
                    content = { Text("${evaluation.anxietyScore}") })
            }
        }

        ScoreChart(
            score = evaluation.depressionScore,
            scores = depressionScores,
            severities = depressionSeverities
        )
        Spacer(Modifier.height(15.dp))
        Text(
            text = "Your answers to the PHQ-9 align with those with: ${evaluation.depressionResults}",
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.height(15.dp))

        ScoreChart(
            score = evaluation.anxietyScore,
            scores = anxietyScores,
            severities = anxietySeverities
        )
        Spacer(Modifier.height(15.dp))
        Text(
            text = "Your answers to the GAD-7 align with those with: ${evaluation.anxietyResults}",
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.height(30.dp))

        ProceedCard("Proceed to Evaluation Summary") { onNavigateToAnalytics(1) }
        Spacer(Modifier.height(15.dp))
        ProceedCard("Proceed to Evaluation Analytics") { onNavigateToAnalytics(1) }


    }
}

@Composable
fun ProceedCard(text: String, navigate: () -> Unit) {

    OutlinedCard(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { navigate() },
        colors = CardDefaults.cardColors(
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
                onClick = { navigate() },
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


//HELPER FUNCTIONS

/**
 *
 */
@Composable
fun findSeverity(score: Int, evalType: String): String {
    val depressionScores: List<String> = stringArrayResource(R.array.depression_scores).toList()
    val depressionSeverities: List<String> =
        stringArrayResource(R.array.depression_severities).toList()

    val anxietyScores: List<String> = stringArrayResource(R.array.anxiety_scores).toList()
    val anxietySeverities: List<String> = stringArrayResource(R.array.anxiety_severities).toList()

    if (evalType == "anxiety") {
        for (i in anxietyScores.indices) {
            val scoreRange = anxietyScores[i].split('-')

            if (score >= scoreRange[0].toInt() && score <= scoreRange[1].toInt()) {
                return anxietySeverities[i]
            }
        }
    } else {
        for (i in depressionScores.indices) {
            val scoreRange = depressionScores[i].split('-')

            if (score >= scoreRange[0].toInt() && score <= scoreRange[1].toInt()) {
                return depressionSeverities[i]
            }
        }
    }
    return ""
}