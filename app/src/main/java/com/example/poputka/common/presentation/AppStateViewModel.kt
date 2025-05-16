package com.example.poputka.common.presentation

import androidx.lifecycle.ViewModel
import com.example.poputka.common.domain.AppStateHolder
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AppStateViewModel @Inject constructor(appStateHolder: AppStateHolder) : ViewModel() {
    val appPrefFlow = appStateHolder.appPreferencesStateHolder.appPrefFlow
}