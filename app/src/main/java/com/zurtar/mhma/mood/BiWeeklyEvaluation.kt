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
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.zurtar.mhma.R
import com.zurtar.mhma.data.models.BiWeeklyEvaluationEntry
import com.zurtar.mhma.util.MoodEvaluationTopAppBar

/**
 * Composable that displays the BiWeekly Evaluation screen with a series of questions about mental health over the past two weeks.
 * The user can navigate through the questions and submit the responses at the end.
 *
 * @param modifier Modifier for customizations to the layout.
 * @param viewModel The view model for the bi-weekly evaluation.
 * @param openDrawer A function to open the app's side drawer.
 * @param onNavigateToAnalytics A function to navigate to the analytics screen.
 */
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
            MoodEvaluationTopAppBar(openDrawer = openDrawer)
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

/**
 * Composable that displays the BiWeekly Evaluation screen with a series of questions about mental health over the past two weeks.
 * The user can navigate through the questions and submit the responses at the end.
 *
 * @param modifier Modifier for customizations to the layout.
 * @param viewModel The view model for the bi-weekly evaluation.
 * @param openDrawer A function to open the app's side drawer.
 * @param onNavigateToAnalytics A function to navigate to the analytics screen.
 */
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
                .padding(start = 15.dp, top = 25.dp, bottom = 15.dp),
            style = MaterialTheme.typography.titleSmall,
            textAlign = TextAlign.Center,
            fontSize = 20.sp
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
                    modifier = Modifier
                        .width(120.dp)
                        .height(50.dp)
                        .padding(top = 10.dp),
                    onClick = { onBack() },
                    content = {
                        Text(
                            text = "Back",
                            fontSize = 18.sp
                        )
                    })

            var text = "Next"
            if (page == questions.size - 1) text = "Submit"
            FilledTonalButton(modifier = Modifier
                .width(120.dp)
                .height(50.dp)
                .padding(top = 10.dp),
                onClick = { onNext() },
                content = {
                    Text(
                        text = text,
                        fontSize = 18.sp
                    )
                })
        }
        Spacer(modifier = Modifier.height(40.dp))

        LinearProgressIndicator(
            progress = { page.toFloat() / questions.size },
            modifier = Modifier.fillMaxWidth(.75f),
        )
    }
}

/**
 * Composable that displays a question card for a specific question, allowing the user to select an answer.
 *
 * @param modifier Modifier for customizations to the layout.
 * @param num The index of the current question.
 * @param question The text of the question.
 * @param selectedOption The current selected option for the question.
 * @param onSelect Function to handle selecting an answer.
 */
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

    var questionNum = "Question ${num + 1}"

    if (num <= 8) {
        questionNum = "Question ${num + 1}: Depression"
    }
    if (num > 8) {
        questionNum = "Question ${num + 1}: Anxiety"
    }


    ElevatedCard(
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        modifier = modifier
            .padding(bottom = 15.dp, top = 15.dp)
            .fillMaxWidth(0.85f)
    ) {
        Column(
            modifier = Modifier.padding(all = 15.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier.padding(bottom = 20.dp),
                text = question,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge,
                fontSize = 18.sp
            )
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
                    text = questionNum
                )
            }
        }
    }
}


/**
 * Composable function to display a list of radio button options for a question.
 *
 * displays a list of radio options and allows the user to select one.
 * selected option is visually highlighted, and the `onSelect` lambda is called
 * whenever an option is clicked.
 *
 * @param radioOptions A list of strings representing the options available to the user.
 * @param selectedOption The index of the currently selected option. This will determine
 *                       which option is visually highlighted.
 * @param onSelect A lambda function to handle the selection of an option. It takes the
 *                 index of the selected option as a parameter.
 */
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
                    text = text,
                    style = MaterialTheme.typography.bodyLarge,
                    fontSize = 18.sp
                )
            }
        }
    }
}

/**
 * Composable function that displays the result of a bi-weekly evaluation.
 *
 * This function shows the user's depression and anxiety scores along with their severity
 * results based on the PHQ-9 and GAD-7 questionnaires. It also provides options to proceed
 * to evaluation summary or analytics.
 *
 * @param modifier The modifier to be applied to the outer Column.
 * @param evaluation The evaluation result containing the user's depression and anxiety scores
 *                   along with the severity results.
 * @param onNavigateToAnalytics A lambda function to handle navigation when the user proceeds
 *                              to the evaluation summary or analytics.
 */
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
            modifier = Modifier.padding(top = 20.dp, bottom = 10.dp),
            text = "You have completed the Bi-Weekly evaluation!",
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(25.dp))

        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Depression Score",
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center
                )
                FilledTonalButton(
                    modifier = Modifier
                        .width(80.dp)
                        .height(60.dp)
                        .padding(top = 10.dp),
                    onClick = {},
                    enabled = true,
                    content = {
                        Text(
                            text = "${evaluation.depressionScore}",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    })

            }
            Spacer(Modifier.width(25.dp))
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Anxiety Score",
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center
                )
                FilledTonalButton(
                    modifier = Modifier
                        .width(80.dp)
                        .height(60.dp)
                        .padding(top = 10.dp),
                    onClick = {},
                    enabled = true,
                    content = {
                        Text(
                            text = "${evaluation.anxietyScore}",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    })
            }
        }

        Spacer(Modifier.height(25.dp))

        Text(
            text = "Your answers to the PHQ-9 align with those with: ${evaluation.depressionResults}",
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            fontSize = 17.sp
        )

        ScoreChart(
            score = evaluation.depressionScore,
            scores = depressionScores,
            severities = depressionSeverities
        )

        Spacer(Modifier.height(25.dp))

        Text(
            text = "Your answers to the GAD-7 align with those with: ${evaluation.anxietyResults}",
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            fontSize = 17.sp
        )
        //Spacer(Modifier.height(.dp))

        ScoreChart(
            score = evaluation.anxietyScore,
            scores = anxietyScores,
            severities = anxietySeverities
        )
        Spacer(Modifier.height(30.dp))
        HorizontalDivider()
        ProceedCard("Proceed to Evaluation Summary") { onNavigateToAnalytics(1) }
        HorizontalDivider()
        ProceedCard("Proceed to Evaluation Analytics") { onNavigateToAnalytics(1) }
        HorizontalDivider()


    }
}

/**
 * Composable function that displays a clickable card to navigate to another screen.
 *
 * This function creates a card with a text label and an icon button. When clicked, it triggers
 * the provided `navigate` lambda to navigate to a different screen.
 *
 * @param text The text to be displayed inside the card.
 * @param navigate A lambda function to handle the navigation when the card is clicked.
 */
@Composable
fun ProceedCard(text: String, navigate: () -> Unit) {

    OutlinedCard(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { navigate() },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        border = BorderStroke(1.dp, Color.Transparent)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.titleMedium
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

/**
 * Composable function that displays a score chart to show the user's score and severity.
 *
 * This function shows the score and severity levels for both depression and anxiety based
 * on the provided score. The score is compared to predefined ranges to determine the severity
 * level.
 *
 * @param score The score value to be displayed.
 * @param scores A list of string values representing the different score ranges.
 * @param severities A list of string values representing the corresponding severity levels
 *                   for each score range.
 */
@Composable
fun ScoreChart(score: Int, scores: List<String>, severities: List<String>) {

    ElevatedCard(
        Modifier.padding(top = 15.dp, start = 10.dp, end = 10.dp),

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


/**
 * Composable function that displays a row with a score and its corresponding severity.
 *
 * This function creates a horizontal row containing the score and its corresponding severity.
 * It is used within the `ScoreChart` to display individual score ranges.
 *
 * @param modifier The modifier to be applied to the Row.
 * @param score The score value to be displayed.
 * @param severity The severity level corresponding to the score.
 */
@Composable
fun RowChart(modifier: Modifier = Modifier, score: String, severity: String) {
    Row(modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
        Text(
            text = score,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .padding(start = 5.dp)
                .width(50.dp),
            textAlign = TextAlign.Left,
            fontSize = 15.sp
        )
        Text(
            text = severity,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier
                .padding(start = 5.dp)
                .width(180.dp),
            textAlign = TextAlign.Left,
            fontSize = 15.sp
        )
    }
}

/**
 * Finds the severity level based on the given score and evaluation type (anxiety or depression).
 *
 * This function checks the provided score against predefined ranges for either anxiety or
 * depression, depending on the evaluation type, and returns the corresponding severity level.
 *
 * @param score The score value to be checked.
 * @param evalType The type of evaluation, either "anxiety" or "depression".
 * @return The severity level corresponding to the given score and evaluation type.
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
            if (score == 0) {
                return "No anxiety score"
            }
        }
    } else {
        for (i in depressionScores.indices) {
            val scoreRange = depressionScores[i].split('-')

            if (score >= scoreRange[0].toInt() && score <= scoreRange[1].toInt()) {
                return depressionSeverities[i]
            }

            if (score == 0) {
                return "No depression score"
            }
        }
    }
    return ""
}