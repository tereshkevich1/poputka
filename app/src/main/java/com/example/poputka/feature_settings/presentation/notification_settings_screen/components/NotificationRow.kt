package com.example.poputka.feature_settings.presentation.notification_settings_screen.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.poputka.R
import com.example.poputka.feature_settings.presentation.notification_settings_screen.models.NotificationUi
import com.example.poputka.ui.theme.DpSpSize

@Composable
fun NotificationRow(
    notification: NotificationUi,
    painter: Painter,
    onSwitchClick: (Boolean) -> Unit,
    onEditClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
            .padding(horizontal = DpSpSize.paddingMedium),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(notification.titleResId),
            style = MaterialTheme.typography.bodyLarge,
            color = if (!notification.isEnabled) MaterialTheme.colorScheme.outline
            else MaterialTheme.colorScheme.onSurface
        )
        Spacer(modifier = Modifier.weight(1f))

        if (notification.isEnabled) {
            TextButton(onClick = onEditClick) {
                Text(
                    text = notification.time.formatted,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.outline
                )
                Spacer(modifier = Modifier.width(DpSpSize.paddingSmall))

                Icon(
                    painter = painter,
                    contentDescription = stringResource(R.string.edit_icon),
                    tint = MaterialTheme.colorScheme.outline
                )
            }
            Spacer(modifier = Modifier.width(DpSpSize.paddingMedium))
        }

        Switch(
            checked = notification.isEnabled,
            onCheckedChange = { onSwitchClick(it) },
            modifier = Modifier
        )
    }
}