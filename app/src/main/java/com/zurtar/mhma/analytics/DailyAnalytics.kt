package com.zurtar.mhma.analytics

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zurtar.mhma.mood.DailyEvaluationEntry
import java.time.LocalDate


@Composable
fun DailyAnalyticsScreenContent() {
    val labelToContent: Map<String, @Composable () -> Unit> = mapOf(
        "Mood Calendar" to { DailyEvaluationCalendar() },
        "History" to { }
    )
    TabbedContent(labelToContent = labelToContent, key = labelToContent.keys.first())
}

@Preview
@Composable
fun DailyHistory() {
    val dailyEntry: DailyEvaluationEntry = DailyEvaluationEntry(
        emotionsMap = mapOf("Happy" to 7f, "Sad" to 4f, "Angry" to 2.5f),
        currentEmotion = "Mildly Stressed",
        dateCompleted = LocalDate.now().toDate()
    )

    ElevatedCard() {
        Row() {

        }
    }
}


@Composable
fun DailyEvaluationCalendar(
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        AppHorizontalCalendar()

        Text(
            modifier = Modifier.padding(start = 10.dp),
            text = "Selected Date:",
            style = MaterialTheme.typography.headlineSmall
        )
    }
}
