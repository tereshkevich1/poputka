package com.example.poputka.presentation.navigation

import androidx.annotation.DrawableRes
import com.example.poputka.R
import com.example.poputka.presentation.home.navigation.HomeDestination
import com.example.poputka.presentation.journal.navigation.JournalDestination
import com.example.poputka.presentation.profile.navigation.ProfileDestination
import com.example.poputka.presentation.tips.navigation.TipsDestination

data class TopLevelRoute<T : Any>(val name: String, val route: T,@DrawableRes val icon: Int)

val topLevelRoutes = listOf(
    TopLevelRoute("Main", HomeDestination.Home, R.drawable.baseline_water_drop),
    TopLevelRoute("Journal", JournalDestination.Journal, R.drawable.list_alt),
    TopLevelRoute("Tips", TipsDestination.Tips, R.drawable.heart_check),
    TopLevelRoute("Profile", ProfileDestination.Profile, R.drawable.person)
)