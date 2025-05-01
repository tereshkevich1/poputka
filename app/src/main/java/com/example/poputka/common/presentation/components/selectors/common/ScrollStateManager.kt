package com.example.poputka.common.presentation.components.selectors.common

import androidx.compose.foundation.lazy.LazyListState
import com.example.poputka.common.presentation.components.selectors.util.Orientation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class ScrollStateManager(
    private val listState: LazyListState,
    private val coroutineScope: CoroutineScope,
    private val paddingPx: Int = 0,
    private val orientation: Orientation
) {
    fun scrollTo(index: Int) {
        coroutineScope.launch {
            val layoutInfo = listState.layoutInfo
            val itemInfo =
                layoutInfo.visibleItemsInfo.firstOrNull { it.index == index } ?: return@launch

            when (orientation) {
                Orientation.HORIZONTAL -> {
                    val viewportHeight = layoutInfo.viewportSize.width
                    val centerOffset = (viewportHeight - itemInfo.size) / 2 - paddingPx
                    listState.animateScrollToItem(index, -centerOffset)
                }

                Orientation.VERTICAL -> {
                    val viewportHeight = layoutInfo.viewportSize.height
                    val centerOffset = (viewportHeight - itemInfo.size) / 2 - paddingPx
                    listState.animateScrollToItem(index, -centerOffset)
                }
            }
        }
    }
}