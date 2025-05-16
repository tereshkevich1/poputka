package com.example.poputka.common.presentation.navigation.util

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut

fun enterFadeTransaction(): EnterTransition = fadeIn(
    animationSpec = tween(
        200, easing = LinearEasing
    )
)

fun exitFadeTransaction(): ExitTransition = fadeOut(
    animationSpec = tween(
        200, easing = LinearEasing
    )
)