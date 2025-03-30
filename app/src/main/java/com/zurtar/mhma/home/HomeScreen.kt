package com.zurtar.mhma.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zurtar.mhma.R
import com.zurtar.mhma.util.DefaultTopAppBar
import androidx.core.graphics.toColorInt

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
            modifier = modifier
                .padding(innerPadding)
                .fillMaxSize(),
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
        modifier = modifier.fillMaxWidth(0.5f),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Home",
            fontSize = MaterialTheme.typography.headlineLarge.fontSize,
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color("#B8D4A6".toColorInt())),
            color = Color.Black,
            textAlign = TextAlign.Center
        )

        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val cardModifier = Modifier
                .padding(top = 20.dp)
                .fillMaxHeight(0.2f)
                .background(color = MaterialTheme.colorScheme.background)
                .weight(1f)
            ElevatedCard(
                modifier = cardModifier,
                onClick = { onNavigateToMoodEvaluation() }
            ) {
                Text(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .height(150.dp)
                        .wrapContentHeight(),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.headlineSmall,
                    text = "Mood Evaluation"
                )
            }
            Spacer(Modifier.width(20.dp))

            ElevatedCard(
                modifier = cardModifier,
                onClick = { onNavigateToJournal() }
            ) {
                Text(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .height(150.dp)
                        .wrapContentHeight(),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.headlineSmall,
                    text = "Journal"
                )
            }

        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
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
                    style = MaterialTheme.typography.headlineSmall,
                    text = "Chatbot"
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenContentPreview() {
    MaterialTheme {
        Scaffold(modifier = Modifier.fillMaxSize(),
            topBar = {
                DefaultTopAppBar(openDrawer = { })
            }
        ) {innerPadding ->
            HomeScreenContent(
                modifier = Modifier.padding(innerPadding).fillMaxSize(),
                onNavigateToMoodEvaluation = {},
                onNavigateToJournal = {},
                onNavigateToChatbot = {}
            )

        }
    }
}