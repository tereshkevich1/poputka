package com.example.poputka.feature_settings.presentation.settings_screen.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.poputka.R

@Composable
fun SettingsNavigationRow(
    onClick: () -> Unit,
    settingsIcon: Painter,
    description: String,
    padding: Dp
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
            .clickable { onClick() }
            .padding(padding),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = settingsIcon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.9f)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = description)
        Spacer(modifier = Modifier.weight(1f))
        Icon(
            painter = painterResource(R.drawable.baseline_keyboard_arrow_right_24),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
        )
    }
}


@Composable
fun SettingsInfoRow(
    onClick: () -> Unit,
    settingsIcon: Painter,
    description: String,
    info: String,
    padding: Dp
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(padding)
    ) {
        Icon(
            painter = settingsIcon, contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.9f)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = description)

        Spacer(modifier = Modifier.weight(1f))
        Text(text = info, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f))
    }
}