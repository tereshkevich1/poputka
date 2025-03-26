package com.example.poputka.core.presentation.components.selectors.horizontal_selector

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import com.example.poputka.core.presentation.components.selectors.util.HorizontalScrollUtils
import com.example.poputka.core.presentation.components.selectors.common.rememberScaleFactor

@Composable
fun rememberHorizontalScaleFactor(
    listState: LazyListState,
    index: Int,
    horizontalPaddingPx: Int
): State<Float> {
    return rememberScaleFactor(
        listState = listState,
        index = index,
        paddingPx = horizontalPaddingPx,
        calculateViewportCenter = { viewportSize -> viewportSize.width / 2 },
        calculateItemCenter = HorizontalScrollUtils::calculateItemCenter
    )
}