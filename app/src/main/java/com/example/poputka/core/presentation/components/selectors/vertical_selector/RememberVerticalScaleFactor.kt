package com.example.poputka.core.presentation.components.selectors.vertical_selector

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import com.example.poputka.core.presentation.components.selectors.common.rememberScaleFactor
import com.example.poputka.core.presentation.components.selectors.util.ScrollConstants
import com.example.poputka.core.presentation.components.selectors.util.VerticalScrollUtils

@Composable
fun rememberVerticalScaleFactor(
    listState: LazyListState,
    index: Int,
    verticalPaddingPx: Int
): State<Float> {
    return rememberScaleFactor(
        listState = listState,
        index = index,
        paddingPx = verticalPaddingPx,
        calculateViewportCenter = { viewportSize -> viewportSize.height / ScrollConstants.CENTER_THRESHOLD_DIVIDER },
        calculateItemCenter = VerticalScrollUtils::calculateItemCenter
    )
}