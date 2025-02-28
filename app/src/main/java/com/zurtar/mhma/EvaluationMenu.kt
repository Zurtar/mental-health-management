package com.zurtar.mhma

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun EvaluationFormCard(
    modifier: Modifier = Modifier,
    title: String,
    desc: String,
    onNavigate: () -> Unit
) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        modifier = modifier
            .padding(bottom = 15.dp)
            .fillMaxWidth(0.75f)
            .fillMaxHeight(0.2f),
        onClick = onNavigate
    ) {
        Column {
            Text(text = title)
            HorizontalDivider()
            Text(text = desc)
        }
    }
}

@Composable
fun MoodEvaluationScreen(modifier: Modifier = Modifier, onNavigate: (Any) -> Unit) {
    Column(
        modifier = modifier.fillMaxWidth(0.5f),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Spacer(Modifier.height(10.dp))
        EvaluationFormCard(
            title = "Daily Evaluation",
            desc = "Complete a list of simple questions to discover your average mood for the day",
            onNavigate = { onNavigate(DailyEvaluation) }

        )
        EvaluationFormCard(
            title = "Bi-Weekly Evaluation",
            desc = "More in-depth questions",
            onNavigate = { onNavigate(BiWeeklyEvaluation) }
        )
    }
}
