package com.example.poputka.common.presentation.components.selectors.util

import androidx.compose.foundation.lazy.LazyListItemInfo
import androidx.compose.foundation.lazy.LazyListState
import kotlin.math.abs

internal object HorizontalScrollUtils {
    fun calculateCenterIndex(
        listState: LazyListState,
        horizontalPaddingPx: Int
    ): Int {
        val layoutInfo = listState.layoutInfo
        val viewportCenter = layoutInfo.viewportSize.width / 2
        return layoutInfo.visibleItemsInfo
            .minByOrNull {
                abs(calculateItemCenter(it, horizontalPaddingPx) - viewportCenter)
            }?.index ?: 0
    }

    fun calculateItemCenter(
        itemInfo: LazyListItemInfo,
        horizontalPaddingPx: Int
    ): Int = itemInfo.offset + horizontalPaddingPx + itemInfo.size / ScrollConstants.CENTER_THRESHOLD_DIVIDER
}