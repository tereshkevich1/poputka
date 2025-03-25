package com.example.poputka.feature_home.presentation.add_drink_screen.components.favorite_drinks_block

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.poputka.core.presentation.DrinkCategory

@Composable
fun FavoriteDrinksBlock() {
    val drinkCategory = DrinkCategory.Water
    val color = colorResource(drinkCategory.colorId)
    val drinkIcon = painterResource(drinkCategory.iconId)
    val description = "400 ml"
    Column {
        repeat(3) {
            FavoriteDrinkItem(
                backgroundColor = color,
                icon = drinkIcon,
                description = description,
                modifier = Modifier
                    .width(64.dp)
                    .padding(vertical = 2.dp)
            )
        }
    }
}