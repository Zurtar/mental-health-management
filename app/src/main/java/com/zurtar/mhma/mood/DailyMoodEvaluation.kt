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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.zurtar.mhma.data.DailyEvaluationEntry
import com.zurtar.mhma.util.DefaultTopAppBar
import com.zurtar.mhma.theme.EmojiFrown
import com.zurtar.mhma.theme.EmojiNeutral
import com.zurtar.mhma.theme.EmojiSmile
import com.zurtar.mhma.util.MoodEvaluationTopAppBar

/**
 * Composable for displaying the Daily Mood Evaluation screen with a top app bar and navigation options.
 *
 * @param modifier Modifier for customizations to the layout.
 * @param viewModel The ViewModel to provide the UI state for the daily mood evaluation.
 * @param openDrawer A function to open the navigation drawer.
 * @param onNavigateToAnalytics A function to navigate to the analytics screen with an optional parameter for the selected entry.
 * @param onNavigateToJournal A function to navigate to the journal screen.
 */
@Composable
fun DailyMoodEvaluationScreen(
    modifier: Modifier = Modifier,
    viewModel: DailyEvaluationViewModel = hiltViewModel(),
    openDrawer: () -> Unit,
    onNavigateToAnalytics: (Int) -> Unit,
    onNavigateToJournal: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    Scaffold(modifier = modifier.fillMaxSize(),
        topBar = {
            MoodEvaluationTopAppBar(openDrawer = openDrawer)
        }
    ) { innerPadding ->


        DailyMoodEvaluationScreenContent(
            modifier = modifier
                .padding(innerPadding)
                .fillMaxSize(),
            dailyEntry = uiState.dailyEntry,
            isSubmitted = uiState.isSubmitted,
            page = uiState.page,
            onEmotionSelect = viewModel::emotionSelect,
            onSubmit = { viewModel.onSubmit() },
            onBack = { viewModel.onBack() },
            onNext = { viewModel.onNext() },
            updateIntensity = viewModel::updateIntensity,
            updateEmotion = viewModel::updateEmotion,
            onNavigateToAnalytics = onNavigateToAnalytics,
            onNavigateToJournal = onNavigateToJournal
        )
    }
}

/**
 * Composable that represents the content of the Daily Mood Evaluation screen, showing the evaluation process
 * and handling the navigation between pages and submission of data.
 *
 * @param modifier Modifier for customizations to the layout.
 * @param dailyEntry The current daily evaluation entry containing user responses.
 * @param isSubmitted Flag indicating whether the evaluation has been submitted.
 * @param page The current page in the mood evaluation process (e.g., page 0 for emotion selection, page 1 for intensity rating).
 * @param onEmotionSelect A function to handle the selection of an emotion.
 * @param onSubmit A function to handle submission of the mood evaluation.
 * @param onBack A function to navigate back to the previous page.
 * @param onNext A function to move to the next page.
 * @param updateIntensity A function to update the intensity of the selected emotion.
 * @param updateEmotion A function to update the selected emotion.
 * @param onNavigateToAnalytics A function to navigate to the analytics screen with the selected data.
 * @param onNavigateToJournal A function to navigate to the journal screen.
 */
@Composable
private fun DailyMoodEvaluationScreenContent(
    modifier: Modifier = Modifier,
    dailyEntry: DailyEvaluationEntry,
    isSubmitted: Int,
    page: Int,
    onEmotionSelect: (String) -> Unit,
    onSubmit: () -> Unit,
    onBack: () -> Unit,
    onNext: () -> Unit,
    updateIntensity: (Float, Int) -> Unit,
    updateEmotion: (ImageVector) -> Unit,
    onNavigateToAnalytics: (Int) -> Unit,
    onNavigateToJournal: () -> Unit
) {

    Column(
        modifier = modifier.background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        if (isSubmitted == 1) {
            DailyResult(
                dailyEntry = dailyEntry,
                onNavigateToAnalytics = onNavigateToAnalytics,
                onNavigateToJournal = onNavigateToJournal
            )
            return
        }

        if (page == 0)
            MoodSelectionQuestionPage(
                dailyEntry = dailyEntry,
                emotionSelect = onEmotionSelect,
                updateEmotion = updateEmotion
            )

        if (page == 1) {
            if (dailyEntry.selectedEmotions.isEmpty()) {
                DailyResult(
                    modifier = modifier,
                    dailyEntry,
                    onNavigateToAnalytics,
                    onNavigateToJournal = onNavigateToJournal
                )
                return
            }
            EmotionRating(dailyEntry, updateIntensity)
        }

        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            if (page > 0)
                FilledTonalButton(modifier = Modifier
                    .width(120.dp)
                    .height(50.dp)
                    .padding(top = 10.dp),
                    onClick = { onBack() },
                    content = {
                        Text(
                            text = "Back",
                            fontSize = 18.sp
                        )
                    })

            var text = "Next"
            if (page == 1) {
                text = "Submit"
                FilledTonalButton(modifier = Modifier
                    .width(120.dp)
                    .height(50.dp)
                    .padding(top = 10.dp),
                    onClick = { onSubmit() },
                    content = {
                        Text(
                            text = text,
                            fontSize = 18.sp
                        )
                    })

            } else {
                FilledTonalButton(modifier = Modifier
                    .width(120.dp)
                    .height(50.dp)
                    .padding(top = 10.dp),
                    onClick = { onNext() },
                    content = {
                        Text(
                            text = text,
                            fontSize = 18.sp
                        )
                    })
            }
        }
    }
}

/**
 * Composable that represents the page where the user selects their emotion for the daily mood evaluation.
 * Displays emotion selection cards to allow users to choose their current emotional state.
 *
 * @param modifier Modifier for customizations to the layout.
 * @param dailyEntry The current daily evaluation entry containing user responses.
 * @param emotionSelect A function to handle the selection of an emotion.
 * @param updateEmotion A function to update the selected mood emoji.
 */
@Composable
fun MoodSelectionQuestionPage(
    modifier: Modifier = Modifier,
    dailyEntry: DailyEvaluationEntry,
    emotionSelect: (String) -> Unit,
    updateEmotion: (ImageVector) -> Unit
) {

    Column(modifier = modifier, verticalArrangement = Arrangement.Center) {
        StressSelectionCard(modifier = Modifier, updateEmotion = updateEmotion)
        EmotionSelectionCard(
            dailyEntry = dailyEntry,
            emotionSelect = emotionSelect
        )
    }

}

/**
 * Composable that displays a card with different emotions to allow users to select which emotion they felt
 * strongest during the day.
 *
 * @param modifier Modifier for customizations to the layout.
 * @param dailyEntry The current daily evaluation entry containing user responses.
 * @param emotionSelect A function to handle the selection of an emotion.
 */
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
            Text(
                text = "What emotion have you felt strongest today?",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge,
                fontSize = 20.sp
            )
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

/**
 * Composable that displays a card allowing the user to select their stress level using emojis.
 * The user can select an emoji representing their current mood.
 *
 * @param modifier Modifier for customizations to the layout.
 * @param updateEmotion A function to update the selected stress emoji.
 */
@Composable
private fun StressSelectionCard(
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
            Text(
                text = "How stressed are you feeling today?",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge,
                fontSize = 20.sp
            )
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
                        tint = MaterialTheme.colorScheme.primary

                    Icon(
                        imageVector = emoji,
                        contentDescription = "Neutral Emoji",
                        modifier = Modifier
                            .size(40.dp)
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
        }
    }
}

/**
 * Composable that displays a card allowing the user to rate the intensity of the emotions they selected.
 * Users can slide a scale from 1-10 to indicate how strongly they are feeling each emotion.
 *
 * @param dailyEntry The current daily evaluation entry containing user responses.
 * @param updateIntensity A function to update the intensity of a selected emotion.
 */
@Composable
fun EmotionRating(
    dailyEntry: DailyEvaluationEntry, updateIntensity: (Float, Int) -> Unit
) {

    ElevatedCard(
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp, top = 25.dp, bottom = 30.dp)
    ) {


        Text(
            modifier = Modifier.padding(15.dp),
            text = "On a scale from 1-10, how strongly are you feeling these emotions",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleLarge,
            fontSize = 20.sp
        )

        repeat(dailyEntry.selectedEmotions.size) { i ->
            var sliderPosition by remember { mutableFloatStateOf(1f) }

            Column(modifier = Modifier.padding(all = 20.dp)) {
                Text(
                    text = dailyEntry.selectedEmotions[i],
                    style = MaterialTheme.typography.bodyMedium,
                    fontSize = 18.sp
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
                    style = MaterialTheme.typography.bodyMedium,
                    fontSize = 15.sp,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

/**
 * Composable that displays the results after completing the daily mood evaluation.
 * Provides a summary of the user's selected emotions, their stress level, and navigation options.
 *
 * @param modifier Modifier for customizations to the layout.
 * @param dailyEntry The current daily evaluation entry containing user responses.
 * @param onNavigateToAnalytics A function to navigate to the evaluation analytics screen.
 * @param onNavigateToJournal A function to navigate to the journal screen.
 */
@Composable
private fun DailyResult(
    modifier: Modifier = Modifier,
    dailyEntry: DailyEvaluationEntry,
    onNavigateToAnalytics: (Int) -> Unit,
    onNavigateToJournal: () -> Unit
) {

    Column(
        modifier
            .padding(5.dp)
            .background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.padding(top = 15.dp, bottom = 30.dp),
            text = "You have completed the Quick evaluation!",
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center
        )

        EmotionSummaryTable(dailyEntry)

        Text(
            modifier = Modifier.padding(bottom = 50.dp),
            text = "Current Stress Level: ${dailyEntry.stressLevel}",
            style = MaterialTheme.typography.titleMedium,
            fontSize = 20.sp
        )
        HorizontalDivider()
        ProceedCard("Proceed to Evaluation Analytics") { onNavigateToAnalytics(0) }
        HorizontalDivider()
        ProceedCard("Proceed to Journal") { onNavigateToJournal() }
        HorizontalDivider()

        Spacer(modifier = Modifier.height(16.dp))
//
//        FilledTonalButton(
//            onClick = { },
//            content = { Text("Exit") })
    }

}

/**
 * Composable that displays a table summarizing the selected emotions and their corresponding scores.
 *
 * @param dailyEntry The current daily evaluation entry containing user responses.
 */
@Composable
fun EmotionSummaryTable(dailyEntry: DailyEvaluationEntry) {

    ElevatedCard(
        Modifier
            .padding(top = 5.dp, bottom = 20.dp)
            .fillMaxWidth(0.85f),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(
                modifier = Modifier.width(100.dp),
                text = "EMOTION",
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Start,
                fontSize = 18.sp
            )

            Text(
                modifier = Modifier.width(70.dp),
                text = "SCORE",
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Right,
                fontSize = 18.sp
            )
        }

        dailyEntry.emotionsMap.forEach {
            EmotionsChart(emotion = it.key, score = it.value.toString())
        }
    }
}

/**
 * Composable that displays a row showing a single emotion and its corresponding score.
 *
 * @param modifier Modifier for customizations to the layout.
 * @param emotion The emotion name.
 * @param score The score associated with the emotion.
 */
@Composable
fun EmotionsChart(modifier: Modifier = Modifier, emotion: String, score: String) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 5.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(
            modifier = Modifier.padding(start = 5.dp, bottom = 5.dp),
            text = emotion,
            style = MaterialTheme.typography.titleSmall,
            textAlign = TextAlign.Left,
            fontSize = 15.sp
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            modifier = Modifier.padding(start = 5.dp, bottom = 5.dp),
            text = score,
            style = MaterialTheme.typography.titleSmall,
            textAlign = TextAlign.Left,
            fontSize = 15.sp
        )
    }
}


/**
 * Composable that represents a clickable chip-style button displaying an emotion.
 * Allows the user to select the emotion.
 *
 * @param emotion The emotion text to display.
 * @param color The color associated with the emotion.
 * @param isSelected A flag to indicate whether the emotion is selected.
 * @param onClick A function to handle the chip click action.
 */
@Composable
private fun EmotionChip(emotion: String, color: Color, isSelected: Boolean, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(50), // Oval shape
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) color else MaterialTheme.colorScheme.secondary,
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
            fontSize = 20.sp,
            maxLines = 1
        )
    }
}