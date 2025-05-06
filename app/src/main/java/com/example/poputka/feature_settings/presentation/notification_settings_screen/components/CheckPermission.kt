@file:OptIn(ExperimentalPermissionsApi::class)

package com.example.poputka.feature_settings.presentation.notification_settings_screen.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.poputka.R
import com.example.poputka.ui.theme.DpSpSize
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.MultiplePermissionsState
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


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CheckMultiplyPermission(multiplePermissionsState: MultiplePermissionsState) {
    if (multiplePermissionsState.allPermissionsGranted) {
        // If all permissions are granted, then show screen with the feature enabled
        Text("Camera and Read storage permissions Granted! Thank you!")
    } else {
        Column {
            Text(
                getTextToShowGivenPermissions(
                    multiplePermissionsState.revokedPermissions,
                    multiplePermissionsState.shouldShowRationale
                )
            )
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = { multiplePermissionsState.launchMultiplePermissionRequest() }) {
                Text("Request permissions")
            }
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
fun getTextToShowGivenPermissions(
    permissions: List<PermissionState>,
    shouldShowRationale: Boolean
): String {
    val revokedPermissionsSize = permissions.size
    if (revokedPermissionsSize == 0) return ""

    val textToShow = StringBuilder().apply {
        append("The ")
    }

    for (i in permissions.indices) {
        textToShow.append(permissions[i].permission)
        when {
            revokedPermissionsSize > 1 && i == revokedPermissionsSize - 2 -> {
                textToShow.append(", and ")
            }

            i == revokedPermissionsSize - 1 -> {
                textToShow.append(" ")
            }

            else -> {
                textToShow.append(", ")
            }
        }
    }
    textToShow.append(if (revokedPermissionsSize == 1) "permission is" else "permissions are")
    textToShow.append(
        if (shouldShowRationale) {
            " important. Please grant all of them for the app to function properly."
        } else {
            " denied. The app cannot function without them."
        }
    )
    return textToShow.toString()
}