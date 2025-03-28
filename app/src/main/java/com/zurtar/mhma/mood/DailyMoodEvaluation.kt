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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.test.isSelected
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
    openDrawer: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    Scaffold(modifier = modifier.fillMaxSize(),
        topBar = {
            DefaultTopAppBar(openDrawer = openDrawer)
        }
    ) { innerPadding ->
//        if (uiState.isSubmitted == 1) {
//            DailyResult(
//                modifier = modifier
//                    .padding(innerPadding)
//                    .fillMaxSize(), uiState.currentEmotion, uiState.strongestEmotion
//            )
//            return@Scaffold
//        }

        DailyMoodEvaluationScreenContent(
            modifier = modifier
                .padding(innerPadding)
                .fillMaxSize(),
            page = uiState.page,
            selectedEmotions = uiState.selectedEmotions,
            emotionIntensities = uiState.emotionIntensities,
            onUpdateEmotion = viewModel::updateEmotion,
            onEmotionSelect = viewModel::emotionSelect,
            onSubmit = { viewModel.onSubmit() },
            onBack = { viewModel.onBack() },
            onNext = { viewModel.onNext() },
            updateIntensity = viewModel::updateIntensity
        )
    }
}

@Composable
private fun DailyMoodEvaluationScreenContent(
    modifier: Modifier = Modifier,
    page: Int,
    selectedEmotions: List<String>,
    emotionIntensities: List<Float>,
    onUpdateEmotion: (ImageVector) -> Unit,
    onEmotionSelect: (String) -> Unit,
    onSubmit: () -> Unit,
    onBack: () -> Unit,
    onNext: () -> Unit,
    updateIntensity:(Float, Int) ->Unit
) {

    var selectedRating by remember { mutableStateOf<Int?>(null) }

    val questions: Array<String> = stringArrayResource(R.array.quick_eval_questions)


    if (page == 3) {
        DailyResult(modifier = modifier, selectedEmotions = selectedEmotions, emotionIntensities=emotionIntensities )
        return
    }

    Column(
        modifier = modifier.background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {

        DailyQuestions(
            selectedEmotions = selectedEmotions,
            emotionSelect = onEmotionSelect,
            emotionIntensities = emotionIntensities,
            num = page,
            question = questions[page],
            updateIntensity = updateIntensity
        )

        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            if (page > 0)
                FilledTonalButton(
                    onClick = { onBack() },
                    content = { Text("Back") })

            var text = "Next"
            if (page == 2) text = "Submit"
            FilledTonalButton(onClick = { onNext() }, content = { Text(text) })
        }
    }

}

@Composable
fun EmotionRating(emotionsList:List<String>, emotionIntensities:List<Float>, updateIntensity:(Float, Int) ->Unit) {


    ElevatedCard(
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end= 10.dp)
    ) {
        Text(
            text = "On a scale from 1-10, how strongly are you feeling these emotion",
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center
        )

        repeat(emotionsList.size) { i ->
            var sliderPosition by remember { mutableStateOf(0f) }

            Column(modifier = Modifier.padding(all = 20.dp)) {
                Text(
                    text = emotionsList[i],
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
                    onValueChangeFinished = { updateIntensity(sliderPosition, i)

                    },
                    steps = 9,
                    valueRange = 0f..10f
                )
                Text(
                    text = sliderPosition.toString(),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Preview
@Composable
fun EmotionRatingPrev() {

    val emotionsList = listOf("Happy", "Sad", "Angry")
    val emotionIntensities = listOf(3.0f, 6.0f, 9.0f)

  //  EmotionRating(emotionsList, emotionIntensities)

}

@Composable
fun DailyQuestions(
    modifier: Modifier = Modifier,
    selectedEmotions: List<String>,
    emotionIntensities:List<Float>,
    num: Int,
    question: String,
    emotionSelect: (String) -> Unit,
    updateIntensity:(Float, Int) ->Unit
) {
    if (num == 0) {
        EmotionSelectionCard(
            selectedEmotions = selectedEmotions,
            emotionSelect = emotionSelect
        )
    } else if (num == 1) {
        EmotionRating(selectedEmotions,emotionIntensities, updateIntensity)
    }

}

@Composable
private fun EmotionSelectionCard(
    modifier: Modifier = Modifier,
    selectedEmotions: List<String>,
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
                        isSelected = selectedEmotions.contains(emotion),
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


@Composable
private fun DailyResult(
    modifier: Modifier = Modifier,
    selectedEmotions: List<String>,
    emotionIntensities: List<Float>,
) {

    Column(modifier.padding(5.dp), verticalArrangement = Arrangement.Top) {
        Text(
            text = "Daily Evaluation Summary",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 20.dp)


        )
        //  CurrentMoodResult(currentEmotion)
        Spacer(modifier = Modifier.height(16.dp))
        //  StrongestEmotionResult(selectedEmotions)
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


@Composable
private fun StrongestEmotionResult(strongestEmotion: String) {

    val emotions = listOf(
        "Sad" to Color(0xFF1E88E5), // Blue
        "Happy" to Color(0xFF4CAF50), // Yellow
        "Fearful" to Color(0xFF8E24AA), // Purple
        "Angry" to Color(0xBAD32F2F), // Red

    )
    var buttColor = Color.White
    emotions.forEach { (emotion, color) ->
        if (strongestEmotion == emotion) {
            buttColor = color
        }
    }

    ElevatedCard(elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "The strongest emotions you have felt today were: ",
                //modifier = Modifier.padding(bottom = 10.dp),
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 25.dp)
            )
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    onClick = { },
                    shape = RoundedCornerShape(50), // Oval shape
                    colors = ButtonDefaults.buttonColors(
                        containerColor = buttColor,
//            containerColor = if (isSelected) color else color.copy(alpha = 0.3f),
                        contentColor = Color.White
                    ),
                    modifier = Modifier
                        .fillMaxWidth(0.3f) // Make it stretch horizontally
                        .height(40.dp) // Oval-like height
                ) {
                    Text(
                        text = strongestEmotion,
                        style = MaterialTheme.typography.bodyMedium,
                        maxLines = 1
                    )
                }
            }
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
fun AIPage() {
    // Variable to hold the text entered in the TextField
    var aiText by remember { mutableStateOf("") }

    // Simple screen with a TextField
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("AI PAGE")

        Spacer(modifier = Modifier.height(16.dp))

        // TextField for user input
        OutlinedTextField(
            value = aiText,
            onValueChange = { aiText = it },
            label = { Text("Enter something here...") },
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp),
            placeholder = { Text("Type your input...") }
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
fun EmotionPickerScreenPreview() {
    AppTheme {
        val navController = rememberNavController()
    }
}

@Preview(showBackground = true)
@Composable
fun PastEmotionsScreenPreview() {
    AppTheme {
        val navController = rememberNavController()
        PastEmotionsScreen(navController = navController)
    }
}

@Preview(showBackground = true)
@Composable
fun AIPagePreview() {
    AppTheme {
        AIPage()
    }
}
*/
