package com.example.poputka.common.presentation.components.selectors.common

import androidx.compose.foundation.lazy.LazyListItemInfo
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.IntSize
import com.example.poputka.common.presentation.components.selectors.util.ScrollConstants
import kotlin.math.abs
import kotlin.math.roundToInt

@Composable
fun rememberScaleFactor(
    listState: LazyListState,
    index: Int,
    paddingPx: Int,
    calculateViewportCenter: (viewportSize: IntSize) -> Int,
    calculateItemCenter: (LazyListItemInfo, Int) -> Int
): State<Float> {
    return remember {
        derivedStateOf {
            val itemInfo = listState.layoutInfo.visibleItemsInfo.firstOrNull { it.index == index }
                ?: return@derivedStateOf 1f

            val viewportCenter = calculateViewportCenter(listState.layoutInfo.viewportSize)
            val itemCenter = calculateItemCenter(itemInfo, paddingPx)
            val distanceFromCenter = abs(itemCenter - viewportCenter)
            val maxDistance = viewportCenter.toFloat()
            val rawScale = 1f - (distanceFromCenter / maxDistance).coerceIn(0f, 1f) * 0.5f

            (rawScale / ScrollConstants.SCALE_STEP).roundToInt() * ScrollConstants.SCALE_STEP
        }
    }
}