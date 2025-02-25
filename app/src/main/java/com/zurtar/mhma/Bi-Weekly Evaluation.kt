package com.zurtar.mhma

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box

import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class BiWeeklyUiState(
    val questionCount: Int = 0,
    val currQuestion: String = ""

)

public class BiWeeklyEvalViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(BiWeeklyUiState())
    val uiState: StateFlow<BiWeeklyUiState> = _uiState.asStateFlow()

}


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
fun QuestionCard(num: Int, question: String) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        modifier = Modifier.size(width = 360.dp, height = 100.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Question ${num}",
                modifier = Modifier.padding(start = 10.dp, bottom = 5.dp),
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = question,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                style = MaterialTheme.typography.bodyMedium
            )
        }

    }
}


@Composable
fun ResponseCard(radioOptions: List<String>) {
    //val radioOptions = listOf("Not at all", "Several days", "More than half the days", "Nearly every day")
    val (selectedOption, onOptionSelected) = remember { mutableStateOf(radioOptions[0]) }


    ElevatedCard(
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        modifier = Modifier.size(width = 360.dp, height = 100.dp)
    ) {

        Row(
            Modifier
                .selectableGroup()
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth()
                .padding(start = 10.dp)
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
}


@Composable
fun WeeklyQuestionScreenLayout(modifier: Modifier = Modifier) {

    val questions: Array<String> = stringArrayResource(R.array.phq_9_questions)
    var questionCount by remember { mutableStateOf(0) }
    var text by rememberSaveable { mutableStateOf(questions[questionCount]) }

    var buttonText = "Next"

    val radioOptions =
        listOf("Not at all", "Several days", "More than half the days", "Nearly every day")


    Column(modifier = modifier.background(MaterialTheme.colorScheme.background), horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "Over the last 2 weeks, have you felt...",
            modifier = Modifier.align(Alignment.Start).padding(start = 5.dp, top = 15.dp),
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(Modifier.size(16.dp))
        QuestionCard(questionCount + 1, text)
        Spacer(Modifier.size(5.dp))
        ResponseCard(radioOptions)
        Spacer(Modifier.size(16.dp))
        Row(Modifier.fillMaxWidth()) {
            FilledTonalButton(onClick = {

                if (questionCount - 1 < questions.size) {
                    questionCount--
                    text = questions[questionCount]
                }
            }
            ) {
                Text("Back")
            }
            Spacer(Modifier.width(230.dp))
            FilledTonalButton(onClick = {

                if (questionCount + 1 < questions.size) {
                    questionCount++
                    text = questions[questionCount]
                }
                if (questionCount == questions.size) {
                    buttonText = "Finish"
                }

            }
            ) {
                Text(buttonText)
            }
        }

    }

}


@Composable
fun EndEvalDialog(){
    val response = listOf("Nearly everyday", "Several days", "More than half the days", "Not at all")

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

fun calcScore(list:List<String>):Int {
    var total = 0

    for(response:String in list) {
        if(response == "Several days"){
            total +=1
        }
        if(response == "More than half the days") {
            total +=2
        }
        if(response == "Nearly everyday") {
            total += 3
        }
    }
    return total
}

@Composable
fun AlertDialogExample(onDismissRequest: () -> Unit, onConfirmation: () -> Unit, dialogTitle: String, dialogText: String, icon: Any) {
    AlertDialog(
        title = {
            Text(text = dialogTitle)
        },
        text = {
            Text(text = dialogText)
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmation()
                }
            ) {
                Text("Continue to Analysis")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text("Dismiss")
            }
        }
    )
}

@Preview
@Composable
fun BiWeeklyIntroScreen() {

    Column(modifier = Modifier.fillMaxWidth()
        .background(MaterialTheme.colorScheme.background), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Bi-Weekly Evaluation:",
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(Modifier.size(16.dp))
        Text(
            text = "The set of questions will help to evaluate your mood over the last 2 weeks based on your responses. " +
                    "The questions used are taken from the PHQ-9, which helps ",
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.size(16.dp))
        ScoreChart()

    }
}

@Composable
fun ScoreChart() {

    ElevatedCard( elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)) {
        Column( horizontalAlignment = Alignment.CenterHorizontally) {

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
fun RowChart(score:String, severity:String){
    Row() {
        Text(
            text = score,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(start = 5.dp)
        )
        Text(
            text =  severity,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(start = 5.dp)
        )
    }
}

//
//@Preview
//@Composable
//fun BiWeeklyGraph(){
//
//    val xValues = listOf(1, 3, 5, 7, 9, 11, 13, 15, 17, 19, 21)
//    val yValues = listOf(5, 7, 2, 5, 10, 13, 12, 10, 15, 20, 25)
//
//
//    Box(
//        modifier = Modifier
//            .background(Color.White)
//            .padding(horizontal = 8.dp, vertical = 12.dp),
//        contentAlignment = Center
//    ) {
//        Canvas(
//            modifier = Modifier.fillMaxSize(),
//        ) {
//            val xAxisSpace = size.width  / xValues.size
//            val yAxisSpace = size.height / yValues.size
//
//            /** placing x axis points */
//            for (i in xValues.indices) {
//                drawContext.canvas.nativeCanvas.drawText(
//                    "${xValues[i]}",
//                    xAxisSpace * (i + 1),
//                    size.height - 30,
//                    textPaint =
//                )
//            }
//
//        }
//    }
//}


@Composable
fun NextButton(clicks: Int, onClick: () -> Unit) {
    Button(onClick = onClick) {
        Text("I've been clicked $clicks times")
    }
}