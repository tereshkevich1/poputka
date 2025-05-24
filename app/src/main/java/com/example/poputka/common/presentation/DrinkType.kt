package com.example.poputka.common.presentation

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.poputka.R

enum class DrinkCategory(
    @StringRes val nameId: Int,
    @DrawableRes val iconId: Int,
    @ColorRes val colorId: Int,
    val hydration: Int,
) {
    Water(R.string.water, R.drawable.water_drop_rounded, R.color.water_color, 100),
    EnergyDrink(R.string.energy_drink, R.drawable.bolt_rounded, R.color.energy_drink_color, 80),
    Tea(R.string.tea, R.drawable.emoji_food_rounded, R.color.tea_color, 90),
    Coffee(R.string.coffee, R.drawable.local_cafe_rounded, R.color.coffee_color, 80),
    Juice(R.string.juice, R.drawable.grocery_rounded, R.color.juice_color, 90),
    Milk(R.string.milk, R.drawable.heart_check, R.color.milk_color, 90),
    Soda(R.string.soda, R.drawable.heart_check, R.color.soda_color, 80),
    Beer(R.string.beer, R.drawable.sports_bar_rounded, R.color.beer_color, 70),
    Wine(R.string.wine, R.drawable.wine_bar_24dp_rounded, R.color.wine_color, 50),
    Whiskey(R.string.whiskey, R.drawable.liquor_rounded, R.color.whiskey_color, 10)
}


