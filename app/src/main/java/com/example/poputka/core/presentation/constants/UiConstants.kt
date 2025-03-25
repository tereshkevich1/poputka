package com.example.poputka.core.presentation.constants

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * UiConstants — A centralized place for defining UI-related constants.
 *
 * `BottomNavBarHeight`:
 * - Represents the fixed height of the Bottom Navigation Bar.
 * - Used across the application to add padding or spacing where necessary,
 *   ensuring proper layout and preventing content from overlapping with the bar.
 */

object UiConstants {
    val BottomNavBarHeight = 94.dp
    val fabYOffset: Dp = 48.dp
    val fabSize: Dp = 68.dp
    val bottomNavAndFabPadding: Dp = BottomNavBarHeight + fabSize / 2 + 16.dp
}


