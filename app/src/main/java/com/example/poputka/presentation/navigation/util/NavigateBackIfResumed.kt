package com.example.poputka.presentation.navigation.util

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController

fun NavController.navigateBackIfResumed(
    lifecycleOwner: LifecycleOwner
) {
    if (lifecycleOwner.lifecycle.currentState.isAtLeast(Lifecycle.State.RESUMED)) {
        this.popBackStack()
    }
}