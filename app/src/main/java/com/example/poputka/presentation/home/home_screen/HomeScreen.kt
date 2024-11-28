package com.example.poputka.presentation.home.home_screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.poputka.presentation.canvas.custom_circular_progress_indicator.AnimatedCircularProgressIndicator
import com.example.poputka.presentation.home.home_screen.drink_log_panel.DrinkLogPanel
import com.example.poputka.presentation.home.util.DrinkCategory
import com.example.poputka.ui.theme.PoputkaTheme

@Composable
fun HomeScreen() {
    val list = listOf(
        Pair(DrinkCategory.EnergyDrink, "2100"),
        Pair(DrinkCategory.Wine, "8"),
        Pair(DrinkCategory.Water, "900"),
        Pair(DrinkCategory.Tea, "700"),
        Pair(DrinkCategory.Coffee, "20"),
        Pair(DrinkCategory.Beer, "50")
    )
    var currentAnimValue by remember { mutableFloatStateOf(600f) }
    val maxValue = 2000f
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(modifier = Modifier.height(12.dp))
        AnimatedCircularProgressIndicator(
            maxValue = maxValue,
            progressBackgroundColor = MaterialTheme.colorScheme.surfaceContainerHighest,
            progressIndicatorColor = MaterialTheme.colorScheme.primary,
            indicatorColor = MaterialTheme.colorScheme.surfaceContainerHighest,
            currentValueProvider = { currentAnimValue }
        )
        Spacer(modifier = Modifier.height(32.dp))

        DrinkLogPanel(list)

        Button(
            onClick = { currentAnimValue += 200f },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Increase Progress")
        }
    }
}

@Composable
@Preview
fun HomeScreenPreview() {
    PoputkaTheme {
        HomeScreen()
    }
}
