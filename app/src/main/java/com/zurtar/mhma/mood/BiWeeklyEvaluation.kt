package com.zurtar.mhma.mood

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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.zurtar.mhma.DefaultTopAppBar
import com.zurtar.mhma.R
import com.zurtar.mhma.models.BiWeeklyEvaluationViewModel


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
            score = uiState.score,
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

    if (page == 9) {
        BiWeeklyResult(modifier = modifier, score)
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
            if (page == 8)
                text = "Submit"
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
                        if (index == selectedOption)
                            MaterialTheme.colorScheme.inversePrimary
                        else
                            MaterialTheme.colorScheme.onSecondary
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
    score: Int
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ScoreChart()
        Spacer(Modifier.height(15.dp))
        Text("Your Score: $score")
    }
}


/*@Composable
fun BiWeeklyEvaluationScreen(modifier: Modifier) {

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Bi-Weekly Evaluation:", style = MaterialTheme.typography.titleMedium
        )
        Spacer(Modifier.size(16.dp))
        Text(
            text = "The set of questions will help to evaluate your mood over the last 2 weeks based on your responses. " + "The questions used are taken from the PHQ-9, which helps ",
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.size(16.dp))
        ScoreChart()
    }
}*/

@Composable
fun ScoreChart() {
    ElevatedCard(
        Modifier.padding(top = 15.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            RowChart(score = "Score", severity = "Severity")
            RowChart(score = "1-4", severity = "Minimal depression")
            RowChart(score = "5-9", severity = "Mild depression")
            RowChart(score = "10-14", severity = "Moderate depression")
            RowChart(score = "15-19", severity = "Moderately severe depression")
            RowChart(score = "20-27", severity = "Severe depression")
        }
    }
}

@Composable
fun RowChart(modifier: Modifier = Modifier, score: String, severity: String) {
    Row(modifier = modifier) {
        Text(
            text = score,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(start = 5.dp)
        )
        Text(
            text = severity,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(start = 5.dp)
        )
    }
}
