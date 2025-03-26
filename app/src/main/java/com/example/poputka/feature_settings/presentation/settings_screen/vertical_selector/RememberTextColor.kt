package com.example.poputka.feature_settings.presentation.settings_screen.vertical_selector

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import com.example.poputka.feature_settings.presentation.settings_screen.vertical_selector.ScrollConstants.CENTER_THRESHOLD_DIVIDER
import com.example.poputka.feature_settings.presentation.settings_screen.vertical_selector.ScrollUtils.calculateItemCenter
import kotlin.math.abs

@Composable
fun rememberTextColor(
    listState: LazyListState,
    index: Int,
    verticalPaddingPx: Int,
    selectedTextColor: Color = MaterialTheme.colorScheme.onSurface,
    unselectedTextColor: Color = MaterialTheme.colorScheme.outline
): State<Color> {
    return remember {
        derivedStateOf {
            val itemInfo = listState.layoutInfo.visibleItemsInfo.firstOrNull { it.index == index }
            val centerThreshold = itemInfo?.size?.div(2) ?: 0

            itemInfo?.let {
                val viewportCenter =
                    listState.layoutInfo.viewportSize.height / CENTER_THRESHOLD_DIVIDER
                val itemCenter = calculateItemCenter(itemInfo, verticalPaddingPx)
                if (abs(itemCenter - viewportCenter) <= centerThreshold) {
                    selectedTextColor
                } else {
                    unselectedTextColor
                }
            } ?: unselectedTextColor
        }
    }
}