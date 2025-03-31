package com.zurtar.mhma.home

import androidx.compose.foundation.background
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
import androidx.compose.material.icons.rounded.DateRange
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zurtar.mhma.util.DefaultTopAppBar
import java.sql.Date
import java.text.SimpleDateFormat
import java.time.Instant
import java.util.Locale

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
                    .padding(top = 20.dp),
                textAlign = TextAlign.Center
            )

            Text(
                text = formattedDate,
                fontSize = 20.sp,
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

        Row (
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

        }
    }

}

@Composable
fun NavigationCard(cardModifier:Modifier, title: String, onNavigateTo: () -> Unit) {

    ElevatedCard(
        modifier = cardModifier.background(Color.Transparent),
        onClick = { onNavigateTo() }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
                .background(Color.Transparent),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge,
                text = title

            )

//            Icon(imageVector = Icons.Rounded.DateRange,
//                contentDescription = "",
//                modifier = Modifier.size(100.dp)
//            )
        }
    }
}

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

@Composable
fun UpcomingScheduleCard() {

    ElevatedCard(
        modifier = Modifier
            .padding(horizontal = 10.dp, vertical = 10.dp)
            .width(350.dp)
            .height(150.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.titleLarge,
                text = "To-do:"
            )
            Text(
                modifier = Modifier.padding(horizontal = 15.dp),
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.bodyMedium,
                text = getToDo()
            )
            Spacer(
                modifier = Modifier.height(20.dp)
            )
            Text(
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.titleLarge,
                text = "Upcoming:"
            )
            Text(
                modifier = Modifier.padding(horizontal = 15.dp),
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.bodyMedium,
                text = getUpcoming()
            )
        }

    }
}

@Composable
fun SuggestedActionsCard() {
    ElevatedCard(
        modifier = Modifier
            .padding(horizontal = 10.dp, vertical = 10.dp)
            .fillMaxWidth()
            .height(100.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.titleLarge,
                text = "Suggested Actions:"
            )
            Text(
                modifier = Modifier.padding(horizontal = 15.dp),
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.bodyMedium,
                text = getSuggestedActions()
            )
        }

    }


}


fun getToDo(): String {
    return "• Quick Evaluation"
}

fun getUpcoming(): String {
    return "• Bi-Weekly Evaluation"
}

fun getSuggestedActions(): String {
    return "• Suggested Actions"
}

@Preview(showBackground = true)
@Composable
fun HomeScreenContentPreview() {
    MaterialTheme {
        Scaffold(modifier = Modifier.fillMaxSize(),
            topBar = {
                DefaultTopAppBar(openDrawer = { })
            }
        ) { innerPadding ->
            HomeScreenContent(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
                onNavigateToMoodEvaluation = {},
                onNavigateToJournal = {},
                onNavigateToAnalytics = {},
                onNavigateToChatbot = {},
            )
        }
    }
}
