package com.example.poputka.core.presentation.components.selectors.vertical_selector

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.text.TextStyle

@Composable
fun VerticalSelectorItem(
    listState: LazyListState,
    index: Int,
    value: String,
    verticalPaddingPx: Int,
    textStyle: TextStyle,
    onClick: () -> Unit
) {
    val textColor by rememberVerticalTextColor(listState, index, verticalPaddingPx)
    val scale by rememberVerticalScaleFactor(listState, index, verticalPaddingPx)

    Text(
        value,
        style = textStyle,
        modifier = Modifier
            .scale(scale)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { onClick() },
        color = textColor
    )
}