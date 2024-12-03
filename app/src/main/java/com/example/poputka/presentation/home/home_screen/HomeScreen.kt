package com.example.poputka.presentation.home.home_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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
import com.example.poputka.presentation.home.home_screen.add_water_button.AddWaterButton
import com.example.poputka.presentation.home.home_screen.drink_log_panel.DrinkItem
import com.example.poputka.presentation.home.home_screen.drink_log_panel.DrinkLogHeader
import com.example.poputka.presentation.home.home_screen.hydration_info_panel.HydrationInfoPanel
import com.example.poputka.presentation.home.util.DrinkCategory
import com.example.poputka.ui.theme.PoputkaTheme

@Composable
fun HomeScreen() {
    val list = listOf(
        Pair(DrinkCategory.EnergyDrink, "2100"),
        Pair(DrinkCategory.Wine, "8"),
        Pair(DrinkCategory.Water, "900"),
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

        HydrationInfoPanel("100 ml", "200 ml", {}, Modifier)
        DrinkLogHeader()
        DrinkItem(
            DrinkCategory.Water,
            "900 ml",
            "03:00",
            {},
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        DrinkItem(
            DrinkCategory.Tea,
            "90 ml",
            "12:00",
            {},
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        DrinkItem(
            DrinkCategory.Wine,
            "900 ml",
            "13:00",
            {},
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        DrinkItem(
            DrinkCategory.EnergyDrink,
            "900 ml",
            "03:00",
            {},
            modifier = Modifier.padding(horizontal = 16.dp)
        )
    }
}

@Composable
@Preview
fun DrinkItemPreview() {
    PoputkaTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                HydrationInfoPanel("200", "3000", {}, Modifier)

                Spacer(Modifier.height(16.dp))

                AddWaterButton({}, {}, modifier = Modifier)
            }
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
