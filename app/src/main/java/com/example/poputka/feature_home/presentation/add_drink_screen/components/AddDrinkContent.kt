@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.poputka.feature_home.presentation.add_drink_screen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.poputka.R
import com.example.poputka.core.presentation.DrinkCategory
import com.example.poputka.feature_home.presentation.add_drink_screen.AddDrinkUiState
import com.example.poputka.feature_home.presentation.add_drink_screen.components.date_time_block.SpacerWithDivider
import com.example.poputka.feature_home.presentation.add_drink_screen.components.date_time_block.TimeRow
import com.example.poputka.feature_home.presentation.add_drink_screen.components.drink_category_selector.DrinkCategorySelector
import com.example.poputka.feature_home.presentation.add_drink_screen.components.drink_liquid_control.DrinkLiquidControl
import com.example.poputka.feature_home.presentation.add_drink_screen.components.favorite_drinks_block.FavoriteDrinksBlock

@Composable
fun AddDrinkContent(
    state: AddDrinkUiState,
    time: String,
    date: String,
    onDateSelect: (Long?) -> Unit,
    onTimeSelect: (TimePickerState) -> Unit,
    onDrinkCategoryChange: (DrinkCategory?) -> Unit,
    drinkVolume: String,
    onDrinkVolumeChange: (String) -> Unit
) {
    val largePadding = dimensionResource(R.dimen.padding_large)
    val innerPadding = dimensionResource(id = R.dimen.padding_medium)
    val smallPadding = dimensionResource(id = R.dimen.padding_small)
    val outlineVariantColor = MaterialTheme.colorScheme.outlineVariant

    Column(
        modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SpacerWithDivider(outlineVariantColor, 20.dp)
        TimeRow(
            state.time,
            state.date,
            time,
            date,
            onDateSelect = { onDateSelect(it) },
            onDateDismiss = {},
            onTimeSelect = { onTimeSelect(it) },
            onTimeDismiss = {},
            modifier = Modifier.padding(horizontal = innerPadding, vertical = smallPadding)
        )
        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(),
            thickness = 1.dp,
            color = outlineVariantColor
        )

        val deviceScreenWidth = LocalConfiguration.current.screenWidthDp.dp
        val liquidControlWidth = 120.dp
        val leftControlPadding = (deviceScreenWidth - liquidControlWidth) / 2

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = innerPadding),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.width(leftControlPadding))
            DrinkLiquidControl(
                color = colorResource(state.drinkCategory.colorId),
                modifier = Modifier
                    .fillMaxHeight(0.6f)
                    .width(liquidControlWidth)
            )

            Spacer(modifier = Modifier.weight(1f))
            FavoriteDrinksBlock()
            Spacer(modifier = Modifier.weight(1f))
        }

        DrinkVolumeInputField(drinkVolume, onDrinkVolumeChange)

        Spacer(modifier = Modifier.weight(1f))

        DrinkCategorySelector(onDrinkCategoryChange = onDrinkCategoryChange)

        Button(
            onClick = {},
            Modifier
                .padding(start = largePadding, end = largePadding)
                .fillMaxWidth()
                .height(56.dp)
        ) {
            Text(text = stringResource(R.string.add_drink))
        }

        Spacer(modifier = Modifier.weight(1f))
    }
}