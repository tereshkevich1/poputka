package com.example.poputka.feature_home.presentation.add_drink_screen.components.drink_category_selector

import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.Dp
import kotlin.math.abs

@Composable
fun DrinkCategoryCard(
    state: LazyListState,
    index: Int,
    horizontalLazyRowPadding: Int,
    backgroundColor: Color,
    icon: Painter,
    drinkName: String,
    cardWidth: Dp,
) {
    val selectedColor = MaterialTheme.colorScheme.inverseSurface

    val borderColor by remember {
        derivedStateOf {
            val layoutInfo = state.layoutInfo
            val visibleItemsInfo = layoutInfo.visibleItemsInfo
            val itemInfo = visibleItemsInfo.firstOrNull { it.index == index }

            itemInfo?.let {
                val delta = it.size / 2
                val center = (horizontalLazyRowPadding + state.layoutInfo.viewportEndOffset) / 2
                val childCenter = horizontalLazyRowPadding + it.offset + it.size / 2
                val target = childCenter - center
                if (target in -delta..delta) return@derivedStateOf selectedColor
            }
            Color.Transparent
        }
    }

    val scale by remember {
        derivedStateOf {
            val currentItem = state.layoutInfo.visibleItemsInfo.firstOrNull { it.index == index }
                ?: return@derivedStateOf 1.0f
            val halfRowWidth = (state.layoutInfo.viewportSize.width) / 2
            (1f - minOf(
                1f,
                abs((currentItem.offset + horizontalLazyRowPadding) + (currentItem.size / 2) - halfRowWidth).toFloat() / halfRowWidth
            ) * 0.50f)
        }
    }
    DrinkItem(
        backgroundColor,
        icon,
        drinkName,
        borderColor,
        Modifier
            .width(cardWidth)
            .scale(scale)
    )
}