package com.zurtar.mhma.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.zurtar.mhma.R
import com.zurtar.mhma.util.DefaultTopAppBar

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    openDrawer: () -> Unit,
    onNavigateToMoodEvaluation: () -> Unit,
    onNavigateToJournal: () -> Unit,
    onNavigateToChatbot: () -> Unit
) {
    Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = {
            DefaultTopAppBar(openDrawer = openDrawer)
        }
    ) { innerPadding ->
        HomeScreenContent(
            modifier = modifier.padding(innerPadding).fillMaxSize(),
            onNavigateToMoodEvaluation = onNavigateToMoodEvaluation,
            onNavigateToJournal = onNavigateToJournal,
            onNavigateToChatbot = onNavigateToChatbot
        )
    }
}

@Composable
private fun HomeScreenContent(
    modifier: Modifier = Modifier,
    onNavigateToMoodEvaluation: () -> Unit,
    onNavigateToJournal: () -> Unit,
    onNavigateToChatbot: () -> Unit
) {
    Column(
        modifier = modifier.fillMaxWidth(0.5f), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column (
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                IconButton(
                    onClick = { onNavigateToMoodEvaluation() }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_add_reaction_24),
                        contentDescription = "Options",
                        tint = Color.White
                    )
                }
                IconButton(
                    onClick = { onNavigateToJournal() }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_book_24),
                        contentDescription = "Options",
                        tint = Color.White
                    )
                }
                IconButton(
                    onClick = { onNavigateToChatbot() }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_chat_bubble_24),
                        contentDescription = "Options",
                        tint = Color.White
                    )
                }

                /*
                ElevatedCard(
                    modifier = Modifier
                        .padding(top = 20.dp)
                        .fillMaxWidth(0.75f)
                        .fillMaxHeight(0.2f)
                        .background(color = MaterialTheme.colorScheme.background),
                    onClick = { onNavigateToMoodEvaluation() }
                ) {
                    Text(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .height(150.dp)
                            .wrapContentHeight(),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.headlineMedium,
                        text = "Mood Evaluation"
                    )
                }

                ElevatedCard(
                    modifier = Modifier
                        .padding(top = 20.dp)
                        .fillMaxWidth(0.75f)
                        .fillMaxHeight(0.2f)
                        .background(color = MaterialTheme.colorScheme.background),
                    onClick = { onNavigateToJournal() }
                ) {
                    Text(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .height(150.dp)
                            .wrapContentHeight(),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.headlineMedium,
                        text = "Journal"
                    )
                }

                ElevatedCard(
                    modifier = Modifier
                        .padding(top = 20.dp)
                        .fillMaxWidth(0.75f)
                        .fillMaxHeight(0.2f)
                        .background(color = MaterialTheme.colorScheme.background),
                    onClick = { onNavigateToChatbot() }
                ) {
                    Text(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .height(150.dp)
                            .wrapContentHeight(),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.headlineMedium,
                        text = "Chatbot"
                    )
                }
                */

            }
        }
    }
}