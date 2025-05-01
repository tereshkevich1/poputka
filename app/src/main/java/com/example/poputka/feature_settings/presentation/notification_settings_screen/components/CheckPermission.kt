@file:OptIn(ExperimentalPermissionsApi::class)

package com.example.poputka.feature_settings.presentation.notification_settings_screen.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.poputka.R
import com.example.poputka.ui.theme.DpSpSize
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.shouldShowRationale

@Composable
fun CheckPermission(notificationPermissionState: PermissionState) {
    if (!notificationPermissionState.status.isGranted) {
        val textToShow = if (notificationPermissionState.status.shouldShowRationale) {
            stringResource(R.string.notification_permission_should_show_rationale)
        } else {
            stringResource(R.string.notification_permission_shouldnt_ShowRationale)
        }

        Text(
            text = textToShow,
            modifier = Modifier.padding(
                horizontal = DpSpSize.paddingMedium,
                vertical = DpSpSize.paddingSmall
            ),
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onErrorContainer
        )
        Button(
            onClick = { notificationPermissionState.launchPermissionRequest() },
            modifier = Modifier.padding(horizontal = DpSpSize.paddingMedium)
        ) {
            Text(stringResource(R.string.request_permission))
        }
    }
}