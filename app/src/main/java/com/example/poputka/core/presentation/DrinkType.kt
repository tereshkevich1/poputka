package com.example.poputka.core.presentation

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.poputka.R

sealed class DrinkCategory(
    @StringRes val nameId: Int,
    @DrawableRes val iconId: Int,
    @ColorRes val colorId: Int,
    val hydration: Int,
) {
    data object Water : DrinkCategory(R.string.water, R.drawable.baseline_water_drop, R.color.water_color, 100)
    data object EnergyDrink : DrinkCategory(R.string.energy_drink, R.drawable.bolt, R.color.energy_drink_color, 80)
    data object Tea : DrinkCategory(R.string.tea, R.drawable.emoji_food_beverage, R.color.tea_color,90)
    data object Coffee : DrinkCategory(R.string.coffee, R.drawable.coffee, R.color.coffee_color, 80)
    data object Juice : DrinkCategory(R.string.juice, R.drawable.grocery, R.color.juice_color, 90)
    data object Milk : DrinkCategory(R.string.milk, R.drawable.heart_check, R.color.milk_color, 90)
    data object Soda : DrinkCategory(R.string.soda, R.drawable.heart_check, R.color.soda_color, 80)
    data object Beer : DrinkCategory(R.string.beer, R.drawable.sports_bar, R.color.beer_color, 70)
    data object Wine : DrinkCategory(R.string.wine, R.drawable.wine_bar, R.color.wine_color, 50)
    data object Whiskey : DrinkCategory(R.string.whiskey, R.drawable.liquor, R.color.whiskey_color, 10)
}


