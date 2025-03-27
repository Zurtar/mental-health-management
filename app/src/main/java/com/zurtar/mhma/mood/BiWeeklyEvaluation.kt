package com.zurtar.mhma.mood

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Tab
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.zurtar.mhma.R
import com.zurtar.mhma.models.BiWeeklyEvaluationViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class BiWeeklyEval {
    private var completed = false
    private var date = ""
    val completedEvals: MutableMap<String, Array<String>> = mutableMapOf<String, Array<String>>()


    @RequiresApi(Build.VERSION_CODES.O)
    fun setCompleted() {
        val dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val currentDate = LocalDateTime.now().format(dateFormat)
        if (completed && date != currentDate) {
            completed = false
            return
        }
        if (!completed && date == "") {
            completed = true
            return
        }
    }

    fun getCompleted(): Boolean {
        return completed
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun setDate() {
        if (getCompleted()) {
            val dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            date = LocalDateTime.now().format(dateFormat)
        }
    }

    fun getDate(): String {
        return date
    }

    fun addCompletedEval(date: String, responses: Array<String>): Int {
        if (date != "") {
            completedEvals.put(date, responses)
            return 1
        }
        return -1
    }

}

@Composable
fun BiWeeklyEvaluationScreen(
    modifier: Modifier = Modifier, viewModel: BiWeeklyEvaluationViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val questions: Array<String> = stringArrayResource(R.array.phq_9_questions)
    // val gadQuestions = Array<String> = stringArrayResource(R.array.gad_7_questions)

    if (uiState.page == 2) {
        // BiWeeklyResult(modifier = modifier, uiState.depresessionScore, uiState.anxietyScore)
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
            num = uiState.page,
            question = questions[uiState.page],
            selectedOption = uiState.questionResponse[uiState.page],
            onSelect = viewModel::onSelect
        )


        Row(
            Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround
        ) {
            if (uiState.page > 0) FilledTonalButton(onClick = { viewModel.onBack() },
                content = { Text("Back") })

            var text = "Next"
            if (uiState.page == 8) text = "Submit";
            FilledTonalButton(onClick = { viewModel.onNext() }, content = { Text(text) })
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

    var text = ""
    if (depressionScore < 10 || anxietyScore < 10) {
        text = "unlikely"
    } else {
        text = "likely"
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

    var pagerState by remember { mutableStateOf(1) }

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
                .fillMaxWidth()
                .background(Color.LightGray),
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

        Text(
            text = "Today"
        )



    }

}

@Composable
fun SummaryCards() {
    ElevatedCard() {
        
    }
}


fun calcScore(list: List<String>): Int {
    var total = 0

    for (response: String in list) {
        if (response == "Several days") {
            total += 1
        }
        if (response == "More than half the days") {
            total += 2
        }
        if (response == "Nearly everyday") {
            total += 3
        }
    }
    return total
}

@Composable
fun AlertDialogExample(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    dialogText: String,
    icon: Any
) {
    AlertDialog(title = {
        Text(text = dialogTitle)
    }, text = {
        Text(text = dialogText)
    }, onDismissRequest = {
        onDismissRequest()
    }, confirmButton = {
        TextButton(onClick = {
            onConfirmation()
        }) {
            Text("Continue to Analysis")
        }
    }, dismissButton = {
        TextButton(onClick = {
            onDismissRequest()
        }) {
            Text("Dismiss")
        }
    })
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
