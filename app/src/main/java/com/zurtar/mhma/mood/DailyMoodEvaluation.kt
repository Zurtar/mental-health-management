package com.zurtar.mhma.mood

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.zurtar.mhma.R
import com.zurtar.mhma.util.DefaultTopAppBar
import com.zurtar.mhma.theme.EmojiFrown
import com.zurtar.mhma.theme.EmojiNeutral
import com.zurtar.mhma.theme.EmojiSmile


@Composable
fun DailyMoodEvaluationScreen(
    modifier: Modifier = Modifier,
    viewModel: DailyEvaluationViewModel = viewModel(),
    openDrawer: () -> Unit,
    onNavigateToAnalytics: (Int) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    Scaffold(modifier = modifier.fillMaxSize(),
        topBar = {
            DefaultTopAppBar(openDrawer = openDrawer)
        }
    ) { innerPadding ->


        DailyMoodEvaluationScreenContent(
            modifier = modifier
                .padding(innerPadding)
                .fillMaxSize(),
            dailyEntry = uiState.dailyEntry,
            isSubmitted = uiState.isSubmitted,
            onEmotionSelect = viewModel::emotionSelect,
            onSubmit = { viewModel.onSubmit() },
            onBack = { viewModel.onBack() },
            onNext = { viewModel.onNext() },
            updateIntensity = viewModel::updateIntensity,
            onNavigateToAnalytics = onNavigateToAnalytics
        )
    }
}

@Composable
private fun DailyMoodEvaluationScreenContent(
    modifier: Modifier = Modifier,
    dailyEntry: DailyEvaluationEntry,
    isSubmitted: Int,
    onEmotionSelect: (String) -> Unit,
    onSubmit: () -> Unit,
    onBack: () -> Unit,
    onNext: () -> Unit,
    updateIntensity: (Float, Int) -> Unit,
    onNavigateToAnalytics: (Int) -> Unit
) {

    Column(
        modifier = modifier.background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {

        if (dailyEntry.page == 0)
            EmotionSelectionCard(
                dailyEntry = dailyEntry,
                emotionSelect = onEmotionSelect
            )

        if (dailyEntry.page == 1)
            EmotionRating(dailyEntry, updateIntensity)

        if (isSubmitted == 1) {
            DailyResult(modifier = modifier, dailyEntry, onNavigateToAnalytics)
        }

        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            if (dailyEntry.page > 0)
                FilledTonalButton(
                    onClick = { onBack() },
                    content = { Text("Back") })

            var text = "Next"
            if (dailyEntry.page == 1) {
                text = "Submit"
                FilledTonalButton(onClick = { onSubmit() }, content = { Text(text) })

            } else {
                FilledTonalButton(onClick = { onNext() }, content = { Text(text) })
            }
        }
    }
}

@Composable
fun EmotionRating(
    dailyEntry: DailyEvaluationEntry, updateIntensity: (Float, Int) -> Unit
) {

    ElevatedCard(
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp)
    ) {
        Text(
            text = "On a scale from 1-10, how strongly are you feeling these emotion",
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center
        )

        repeat(dailyEntry.selectedEmotions.size) { i ->
            var sliderPosition by remember { mutableFloatStateOf(1f) }

            Column(modifier = Modifier.padding(all = 20.dp)) {
                Text(
                    text = dailyEntry.selectedEmotions[i],
                    style = MaterialTheme.typography.bodyMedium
                )
                Slider(
                    value = sliderPosition,
                    onValueChange = { sliderPosition = it },
                    colors = SliderDefaults.colors(
                        thumbColor = MaterialTheme.colorScheme.secondary,
                        activeTrackColor = MaterialTheme.colorScheme.secondary,
                        inactiveTrackColor = MaterialTheme.colorScheme.secondaryContainer,
                    ),
                    onValueChangeFinished = {
                        updateIntensity(sliderPosition, i)

                    },
                    steps = 9,
                    valueRange = 1f..10f
                )
                Text(
                    text = sliderPosition.toInt().toString(),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}


@Composable
private fun EmotionSelectionCard(
    modifier: Modifier = Modifier,
    dailyEntry: DailyEvaluationEntry,
    emotionSelect: (String) -> Unit
) {
    var selectedEmotion = ""
    val emotions = listOf(
        "Sad" to Color(0xFF1E88E5), // Blue
        "Happy" to Color(0xFF4CAF50), // Yellow
        "Fearful" to Color(0xFF8E24AA), // Purple
        "Angry" to Color(0xBAD32F2F), // Red

    )


    ElevatedCard(
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        modifier = modifier
            .padding(bottom = 15.dp, top = 5.dp)
            .fillMaxWidth(0.85f)
    ) {

        Column(
            modifier = Modifier
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text("What emotion have you felt strongest today?")
            HorizontalDivider()
            // Vertical Emotion Picker
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                emotions.forEach { (emotion, color) ->
                    EmotionChip(
                        emotion = emotion,
                        color = color,
                        isSelected = dailyEntry.selectedEmotions.contains(emotion),
                        onClick = {
                            selectedEmotion = emotion
                            emotionSelect(emotion)
                        }
                    )
                }
            }
        }
    }
}


@Composable
private fun MoodSelectionCard(
    modifier: Modifier = Modifier,
    updateEmotion: (ImageVector) -> Unit
) {
    var selectedMood by remember { mutableStateOf<ImageVector?>(null) }

    val icons = listOf(
        EmojiFrown to Color.Red,
        EmojiNeutral to Color.LightGray,
        EmojiSmile to Color.Green
    )

    ElevatedCard(
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        modifier = modifier
            .padding(bottom = 15.dp, top = 15.dp)
            .fillMaxWidth(0.85f)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text("How are you feeling today?")
            HorizontalDivider()
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                icons.forEach { p ->
                    val emoji = p.first
                    var tint = Color.Gray
                    if (emoji == selectedMood)
                        tint = Color.White

                    Icon(
                        imageVector = emoji,
                        contentDescription = "Neutral Emoji",
                        modifier = Modifier
                            .size(35.dp)
                            .clickable(
                                interactionSource = null,
                                indication = null,
                                onClick = {
                                    selectedMood = emoji

                                    updateEmotion(emoji)
                                }
                            ),
                        tint = tint,
                    )
                }
            }
//                Spacer(modifier = Modifier.height(16.dp))
            /*
                            if (selectedEmotion != null) {
                                Text(
                                    text = "Please pick a option on why you are feeling $selectedEmotion:",
                                    style = MaterialTheme.typography.bodyMedium
                                )

                                // Rating options: 1 to 5 as a vertical list
                                Column(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalArrangement = Arrangement.spacedBy(16.dp)
                                ) {
                                    (1..5).forEach { rating ->
                                        Button(
                                            onClick = {
                                                selectedRating = rating
                                                onNavigate() // Navigate to the AI page when a button is clicked
                                            },
                                            shape = RoundedCornerShape(50),
                                            colors = ButtonDefaults.buttonColors(
                                                containerColor = if (selectedRating == rating) Color.Gray else Color.LightGray
                                            ),
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .height(50.dp)
                                        ) {
                                            Text("Item $rating")
                                        }
                                    }
                                }
                            }
            */
        }
    }
}


@Preview
@Composable
fun DailyResultPrev() {

    val emotions = listOf("Happy", "Angry")
    val intensities = listOf(3.0f, 7.0f)

    val dEntry = DailyEvaluationEntry(
        selectedEmotions = emotions,
        emotionIntensities = intensities,
        emotionsMap = emotions.zip(intensities).toMap()
    )

    DailyResult(dailyEntry = dEntry, onNavigateToAnalytics = {})
}

@Composable
private fun DailyResult(
    modifier: Modifier = Modifier,
    dailyEntry: DailyEvaluationEntry,
    onNavigateToAnalytics: (Int) -> Unit
) {

    Column(
        modifier
            .padding(5.dp)
            .background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.padding(bottom = 30.dp),
            text = "You have completed the Quick evaluation!",
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center
        )

        Text(
            modifier = Modifier.padding(bottom = 20.dp),
            text = "Strongest Emotion:",
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center
        )

        Row(modifier = Modifier.fillMaxWidth()) {
            Button(
                onClick = { },
                shape = RoundedCornerShape(50), // Oval shape
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    contentColor = Color.Black
                ),
            ) {
                Text(
                    text = "Happy",
                    style = MaterialTheme.typography.bodyMedium,
                )
            }

            Text(
                text = "5",
                style = MaterialTheme.typography.bodyMedium,
            )

        }

        ProceedCard("Proceed to Evaluation Analytics") { onNavigateToAnalytics(0) }
        ProceedCard("Proceed to Journal") { onNavigateToAnalytics(0) }

        Spacer(modifier = Modifier.height(16.dp))

        FilledTonalButton(
            onClick = { },
            content = { Text("Exit") })
    }

}

@Composable
fun EmotionSummaryTable() {
    ElevatedCard(
        Modifier.padding(top = 15.dp),

        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(
                text = "EMOTION",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier
                    .width(100.dp)
                    .padding(end = 40.dp),
                textAlign = TextAlign.Start
            )

            Text(
                text = "SCORE",
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Right,
                modifier = Modifier
                    .width(100.dp)
                    .padding(end = 20.dp)
            )
        }
    }
}

@Composable
private fun CurrentMoodResult(currentEmotion: String) {
    ElevatedCard(elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "You are currently feeling: ",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = 10.dp)
            )
            Text(
                text = currentEmotion,
                fontSize = 20.sp
            )
        }
    }
}

fun makeMap(intensities: List<Float>, emotion: List<String>): MutableMap<String, Float> {
    val emotionsMap = mutableMapOf<String, Float>()

    for (i in 0..emotion.size) {
        emotionsMap[emotion[i]] = intensities[i]
    }

    return emotionsMap
}


@Composable
fun StrongestEmotionResult(dailyEntry: DailyEvaluationEntry) {
//
//    val emotions = listOf(
//        "Sad" to Color(0xFF1E88E5), // Blue
//        "Happy" to Color(0xFF4CAF50), // Yellow
//        "Fearful" to Color(0xFF8E24AA), // Purple
//        "Angry" to Color(0xBAD32F2F), // Red
//
//    )
//
//    val emotionsMap = makeMap(dailyEntry.emotionIntensities, dailyEntry.selectedEmotions)
//
//    // val strongestEmotion = emotionsMap.filter { it.value == emotionsMap.values.max()}
//
//    val strongestEmotion = "Happy"
//
////    var buttColor = Color.White
////    emotions.forEach { (emotion, color) ->
////        if (strongestEmotion.containsKey(emotion)) {
////            buttColor = color
////        }
////    }
//    var buttColor = Color.White
//    emotions.forEach { (emotion, color) ->
//        if (strongestEmotion == emotion) {
//            buttColor = color
//        }
//    }


    ElevatedCard(elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)) {
        Row() {
            Text(
                text = "The strongest emotions you have felt today were: ",
                //modifier = Modifier.padding(bottom = 10.dp),
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 25.dp)
            )
        }
    }
}


@Composable
private fun EmotionChip(emotion: String, color: Color, isSelected: Boolean, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(50), // Oval shape
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) color else MaterialTheme.colorScheme.onSecondary,
//            containerColor = if (isSelected) color else color.copy(alpha = 0.3f),
            contentColor = Color.White
        ),
        modifier = Modifier
            .fillMaxWidth() // Make it stretch horizontally
            .height(50.dp) // Oval-like height
    ) {
        Text(
            text = emotion,
            style = MaterialTheme.typography.bodyMedium,
            maxLines = 1
        )
    }
}


@Composable
fun PastEmotionsScreen(modifier: Modifier = Modifier, navController: NavHostController) {
    Column(modifier = modifier.fillMaxSize()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("No past emotions recorded yet.", style = MaterialTheme.typography.bodyMedium)
        }

        // Back Button at the bottom
        Spacer(modifier = Modifier.weight(1f)) // This pushes the button to the bottom

        Button(
            onClick = { navController.popBackStack() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text("Back")
        }
    }
}

/*


@Preview(showBackground = true)
@Composable
fun PastEmotionsScreenPreview() {
    AppTheme {
        val navController = rememberNavController()
        PastEmotionsScreen(navController = navController)
    }
}
*/
