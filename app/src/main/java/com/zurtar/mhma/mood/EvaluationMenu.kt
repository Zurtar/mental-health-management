package com.zurtar.mhma.mood

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.zurtar.mhma.util.DefaultTopAppBar
import com.zurtar.mhma.util.MoodEvaluationTopAppBar

/**
 * Composable that displays the main Mood Evaluation screen with a top app bar and navigation options.
 *
 * @param modifier Modifier for customizations to the layout.
 * @param viewModel The ViewModel to provide the UI state for mood evaluations.
 * @param openDrawer A function to open the navigation drawer.
 * @param onNavigateToDaily A function to navigate to the daily evaluation screen.
 * @param onNavigateToBiWeekly A function to navigate to the bi-weekly evaluation screen.
 */
@Composable
fun MoodEvaluationScreen(
    modifier: Modifier = Modifier,
    viewModel: EvaluationMenuViewModel = hiltViewModel(),
    openDrawer: () -> Unit,
    onNavigateToDaily: () -> Unit,
    onNavigateToBiWeekly: () -> Unit
) {
    Scaffold(modifier = modifier.fillMaxSize(),
        topBar = {
            MoodEvaluationTopAppBar(openDrawer = openDrawer)
        }
    ) { innerPadding ->
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()

        MoodEvaluationScreenContent(
            modifier = modifier
                .padding(innerPadding)
                .fillMaxSize(),
            dailyCompleted = uiState.isDailyEntry,
            biWeeklyCompleted = uiState.isBiWeeklyCompleted,
            onNavigateToDaily = onNavigateToDaily,
            onNavigateToBiWeekly = onNavigateToBiWeekly
        )
    }
}


/**
 * Composable that contains the content of the Mood Evaluation screen, including evaluation options.
 *
 * @param modifier Modifier for customizations to the layout.
 * @param dailyCompleted Boolean flag to indicate if the daily evaluation is completed.
 * @param biWeeklyCompleted Boolean flag to indicate if the bi-weekly evaluation is completed.
 * @param onNavigateToDaily A function to navigate to the daily evaluation screen.
 * @param onNavigateToBiWeekly A function to navigate to the bi-weekly evaluation screen.
 */
@Composable
private fun MoodEvaluationScreenContent(
    modifier: Modifier = Modifier,
    dailyCompleted: Boolean,
    biWeeklyCompleted: Boolean,
    onNavigateToDaily: () -> Unit,
    onNavigateToBiWeekly: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth(0.5f)
            .padding(top = 50.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Spacer(Modifier.height(10.dp))
        EvaluationCard(
            title = "Daily Evaluation",
            desc = "",
            completed = dailyCompleted,
            onNavigate = { onNavigateToDaily() }

        )
        EvaluationCard(
            title = "Bi-Weekly Evaluation",
            desc = "",
            completed = biWeeklyCompleted,
            onNavigate = { onNavigateToBiWeekly() }
        )

        EvaluationCard(
            title = "Analytics",
            desc = "",
            checkMark = 0,
            onNavigate = { onNavigateToBiWeekly() }
        )
    }
}

/**
 * Composable for an individual evaluation card, including a checkmark if completed.
 *
 * @param modifier Modifier for customizations to the layout.
 * @param title The title of the evaluation (e.g., Daily Evaluation, Bi-Weekly Evaluation).
 * @param desc A description for the evaluation (can be left empty).
 * @param completed Boolean flag to indicate if the evaluation is completed.
 * @param checkMark An integer to control whether a checkmark is shown.
 * @param onNavigate A function to navigate when the card is clicked.
 */
@Composable
fun EvaluationCard(
    modifier: Modifier = Modifier,
    title: String,
    desc: String,
    completed: Boolean = false,
    checkMark: Int = 1,
    onNavigate: () -> Unit
) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(6.dp),
        modifier = modifier
            .padding(top = 15.dp, bottom = 15.dp)
            .background(MaterialTheme.colorScheme.background)
            .fillMaxWidth(0.75f),
        // .fillMaxHeight(0.2f),
        onClick = { onNavigate() }
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            if (checkMark == 1) {
                IsCompletedCheckButton(completed)
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge
                )

            } else {
                Text(
                    modifier = Modifier.padding(10.dp),
                    text = title,
                    style = MaterialTheme.typography.titleLarge
                )
            }


        }

        HorizontalDivider()
    }
}


/**
 * Composable for a checkmark icon button that indicates if an evaluation is completed.
 *
 * @param completed Boolean flag to show if the evaluation is completed.
 */
@Composable
fun IsCompletedCheckButton(completed: Boolean) {
    IconToggleButton(
        checked = completed,
        onCheckedChange = {}
    ) {
        if (completed) {
            Icon(
                Icons.Filled.CheckCircle,
                contentDescription = ""
            )
        } else {
            Icon(
                Icons.Outlined.CheckCircle,
                contentDescription = ""
            )
        }
    }
}