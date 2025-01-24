package com.example.poputka.presentation.add_drink_screen.components.favorite_drinks_block

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow

@Composable
fun FavoriteDrinkItem(
    backgroundColor: Color,
    icon: Painter,
    description: String,
    modifier: Modifier,
    textStyle: TextStyle = MaterialTheme.typography.bodyMedium,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = icon,
            contentDescription = null,
            modifier = modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .clip(CircleShape)
                .background(backgroundColor)
                .clickable { },
            contentScale = ContentScale.Inside
        )
        Text(
            text = description,
            overflow = TextOverflow.Ellipsis,
            maxLines = 2,
            style = textStyle
        )
    }
}