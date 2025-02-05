package com.example.poputka.presentation.profile.profile_screen.vertical_selector

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import com.example.poputka.presentation.profile.profile_screen.vertical_selector.ScrollConstants.CENTER_THRESHOLD_DIVIDER
import com.example.poputka.presentation.profile.profile_screen.vertical_selector.ScrollConstants.SCALE_STEP
import com.example.poputka.presentation.profile.profile_screen.vertical_selector.ScrollUtils.calculateItemCenter
import kotlin.math.abs
import kotlin.math.roundToInt

@Composable
fun rememberScaleFactor(
    listState: LazyListState,
    index: Int,
    verticalPaddingPx: Int
): State<Float> {
    return remember {
        derivedStateOf {
            val itemInfo = listState.layoutInfo.visibleItemsInfo.firstOrNull { it.index == index }
                ?: return@derivedStateOf 1f

            val viewportHeight = listState.layoutInfo.viewportSize.height
            val viewportCenter = viewportHeight / CENTER_THRESHOLD_DIVIDER

            val itemCenter = calculateItemCenter(itemInfo, verticalPaddingPx)
            val distanceFromCenter = abs(itemCenter - viewportCenter)
            val maxDistance = viewportCenter.toFloat()

            val rawScale = 1f - (distanceFromCenter / maxDistance).coerceIn(0f, 1f) * 0.5f

            (rawScale / SCALE_STEP).roundToInt() * SCALE_STEP
        }
    }
}