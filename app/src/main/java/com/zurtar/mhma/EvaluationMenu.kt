package com.zurtar.mhma

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun Testcard (str: String) {
    var description = ""
    if(str == "Daily Evaluation") {
        description = "Complete a list of simple questions to discover your average mood for the day"
    }

    if(str == "Bi-Weekly Evaluation"){
        description = " More in-depth questions"
    }

    ElevatedCard(elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        modifier = Modifier.size(width = 320.dp, height = 100.dp)
    ){
        Column {
            Text(
                text = str,
                modifier = Modifier.padding(16.dp),
                textAlign = TextAlign.Center
            )
            Text(
                text = description,
                modifier = Modifier.padding(start = 16.dp)
            )
        }

    }

}

@Preview
@Composable
fun Screen(modifier: Modifier = Modifier) {
    Column(modifier = modifier){

        Testcard("Daily Evaluation")
        Spacer(Modifier.size(16.dp))
        Testcard("Bi-Weekly Evaluation")
    }
}
