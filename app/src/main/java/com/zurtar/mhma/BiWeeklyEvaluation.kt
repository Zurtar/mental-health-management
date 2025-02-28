package com.zurtar.mhma

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.zurtar.mhma.models.BiWeeklyEvaluationViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class User {
    var weeklyEval = BiWeeklyEval()

}

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
fun AltQuestionCard(modifier: Modifier = Modifier, num: Int, question: String) {
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
            AltQuestionResponse(radioOptions)
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
                    text = "Question ${num+1}"
                )
            }
        }
    }
}

@Composable
fun AltQuestionResponse(radioOptions: List<String>) {
    val (selectedOption, onOptionSelected) = remember { mutableStateOf(radioOptions[0]) }
    Column(
        modifier = Modifier
            .selectableGroup()
            .fillMaxWidth()
            .padding(start = 10.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        radioOptions.forEach { text ->
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
                    .clickable { onOptionSelected(text) }
                    .background(
                        if (text == selectedOption)
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
//            }
            /*  Column(
              Modifier
                  .padding(start = 5.dp, top = 15.dp)
                  .selectable(
                      selected = (text == selectedOption),
                      onClick = { onOptionSelected(text) },
                      role = Role.RadioButton
                  )
          ) {
              RadioButton(
                  selected = (text == selectedOption),
                  onClick = null,
                  modifier = Modifier.align(Alignment.CenterHorizontally)
              )
              Text(
                  text = text,
                  style = MaterialTheme.typography.bodyLarge,
                  fontSize = 11.sp,
                  textAlign = TextAlign.Center,
                  //modifier = Modifier.padding(start = 10.dp)
              )

          }*/
        }
    }
}

@Composable
fun QuestionCard(modifier: Modifier = Modifier, num: Int, question: String) {
    val radioOptions =
        listOf("Not at all", "Several days", "More than half the days", "Nearly every day")

    ElevatedCard(
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        modifier = modifier
            .padding(bottom = 15.dp)
            .fillMaxWidth(0.85f)
            .fillMaxHeight(0.2f)
    ) {
        Column(modifier) {
            Text(text = "Question ${num + 1}: " + question)
            HorizontalDivider()
            QuestionResponse(radioOptions)
        }
    }
}


@Composable
fun QuestionResponse(radioOptions: List<String>) {
    val (selectedOption, onOptionSelected) = remember { mutableStateOf(radioOptions[0]) }
    Row(
        modifier = Modifier
            .selectableGroup()
            .fillMaxHeight()
            .fillMaxWidth()
            .padding(start = 10.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        radioOptions.forEach { text ->
            Column(
                Modifier
                    .padding(start = 5.dp, top = 15.dp)
                    .selectable(
                        selected = (text == selectedOption),
                        onClick = { onOptionSelected(text) },
                        role = Role.RadioButton
                    )
            ) {
                RadioButton(
                    selected = (text == selectedOption),
                    onClick = null,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                Text(
                    text = text,
                    style = MaterialTheme.typography.bodyLarge,
                    fontSize = 11.sp,
                    textAlign = TextAlign.Center,
                    //modifier = Modifier.padding(start = 10.dp)
                )

            }
        }
    }
}


@Composable
fun BiWeeklyEvaluationScreen(
    modifier: Modifier = Modifier,
    viewModel: BiWeeklyEvaluationViewModel = viewModel()
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val questions: Array<String> = stringArrayResource(R.array.phq_9_questions)

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
        AltQuestionCard(num = uiState.page, question = questions[uiState.page])
        /*   repeat(times = 3) { i ->
               Spacer(Modifier.size(16.dp))
               QuestionCard(num = uiState.page * 3 + i, question = questions[uiState.page * 3 + i])
           }*/
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            var text = "Next"
            if (uiState.page > 0)
                FilledTonalButton(
                    onClick = { viewModel.onBack() },
                    content = { Text("Back") })
            if (uiState.page == 8)
                text = "Submit";
            FilledTonalButton(onClick = { viewModel.onNext() }, content = { Text(text) })
        }
    }
}


@Composable
fun EndEvalDialog() {
    val response =
        listOf("Nearly everyday", "Several days", "More than half the days", "Not at all")

    val openAlertDialog = remember { mutableStateOf(false) }

    when {

        openAlertDialog.value -> {
            AlertDialogExample(
                onDismissRequest = { openAlertDialog.value = false },
                onConfirmation = {
                    openAlertDialog.value = false
                    println("Confirmation registered") // Add logic here to handle confirmation.
                },
                dialogTitle = "Evaluation Completed",
                dialogText = "Your Score:\n     ${calcScore(response)}",
                icon = Icons.Default.Info
            )
        }
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
fun ScoreChart() {

    ElevatedCard(elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            RowChart("Score", "Severity")
            RowChart("1-4", "Minimal depression")
            RowChart("5-9", "Mild depression")
            RowChart("10-14", "Moderate depression")
            RowChart("15-19", "Moderately severe depression")
            RowChart("20-27", "Severe depression")
        }
    }


}

@Composable
fun RowChart(score: String, severity: String) {
    Row() {
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

@Composable
fun NextButton(clicks: Int, onClick: () -> Unit) {
    Button(onClick = onClick) {
        Text("I've been clicked $clicks times")
    }
}