package com.example.poputka.core.presentation.components.selectors.util

import androidx.compose.foundation.lazy.LazyListItemInfo
import androidx.compose.foundation.lazy.LazyListState
import kotlin.math.abs

internal object VerticalScrollUtils {
    fun calculateCenterIndex(
        listState: LazyListState,
        verticalPaddingPx: Int
    ): Int {
        val layoutInfo = listState.layoutInfo
        val viewportCenter = layoutInfo.viewportSize.height / 2
        return layoutInfo.visibleItemsInfo
            .minByOrNull {
                abs(calculateItemCenter(it, verticalPaddingPx) - viewportCenter)
            }?.index ?: 0
    }

    fun calculateItemCenter(
        itemInfo: LazyListItemInfo,
        verticalPaddingPx: Int
    ): Int = itemInfo.offset + verticalPaddingPx + itemInfo.size / ScrollConstants.CENTER_THRESHOLD_DIVIDER
}