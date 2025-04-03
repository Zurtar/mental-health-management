package com.zurtar.mhma.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.rounded.Call
import androidx.compose.material.icons.rounded.Create
import androidx.compose.material.icons.rounded.DateRange
import androidx.compose.material.icons.rounded.Share
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zurtar.mhma.mood.ProceedCard
import com.zurtar.mhma.util.DefaultTopAppBar
import java.sql.Date
import java.text.SimpleDateFormat
import java.time.Instant
import java.util.Locale

/**
 * Composable for the Home screen, which serves as the main dashboard. It includes navigation options
 * to different sections of the app such as mood evaluation, journaling, chatbot, and analytics.
 *
 * @param modifier Modifier to be applied to the composable.
 * @param openDrawer A lambda function to open the navigation drawer.
 * @param onNavigateToMoodEvaluation A lambda function for navigating to the mood evaluation screen.
 * @param onNavigateToJournal A lambda function for navigating to the journal screen.
 * @param onNavigateToChatbot A lambda function for navigating to the chatbot screen.
 * @param onNavigateToAnalytics A lambda function for navigating to the analytics screen.
 */
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    openDrawer: () -> Unit,
    onNavigateToMoodEvaluation: () -> Unit,
    onNavigateToJournal: () -> Unit,
    onNavigateToChatbot: () -> Unit,
    onNavigateToAnalytics: () -> Unit
) {
    Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = {
            DefaultTopAppBar(openDrawer = openDrawer)
        }
    ) { innerPadding ->
        HomeScreenContent(
            modifier = modifier
                .padding(innerPadding)
                .fillMaxSize(),
            onNavigateToMoodEvaluation = onNavigateToMoodEvaluation,
            onNavigateToJournal = onNavigateToJournal,
            onNavigateToChatbot = onNavigateToChatbot,
            onNavigateToAnalytics = onNavigateToAnalytics,
        )
    }
}

/**
 * Composable function for displaying the content of the Home screen. This includes:
 * - A greeting based on the current time of day.
 * - The current date in a readable format.
 * - Navigation options to various sections of the app (Mood Evaluation, Journal, Chat, Analytics).
 *
 * @param modifier Modifier to be applied to the composable.
 * @param onNavigateToMoodEvaluation A lambda function for navigating to the mood evaluation screen.
 * @param onNavigateToJournal A lambda function for navigating to the journal screen.
 * @param onNavigateToAnalytics A lambda function for navigating to the analytics screen.
 * @param onNavigateToChatbot A lambda function for navigating to the chatbot screen.
 */
@Composable
private fun HomeScreenContent(
    modifier: Modifier = Modifier,
    onNavigateToMoodEvaluation: () -> Unit,
    onNavigateToJournal: () -> Unit,
    onNavigateToAnalytics: () -> Unit,
    onNavigateToChatbot: () -> Unit
) {

    val currentDate = Date.from(Instant.now())
    val dateFormat = SimpleDateFormat("EEEE, MMMM dd", Locale.getDefault())
    val formattedDate = dateFormat.format(currentDate)

    Column(modifier = modifier.fillMaxWidth()) {
        Column(
            //  modifier = modifier.fillMaxWidth(0.5f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = getGreetingFromDate(currentDate),
                fontSize = 27.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp, start = 10.dp),
                textAlign = TextAlign.Start
            )

            Text(
                text = formattedDate,
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 25.dp),
            )

//            UpcomingScheduleCard()
//
//            SuggestedActionsCard()
        }
        val cardModifier = Modifier
            .padding(horizontal = 10.dp, vertical = 10.dp)
            .width(80.dp)
            .height(120.dp)
            .weight(1f)
            .align(Alignment.CenterHorizontally)

        /*  Row (
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.Center
          ) {
              NavigationCard(cardModifier,"Mood Evaluation", onNavigateToMoodEvaluation)
              NavigationCard(cardModifier,"Chat", onNavigateToChatbot)
          }

          Row(modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.Center)
          {
              NavigationCard(cardModifier,"Journal", onNavigateToJournal)
              NavigationCard(cardModifier,"Analytics", onNavigateToAnalytics)

          }*/

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp)
        ) {
            HorizontalDivider()
            NavigationCard("Mood Evaluation", Icons.Rounded.DateRange, onNavigateToMoodEvaluation)
            HorizontalDivider()
            NavigationCard("Chat", Icons.Rounded.Call, onNavigateToChatbot)
            HorizontalDivider()
            NavigationCard("Journal", Icons.Rounded.Create, onNavigateToJournal)
            HorizontalDivider()
            NavigationCard("Analytics", Icons.Rounded.Share, onNavigateToAnalytics)
            HorizontalDivider()
        }
    }
}

/**
 * Composable function that creates a navigation card with an icon, text, and a forward arrow button.
 * The card is clickable and triggers the provided navigation action when clicked.
 *
 * @param text The text to be displayed on the card.
 * @param icon The icon to be displayed next to the text.
 * @param navigate The lambda function that will be invoked when the card is clicked, typically used for navigation.
 */
@Composable
fun NavigationCard(text: String, icon: ImageVector, navigate: () -> Unit) {
    OutlinedCard(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { navigate() },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        border = BorderStroke(1.dp, Color.Transparent)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Icon(
                imageVector = icon,
                contentDescription = "Favorite",
                modifier = Modifier.size(30.dp)
            )

            Text(
                modifier = Modifier.width(170.dp),
                text = text,
                style = MaterialTheme.typography.titleLarge
            )
            FilledTonalButton(
                onClick = { navigate() },
                colors = ButtonColors(
                    containerColor = Color.Transparent,
                    contentColor = Color.Black,
                    disabledContainerColor = Color.Transparent,
                    disabledContentColor = Color.Transparent
                )
            ) {

                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = "Favorite",
                    modifier = Modifier.size(30.dp)
                )
            }
        }
    }
}

/**
 * Returns a greeting based on the time of day.
 * It checks the hour of the day and returns a corresponding greeting:
 * "Good Morning!", "Good Afternoon!", or "Good Evening!".
 *
 * @param date The current date and time used to determine the greeting.
 * @return A greeting string based on the time of day.
 */
fun getGreetingFromDate(date: java.util.Date): String {
    val calendar = java.util.Calendar.getInstance()
    calendar.time = date
    val hour = calendar.get(java.util.Calendar.HOUR_OF_DAY)
    return when (hour) {
        in 0..11 -> "Good Morning!"
        in 12..17 -> "Good Afternoon!"
        else -> "Good Evening!"
    }
}