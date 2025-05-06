@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)

package com.example.poputka.feature_settings.presentation.notification_settings_screen

import android.Manifest
import android.os.Build
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.example.poputka.R
import com.example.poputka.common.presentation.components.TimePickerModule
import com.example.poputka.feature_settings.presentation.notification_settings_screen.components.CheckMultiplyPermission
import com.example.poputka.feature_settings.presentation.notification_settings_screen.components.CheckPermission
import com.example.poputka.feature_settings.presentation.notification_settings_screen.components.NotificationRow
import com.example.poputka.ui.theme.DpSpSize.paddingMedium
import com.example.poputka.ui.theme.DpSpSize.paddingSmall
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.accompanist.permissions.rememberPermissionState
import kotlinx.coroutines.launch

@ExperimentalMaterial3Api
@Composable
fun NotificationSettingsRoute(
    onNavigateToSettingsScreen: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: NotificationViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current

    val lifecycleOwner = LocalLifecycleOwner.current
    LaunchedEffect(Unit) {
        lifecycleOwner.lifecycleScope.launch {
            viewModel.events.flowWithLifecycle(lifecycleOwner.lifecycle, Lifecycle.State.STARTED)
                .collect {
                    when (it) {
                        NotificationScreenEvent.NavigateToSettingsScreen -> onNavigateToSettingsScreen()
                        is NotificationScreenEvent.ShowToast -> Toast.makeText(
                            context,
                            it.message,
                            Toast.LENGTH_LONG
                        )
                    }
                }
        }
    }

    NotificationSettingsScreen(
        state = state,
        onAction = viewModel::onAction,
        modifier = modifier
    )
}


@Composable
fun NotificationSettingsScreen(
    state: NotificationSettingsState,
    onAction: (NotificationScreenAction) -> Unit,
    modifier: Modifier
) {

    val notificationPermissionState = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        rememberPermissionState(Manifest.permission.POST_NOTIFICATIONS)
    } else {
        null
    }

    val permissionIsGranted = notificationPermissionState?.status?.isGranted ?: true

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.title_notification_settings)) },
                navigationIcon = {
                    IconButton(onClick = { onAction(NotificationScreenAction.OnBackClick) })
                    {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            stringResource(R.string.back_icon)
                        )
                    }
                },
                actions = {
                    Switch(
                        checked = state.isNotificationEnabled,
                        onCheckedChange = {
                            onAction(NotificationScreenAction.OnNotificationSettingsToggle(it))
                        },
                        modifier = Modifier.padding(horizontal = paddingMedium)
                    )
                }
            )
        }
    ) { innerPadding ->

        val painter = painterResource(R.drawable.edit)

        Column(
            modifier = modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {


            val multiplePermissionsState = rememberMultiplePermissionsState(
                listOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                )
            )
            CheckMultiplyPermission(multiplePermissionsState)





            notificationPermissionState?.let {
                CheckPermission(notificationPermissionState)
            }

            Text(
                text = stringResource(R.string.notification_settings_description),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.outline,
                modifier = Modifier
                    .padding(horizontal = paddingMedium, vertical = paddingSmall)
            )

            if (permissionIsGranted && state.isNotificationEnabled) {
                LazyColumn(modifier = Modifier) {
                    items(
                        items = state.notifications,
                        key = { notification -> notification.id }
                    ) { notification ->
                        NotificationRow(
                            notification = notification,
                            painter = painter,
                            onSwitchClick = { isEnabled ->
                                onAction(
                                    NotificationScreenAction.OnNotificationToggle(
                                        notification,
                                        isEnabled
                                    )
                                )
                            },
                            onEditClick = {
                                onAction(
                                    NotificationScreenAction.OnNotificationClick(
                                        notification
                                    )
                                )
                            }
                        )
                    }
                }
            }
        }
    }

    if (state.showTimePicker) {
        TimePickerModule(
            selectedTime = System.currentTimeMillis(),
            onDismiss = { onAction(NotificationScreenAction.OnTimePickerDismissed) },
            onConfirm = { onAction(NotificationScreenAction.OnTimeSelected(it.hour, it.minute)) }
        )
    }
}

