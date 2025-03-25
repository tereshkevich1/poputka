package com.example.poputka.feature_settings.presentation.settings_screen.vertical_selector

import androidx.compose.foundation.lazy.LazyListState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class ScrollStateManager(
    private val listState: LazyListState,
    private val coroutineScope: CoroutineScope,
    private val verticalPaddingPx: Int = 0
) {
    fun scrollTo(index: Int) {
        coroutineScope.launch {
            val layoutInfo = listState.layoutInfo
            val itemInfo =
                layoutInfo.visibleItemsInfo.firstOrNull { it.index == index } ?: return@launch

            val viewportHeight = layoutInfo.viewportSize.height
            val centerOffset = (viewportHeight - itemInfo.size) / 2 - verticalPaddingPx
            listState.animateScrollToItem(index, -centerOffset)
        }
    }
}