package com.example.poputka.feature_home.presentation.home_screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.poputka.feature_home.presentation.home_screen.custom_circular_progress_indicator.AnimatedCircularProgressIndicator
import com.example.poputka.feature_home.presentation.home_screen.add_water_button.AddWaterButton
import com.example.poputka.feature_home.presentation.home_screen.drink_log_panel.DrinkItem
import com.example.poputka.feature_home.presentation.home_screen.drink_log_panel.DrinkLogHeader
import com.example.poputka.feature_home.presentation.home_screen.hydration_info_panel.HydrationInfoPanel
import com.example.poputka.feature_settings.presentation.local_settings_state.LocalSettingsState
import com.example.poputka.core.presentation.DrinkCategory
import com.example.poputka.core.presentation.constants.UiConstants.bottomNavAndFabPadding
import com.example.poputka.ui.theme.PoputkaTheme

@Composable
fun HomeScreen() {
    val volumeUnit = LocalSettingsState.current.volumeUnitSetting.abbreviation
    val currentAnimValue by remember { mutableFloatStateOf(600f) }
    val maxValue = 2000f
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(bottom = bottomNavAndFabPadding),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(12.dp))
        AnimatedCircularProgressIndicator(
            maxValue = maxValue,
            progressBackgroundColor = MaterialTheme.colorScheme.surfaceContainerHighest,
            progressIndicatorColor = MaterialTheme.colorScheme.primary,
            indicatorColor = MaterialTheme.colorScheme.surfaceContainerHighest,
            currentValueProvider = { currentAnimValue }
        )
        Spacer(modifier = Modifier.height(32.dp))

        HydrationInfoPanel("100 $volumeUnit", "200 $volumeUnit", {}, Modifier)
        DrinkLogHeader()
        DrinkItem(
            DrinkCategory.Water,
            "900 $volumeUnit",
            "03:00",
            {},
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        DrinkItem(
            DrinkCategory.Tea,
            "90 $volumeUnit",
            "12:00",
            {},
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        DrinkItem(
            DrinkCategory.Wine,
            "900 $volumeUnit",
            "13:00",
            {},
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        DrinkItem(
            DrinkCategory.EnergyDrink,
            "900 $volumeUnit",
            "03:00",
            {},
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        DrinkItem(
            DrinkCategory.EnergyDrink,
            "900 $volumeUnit",
            "03:00",
            {},
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        DrinkItem(
            DrinkCategory.Tea,
            "90 $volumeUnit",
            "12:00",
            {},
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        DrinkItem(
            DrinkCategory.Wine,
            "900 $volumeUnit",
            "13:00",
            {},
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        DrinkItem(
            DrinkCategory.EnergyDrink,
            "900 $volumeUnit",
            "03:00",
            {},
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        DrinkItem(
            DrinkCategory.EnergyDrink,
            "900 $volumeUnit",
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

                AddWaterButton({}, {}, true, modifier = Modifier)
            }
        }
    }
}

@SuppressLint("ViewModelConstructorInComposable")
@Composable
@Preview
fun HomeScreenPreview() {
    PoputkaTheme {
        HomeScreen()
    }
}
