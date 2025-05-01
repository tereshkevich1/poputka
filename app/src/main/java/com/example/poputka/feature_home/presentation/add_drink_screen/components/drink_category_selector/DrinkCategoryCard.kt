package com.example.poputka.feature_home.presentation.add_drink_screen.components.drink_category_selector

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.Dp
import com.example.poputka.common.presentation.components.selectors.horizontal_selector.rememberHorizontalColor
import com.example.poputka.common.presentation.components.selectors.horizontal_selector.rememberHorizontalScaleFactor

@Composable
fun DrinkCategoryCard(
    onClick: () -> Unit,
    listState: LazyListState,
    index: Int,
    horizontalLazyRowPadding: Int,
    backgroundColor: Color,
    icon: Painter,
    drinkName: String,
    cardWidth: Dp,
) {
    val borderColor by rememberHorizontalColor(
        listState = listState,
        index = index,
        horizontalPaddingPx = horizontalLazyRowPadding,
        selectedColor = MaterialTheme.colorScheme.inverseSurface,
        unselectedColor = Color.Transparent
    )

    val scale by rememberHorizontalScaleFactor(
        listState = listState,
        index = index,
        horizontalPaddingPx = horizontalLazyRowPadding
    )

    DrinkItem(
        backgroundColor = backgroundColor,
        icon = icon,
        description = drinkName,
        borderColor = borderColor,
        modifier = Modifier
            .width(cardWidth)
            .scale(scale)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { onClick() }
    )
}


