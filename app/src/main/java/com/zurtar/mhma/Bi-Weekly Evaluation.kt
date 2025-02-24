package com.zurtar.mhma

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement

import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
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

@Preview
@Composable
fun WeeklyQuestionScreenLayout(modifier: Modifier = Modifier) {

    val questions: Array<String> = stringArrayResource(R.array.phq_9_questions)
    var questionCount by remember { mutableStateOf(0) }
    var text by rememberSaveable { mutableStateOf(questions[questionCount]) }

    var buttonText = "Next"

    val radioOptions =
        listOf("Not at all", "Several days", "More than half the days", "Nearly every day")


    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        QuestionCard(questionCount + 1, text)
        Spacer(Modifier.size(16.dp))
        ResponseCard(radioOptions)

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


fun NextOnClick(count: Int, text: String) {


}


@Composable
fun NextButton(clicks: Int, onClick: () -> Unit) {
    Button(onClick = onClick) {
        Text("I've been clicked $clicks times")
    }
}