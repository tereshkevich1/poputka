package com.example.poputka.presentation.profile.profile_screen.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import com.example.poputka.R

@Composable
fun SettingsRowItem(onClick: () -> Unit, settingsIcon: Painter, description: String, padding: Dp) {
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
        Spacer(modifier = Modifier.width(padding))
        Text(text = description)
        Spacer(modifier = Modifier.weight(1f))
        Icon(
            painter = painterResource(R.drawable.baseline_keyboard_arrow_right_24),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
        )
    }
}