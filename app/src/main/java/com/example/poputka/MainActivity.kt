package com.example.poputka

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.poputka.common.presentation.HydroApp
import dagger.hilt.android.AndroidEntryPoint

/**
 * MainActivity — the main entry point of the application.
 *
 * Changes related to `BottomNavBar`:
 * 1. Removed the automatic bottom padding (`paddingValues`) in the `Scaffold` to ensure
 *    main content is displayed under the navigation bar without overlapping.
 * 2. Defined the BottomNavBar height in `UiConstants.BottomNavBarHeight` for consistent usage.
 * 3. Added manual padding where necessary using `Spacer` or `Modifier.padding`.
 *
 * These changes improve the UI structure and prevent conflicts between the main content
 * and the BottomNavBar.
 */

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

       //val request = OneTimeWorkRequestBuilder<DailyHydrationWorker>().build()
        //WorkManager.getInstance(this).enqueue(request)

        enableEdgeToEdge()
        setContent {
            HydroApp()
        }
    }
}




