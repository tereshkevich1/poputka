package com.example.poputka.core.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp

@Composable
fun DrinkItemIcon(
    backgroundColor: Color,
    icon: Painter,
    modifier: Modifier = Modifier
) {
    Image(
        painter = icon,
        contentDescription = null,
        modifier = modifier
            .size(28.dp)
            .clip(CircleShape)
            .background(color = backgroundColor),
        contentScale = ContentScale.Inside
    )
}