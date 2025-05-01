package com.example.poputka.common.presentation.components.selectors.horizontal_selector

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.graphics.Color
import com.example.poputka.common.presentation.components.selectors.common.rememberItemColor
import com.example.poputka.common.presentation.components.selectors.util.HorizontalScrollUtils
import com.example.poputka.common.presentation.components.selectors.util.ScrollConstants

@Composable
fun rememberHorizontalColor(
    listState: LazyListState,
    index: Int,
    horizontalPaddingPx: Int,
    selectedColor: Color = MaterialTheme.colorScheme.onSurface,
    unselectedColor: Color = MaterialTheme.colorScheme.outline
): State<Color> {
    return rememberItemColor(
        listState = listState,
        index = index,
        paddingPx = horizontalPaddingPx,
        selectedColor = selectedColor,
        unselectedColor = unselectedColor,
        calculateViewportCenter = { viewportSize -> viewportSize.width / ScrollConstants.CENTER_THRESHOLD_DIVIDER },
        calculateItemCenter = HorizontalScrollUtils::calculateItemCenter
    )
}