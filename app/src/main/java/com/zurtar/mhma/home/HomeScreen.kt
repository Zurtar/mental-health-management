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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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

    Column(
        modifier = modifier.fillMaxWidth(0.5f),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val currentDate = Date.from(Instant.now())
        Text(
            text = getGreetingFromDate(currentDate),
            fontSize = 27.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp),
            textAlign = TextAlign.Center
        )
        val dateFormat = SimpleDateFormat("EEEE, MMMM dd", Locale.getDefault())
        val formattedDate = dateFormat.format(currentDate)
        Text(
            text = formattedDate,
            fontSize = 20.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 25.dp),
        )

        ElevatedCard(
            modifier = Modifier
                .padding(horizontal = 10.dp, vertical = 10.dp)
                .width(300.dp)
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


        val cardModifier = Modifier
            .padding(horizontal = 10.dp, vertical = 10.dp)
            .width(80.dp)
            .height(120.dp)
            .weight(1f)
            .align(Alignment.Start)

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            ElevatedCard(
                modifier = cardModifier,
                onClick = { onNavigateToMoodEvaluation() }
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
                        text = "Mood Eval"
                    )
                }

            }

            ElevatedCard(
                modifier = cardModifier,
                onClick = { onNavigateToJournal() }
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
                        text = "Journal"
                    )
                }
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            ElevatedCard(
                modifier = cardModifier,
                onClick = { onNavigateToChatbot() }
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(10.dp),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Top
                ) {
                    Text(
                        modifier = Modifier.padding(5.dp),
                        textAlign = TextAlign.Start,
                        style = MaterialTheme.typography.titleLarge,
                        text = "Chat Bot"
                    )
                }
            }

            Spacer(
                modifier = Modifier
                    .width(160.dp)
                    .height(150.dp)
                    .padding(10.dp)
                    .padding(horizontal = 10.dp, vertical = 10.dp)
                    .weight(1f)
                    .aspectRatio(1f)
            )
        }


        ElevatedCard(
            modifier = Modifier
                .padding(top = 20.dp)
                .fillMaxWidth(0.75f)
                .fillMaxHeight(0.2f)
                .background(color = MaterialTheme.colorScheme.background),
            onClick = { onNavigateToAnalytics() }
        ) {
            Text(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .height(150.dp)
                    .wrapContentHeight(),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineMedium,
                text = "Analytics"
            )
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
