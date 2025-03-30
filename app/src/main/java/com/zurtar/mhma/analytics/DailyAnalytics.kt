package com.zurtar.mhma.analytics

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zurtar.mhma.data.BiWeeklyEvaluationEntry
import com.zurtar.mhma.mood.DailyEvaluationEntry
import com.zurtar.mhma.mood.findSeverity
import java.time.LocalDate
import java.time.format.DateTimeFormatter


@Composable
fun DailyAnalyticsScreenContent() {
    val labelToContent: Map<String, @Composable () -> Unit> = mapOf(
        "Mood Calendar" to { DailyEvaluationCalendar() },
        "History" to { DailyHistory() }
    )
    TabbedContent(labelToContent = labelToContent, key = labelToContent.keys.first())
}

@Preview
@Composable
fun DailyHistory() {
    val dailyEntry: DailyEvaluationEntry = DailyEvaluationEntry(
        emotionsMap = mapOf("Happy" to 7f, "Sad" to 4f, "Angry" to 2.5f),
        stressLevel = "Mildly Stressed",
        dateCompleted = LocalDate.now().toDate()
    )

    val results = makeCardInfoDaily()
    DailyHistoricalAnalytics(results)
}

@Composable
fun SummaryCard(dailyEntry: DailyEvaluationEntry) {

    val emotionsList = dailyEntry.emotionsMap.toList()
    val stress = stressLevel(dailyEntry.stressLevel)
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    val emotions = listOf(
        "Sad" to Color(0xFF1E88E5), // Blue
        "Happy" to Color(0xFF4CAF50), // Yellow
        "Fearful" to Color(0xFF8E24AA), // Purple
        "Angry" to Color(0xBAD32F2F), // Red

    )

    ElevatedCard(elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .clickable { }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            ElevatedButton(
                onClick = {},
                modifier = Modifier
                    .width(34.dp)
                    .align(Alignment.CenterVertically),
                contentPadding = PaddingValues(0.dp)
            ) {
                Text(
                    text = "${emotionsList[0].second}",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Text(
                modifier = Modifier.padding(start = 15.dp),
                text = emotionsList[0].first,
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center
            )

            Text(
                modifier = Modifier.padding(start = 180.dp),
                text = dailyEntry.dateCompleted?.toLocalDate()?.format(formatter) ?: "null",
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Start
            )

        }
        Text(
            modifier = Modifier.padding(start = 40.dp),
            text = "Stress Level: ${stress}",
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center
        )

    }
}

@Composable
fun DailyHistoricalAnalytics(
    dailyEvaluations: List<DailyEvaluationEntry>
) {

    // Generate results....
    val dailyEvaluations_ = dailyEvaluations.map { x ->
        x.copy(
        )
    }

    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val current = LocalDate.now()
    val currentString = current.format(formatter)
    val yesterday = current.minusDays(1).format(formatter)

    val todays = dailyEvaluations.filter {
        (it.dateCompleted?.toLocalDate() ?: LocalDate.MIN).format(formatter) == currentString
    }

    val lastWeek = dailyEvaluations.filter {
        (it.dateCompleted?.toLocalDate() ?: LocalDate.MIN).format(formatter) == yesterday
    }

    val other = dailyEvaluations.filter {
        (it.dateCompleted?.toLocalDate() ?: LocalDate.MIN) < current.minusDays(2)
    }

    val state = rememberScrollState()
    Column(modifier = Modifier.verticalScroll(state)) {
        //SummaryPopupPreview()

        WeekTitles("Current Week")
        todays.forEach { SummaryCard(it) }

        WeekTitles("Last Week")
        lastWeek.forEach { SummaryCard(it) }

        WeekTitles("Previous Weeks")
        other.forEach { SummaryCard(it) }
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


@Composable
fun makeCardInfoDaily(): List<DailyEvaluationEntry> {
    val results = mutableListOf<DailyEvaluationEntry>()

    val current = LocalDate.now()
    for (i in 0..8) {

        val localDate = current.minusDays(i.toLong())

        results.add(
            DailyEvaluationEntry(
                dateCompleted = localDate.toDate()
            )
        )
    }
    return results.toList()
}


fun stressLevel(currentStress: String): String {
    var level = ""

    if (currentStress == "Very Stressed") {
        level = "High"
    } else if (currentStress == "Mildly Stressed") {
        level = "Low"
    } else {
        level = "None"
    }

    return level
}