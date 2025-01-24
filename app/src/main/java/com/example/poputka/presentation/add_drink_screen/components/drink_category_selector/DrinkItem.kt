package com.example.poputka.presentation.add_drink_screen.components.drink_category_selector

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.poputka.presentation.util.DrinkItemIcon

@Composable
fun DrinkItem(
    backgroundColor: Color,
    icon: Painter,
    description: String,
    borderColor: Color,
    modifier: Modifier,
    textStyle: TextStyle = MaterialTheme.typography.bodyMedium,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        DrinkItemIcon(
            backgroundColor,
            icon,
            Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .border(4.dp, color = borderColor, shape = CircleShape)
        )
        Text(
            text = description,
            overflow = TextOverflow.Ellipsis,
            maxLines = 2,
            style = textStyle
        )
    }
}