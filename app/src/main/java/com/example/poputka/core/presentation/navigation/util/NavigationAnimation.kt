package com.example.poputka.core.presentation.navigation.util

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally

fun enterSlideTransition(): EnterTransition = slideInHorizontally(
    initialOffsetX = { fullWidth -> fullWidth },
    animationSpec = tween(500)
)

fun exitSlideTransition(): ExitTransition = slideOutHorizontally(
    targetOffsetX = { fullWidth -> -fullWidth },
    animationSpec = tween(500)
)

fun popEnterSlideTransition(): EnterTransition = slideInHorizontally(
    initialOffsetX = { fullWidth -> -fullWidth },
    animationSpec = tween(500)
)

fun popExitSlideTransition(): ExitTransition = slideOutHorizontally(
    targetOffsetX = { fullWidth -> fullWidth },
    animationSpec = tween(500)
)

fun enterFadeTransaction(): EnterTransition = fadeIn(
    animationSpec = tween(
        300, easing = LinearEasing
    )
)

fun exitFadeTransaction(): ExitTransition = fadeOut(
    animationSpec = tween(
        300, easing = LinearEasing
    )
)