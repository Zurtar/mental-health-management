package com.zurtar.mhma.analytics

import android.util.Log
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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.zurtar.mhma.data.DailyEvaluationEntry
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Date


@Composable
fun DailyAnalyticsScreenContent(
    modifier: Modifier = Modifier,
    viewModel: DailyAnalyticsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    Log.println(Log.DEBUG, "DailyAnalytics", uiState.pastEvaluations.toString())

    val labelToContent: Map<String, @Composable () -> Unit> = mapOf(
        "Mood Calendar" to {
            DailyEvaluationCalendar(
                evaluations = uiState.pastEvaluations,
                selectedDate = uiState.selectedDate,
                onDateSelect = { d: LocalDate? -> viewModel.selectDate(d) })
        },
        "History" to {
            DailyHistoricalAnalytics(
                dailyEvaluations = uiState.pastEvaluations
            )
        } // call DailyHistoricalAnalytics here
    )

    TabbedContent(labelToContent = labelToContent, key = labelToContent.keys.first())
}

//
@Composable
fun DailyAnalyticCard(dailyEntry: DailyEvaluationEntry) {

    val emotionsList = dailyEntry.emotionsMap.toList()
    val stress = stressLevel(dailyEntry.stressLevel)
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    ElevatedCard(
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
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
                    text = "${dailyEntry.strongestEmotion.second}",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Text(
                modifier = Modifier.padding(start = 25.dp),
                text = dailyEntry.strongestEmotion.first,
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center
            )

            Text(
                modifier = Modifier.padding(start = 180.dp, end = 5.dp),
                text = dailyEntry.dateCompleted?.toLocalDate()?.format(formatter) ?: "null",
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Start,
                fontSize = 15.sp
            )

        }
        Text(
            modifier = Modifier.padding(start = 40.dp, bottom = 10.dp),
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
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val current = LocalDate.now()
    val currentString = current.format(formatter)
    val yesterday = current.minusDays(1).format(formatter)


    val dailyEvaluations_ = dailyEvaluations.sortedByDescending { ele ->
        ele.dateCompleted?.toLocalDate() ?: LocalDate.MIN
    }

    val today = dailyEvaluations_.filter {
        (it.dateCompleted?.toLocalDate() ?: LocalDate.MIN).format(formatter) == currentString
    }

    val lastWeek = dailyEvaluations_.filter {
        (it.dateCompleted?.toLocalDate() ?: LocalDate.MIN).format(formatter) == yesterday
    }

    val other = dailyEvaluations_.filter {
        (it.dateCompleted?.toLocalDate() ?: LocalDate.MAX) < current.minusDays(2)
    }

    val state = rememberScrollState()
    Column(modifier = Modifier.verticalScroll(state)) {
        //SummaryPopupPreview()

        WeekTitles("Today")
        if (!today.isNullOrEmpty())
            DailyAnalyticCard(today.first())

        WeekTitles("Yesterday")
        lastWeek.forEach { DailyAnalyticCard(it) }

        WeekTitles("Previous Days")
        other.forEach { DailyAnalyticCard(it) }
    }
}


@Composable
fun DailyEvaluationCalendar(
    modifier: Modifier = Modifier,
    evaluations: List<DailyEvaluationEntry>,
    selectedDate: LocalDate?,
    onDateSelect: (LocalDate?) -> Unit
) {

    val selectedEntry = evaluations.find { it.dateCompleted?.toLocalDate() == selectedDate }
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        AppHorizontalCalendar(
            evaluations = evaluations,
            selectedDate = selectedDate,
            onDateSelect = onDateSelect
        )
        if (selectedEntry != null) {
            Text(
                modifier = Modifier.padding(start = 10.dp),
                text = "Selected Date:",
                style = MaterialTheme.typography.headlineSmall
            )
            DailyAnalyticCard(selectedEntry)
        } else {
            Text(
                modifier = Modifier.padding(start = 10.dp),
                text = "Selected Date: No Entries",
                style = MaterialTheme.typography.headlineSmall
            )
        }

    }
}


@Composable
fun makeCardInfoDaily(): List<DailyEvaluationEntry> {
    val results = mutableListOf<DailyEvaluationEntry>()

    val map = mapOf(
        "Happy" to 7f,
        "Sad" to 3f
    )

    val current = LocalDate.now()
    for (i in 0..8) {

        val localDate = current.minusDays(i.toLong())

        results.add(
            DailyEvaluationEntry(
                emotionsMap = map,
                stressLevel = "Very Stressed",
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