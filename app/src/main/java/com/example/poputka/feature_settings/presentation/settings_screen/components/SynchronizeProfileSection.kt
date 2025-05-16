package com.example.poputka.feature_settings.presentation.settings_screen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.poputka.R
import com.example.poputka.ui.theme.DpSpSize.paddingMedium
import com.example.poputka.ui.theme.DpSpSize.paddingSmall

@Composable
fun SynchronizeProfileSection(onClick: () -> Unit) {
    val cardShape = MaterialTheme.shapes.large
    val backgroundColor = MaterialTheme.colorScheme.outline
    val fontStyle = MaterialTheme.typography.bodyLarge.copy(backgroundColor)
    val iconColor = MaterialTheme.colorScheme.surfaceContainerHigh

    Column(
        modifier = Modifier
            .clip(cardShape)
            .clickable {
                onClick()
            }
            .padding(horizontal = paddingMedium, vertical = paddingSmall),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(R.drawable.person),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            colorFilter = ColorFilter.tint(iconColor),
            modifier = Modifier
                .padding(bottom = paddingSmall)
                .size(56.dp)
                .clip(CircleShape)
                .background(backgroundColor)
                .padding(6.dp),
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = stringResource(R.string.sync_data), style = fontStyle)
            Icon(
                painter = painterResource(R.drawable.keyboard_arrow_right),
                contentDescription = null,
                tint = backgroundColor
            )
        }

    }
}