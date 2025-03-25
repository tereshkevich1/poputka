package com.example.poputka.feature_home.presentation.home_screen.drink_log_panel

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.poputka.R
import com.example.poputka.core.presentation.DrinkCategory
import com.example.poputka.core.presentation.components.DrinkItemIcon
import com.example.poputka.ui.theme.PoputkaTheme

@Composable
fun DrinkItem(
    drink: DrinkCategory,
    amount: String,
    time: String,
    onAddButtonClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .padding(bottom = dimensionResource(R.dimen.padding_small))
            .fillMaxWidth()
            .height(64.dp)
            .clip(MaterialTheme.shapes.extraLarge)
            .background(MaterialTheme.colorScheme.surfaceContainerHigh),
        verticalAlignment = Alignment.CenterVertically
    ) {
        DrinkItemIcon(
            backgroundColor = colorResource(drink.colorId),
            icon = painterResource(drink.iconId),
            modifier = Modifier.padding(start = 16.dp)
        )

        DrinkDetails(
            drink = drink,
            time = time,
            modifier = Modifier
                .padding(start = 16.dp)
                .weight(0.8f)
        )

        DrinkAmount(
            amount = amount,
            hydration = drink.hydration,
            modifier = Modifier.weight(1f)
        )

        AddButton(onClick = onAddButtonClick)
    }
}


@Composable
@Preview
fun DrinkListItemPreview() {
    PoputkaTheme(darkTheme = true) {
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize()
            ) {
                DrinkItem(
                    DrinkCategory.Water,
                    "900 ml",
                    "03:00",
                    {},
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
        }
    }
}