package com.zurtar.mhma

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun DailyMoodEvaluationScreen(modifier: Modifier = Modifier, onNavigate: () -> Unit) {
    var selectedEmotion by remember { mutableStateOf<String?>(null) }
    var selectedRating by remember { mutableStateOf<Int?>(null) }

    val emotions = listOf(
        "Sad" to Color(0xFF1E88E5), // Blue
        "Happy" to Color(0xFFFFEB3B), // Yellow
        "Fearful" to Color(0xFF8E24AA), // Purple
        "Angry" to Color(0xFFD32F2F), // Red
        "Surprised" to Color(0xFFFFA726), // Orange
        "Disgusted" to Color(0xFF388E3C) // Green
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "How are you feeling today?",
            style = MaterialTheme.typography.titleMedium
        )

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
                    isSelected = selectedEmotion == emotion,
                    onClick = { selectedEmotion = emotion }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

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
    }
}

@Composable
fun EmotionChip(emotion: String, color: Color, isSelected: Boolean, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(50), // Oval shape
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) color else color.copy(alpha = 0.3f),
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
