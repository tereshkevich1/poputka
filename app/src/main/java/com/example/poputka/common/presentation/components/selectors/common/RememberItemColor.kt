package com.example.poputka.common.presentation.components.selectors.common

import androidx.compose.foundation.lazy.LazyListItemInfo
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.IntSize
import kotlin.math.abs

@Composable
fun rememberItemColor(
    listState: LazyListState,
    index: Int,
    paddingPx: Int,
    calculateViewportCenter: (viewportSize: IntSize) -> Int,
    calculateItemCenter: (LazyListItemInfo, Int) -> Int,
    selectedColor: Color = MaterialTheme.colorScheme.onSurface,
    unselectedColor: Color = MaterialTheme.colorScheme.outline
): State<Color> {
    return remember {
        derivedStateOf {
            val itemInfo = listState.layoutInfo.visibleItemsInfo.firstOrNull { it.index == index }
            val centerThreshold = itemInfo?.size?.div(2) ?: 0

            itemInfo?.let {
                val viewportCenter = calculateViewportCenter(listState.layoutInfo.viewportSize)
                val itemCenter = calculateItemCenter(it, paddingPx)
                if (abs(itemCenter - viewportCenter) <= centerThreshold) {
                    selectedColor
                } else {
                    unselectedColor
                }
            } ?: unselectedColor
        }
    }
}