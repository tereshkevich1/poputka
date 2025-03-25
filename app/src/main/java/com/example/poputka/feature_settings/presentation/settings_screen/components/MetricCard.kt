package com.example.poputka.feature_settings.presentation.settings_screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.poputka.R

@Composable
fun MetricCard(icon: Painter, iconColor: Color, value: String, label: String, modifier: Modifier) {
    val paddingMedium = dimensionResource(R.dimen.padding_medium)
    val paddingSmall = dimensionResource(R.dimen.padding_small)
    val headlineFontStyle = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
    val titleColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
    val titleFontStyle = MaterialTheme.typography.titleSmall.copy(titleColor)
    val cardShape = MaterialTheme.shapes.large
    val cardColor = MaterialTheme.colorScheme.surfaceContainerHigh
    Column(
        modifier = modifier
            .fillMaxWidth(1f)
            .clip(cardShape)
            .background(cardColor)
            .padding(paddingMedium),
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = icon,
            contentDescription = null,
            tint = iconColor,
            modifier = Modifier.size(26.dp),
        )
        Text(
            text = value,
            style = headlineFontStyle,
            modifier = Modifier.padding(top = paddingSmall, bottom = paddingSmall)
        )
        Text(text = label, style = titleFontStyle)
    }
}