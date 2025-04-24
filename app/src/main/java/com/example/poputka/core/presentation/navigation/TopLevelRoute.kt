package com.example.poputka.core.presentation.navigation

import androidx.annotation.DrawableRes
import com.example.poputka.R
import com.example.poputka.feature_home.presentation.navigation.HomeDestination
import com.example.poputka.feature_journal.presentation.navigation.JournalDestination
import com.example.poputka.feature_settings.presentation.navigation.ProfileDestination
import com.example.poputka.feature_tips.presentation.navigation.TipsDestination

data class TopLevelRoute<T : Any>(val name: String, val route: T, @DrawableRes val icon: Int)

val topLevelRoutes = listOf(
    TopLevelRoute("Main", HomeDestination.Home, R.drawable.water_drop),
    TopLevelRoute("Journal", JournalDestination.Journal, R.drawable.list_alt),
    TopLevelRoute("Tips", TipsDestination.Tips, R.drawable.heart_check),
    TopLevelRoute("Profile", ProfileDestination.Profile, R.drawable.person)
)