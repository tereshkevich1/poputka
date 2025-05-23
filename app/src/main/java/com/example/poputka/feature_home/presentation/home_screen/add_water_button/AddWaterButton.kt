package com.example.poputka.feature_home.presentation.home_screen.add_water_button

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalViewConfiguration
import androidx.compose.ui.res.painterResource
import com.example.poputka.R
import com.example.poputka.common.presentation.constants.UiConstants.fabSize
import com.example.poputka.common.presentation.constants.UiConstants.fabYOffset
import com.example.poputka.common.presentation.util.dpToPx
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest

@Composable
fun AddWaterButton(
    onClick: () -> Unit,
    onLongClick: () -> Unit,
    bottomBarState: Boolean,
    modifier: Modifier,
    containerColor: Color = MaterialTheme.colorScheme.primary,
    contentColor: Color = MaterialTheme.colorScheme.onPrimary
) {
    val interactionSource = remember { MutableInteractionSource() }
    val viewConfiguration = LocalViewConfiguration.current

    LaunchedEffect(interactionSource) {
        var isLongClick = false

        interactionSource.interactions.collectLatest { interaction ->
            when (interaction) {
                is PressInteraction.Press -> {
                    isLongClick = false
                    delay(viewConfiguration.longPressTimeoutMillis)
                    isLongClick = true
                    onLongClick()
                }

                is PressInteraction.Release -> {
                    if (isLongClick.not()) {
                        onClick()
                    }
                }
            }
        }
    }

    val fabYOffsetPx = dpToPx(fabYOffset)
    AnimatedVisibility(
        visible = bottomBarState,
        enter = slideInVertically(initialOffsetY = { it + fabYOffsetPx }),
        exit = slideOutVertically(targetOffsetY = { 2 * it + fabYOffsetPx }),
    ) {
        FloatingActionButton(
            onClick = {},
            modifier = modifier.size(fabSize),
            shape = CircleShape,
            containerColor = containerColor,
            contentColor = contentColor,
            interactionSource = interactionSource
        ) {
            Icon(painter = painterResource(R.drawable.add), contentDescription = null)
        }
    }
}