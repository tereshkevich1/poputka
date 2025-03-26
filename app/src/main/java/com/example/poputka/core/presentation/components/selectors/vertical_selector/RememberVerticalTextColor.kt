package com.example.poputka.core.presentation.components.selectors.vertical_selector

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.graphics.Color
import com.example.poputka.core.presentation.components.selectors.common.rememberItemColor
import com.example.poputka.core.presentation.components.selectors.util.ScrollConstants
import com.example.poputka.core.presentation.components.selectors.util.VerticalScrollUtils

@Composable
fun rememberVerticalTextColor(
    listState: LazyListState,
    index: Int,
    verticalPaddingPx: Int,
    selectedTextColor: Color = MaterialTheme.colorScheme.onSurface,
    unselectedTextColor: Color = MaterialTheme.colorScheme.outline
): State<Color> {
    return rememberItemColor(
        listState = listState,
        index = index,
        paddingPx = verticalPaddingPx,
        selectedColor = selectedTextColor,
        unselectedColor = unselectedTextColor,
        calculateViewportCenter = { viewportSize -> viewportSize.height / ScrollConstants.CENTER_THRESHOLD_DIVIDER },
        calculateItemCenter = VerticalScrollUtils::calculateItemCenter
    )
}