package com.example.poputka.feature_weather.domain.use_case

import android.util.Log
import com.example.poputka.common.domain.AppStateHolder
import javax.inject.Inject

class SaveHydrationGoalUseCase @Inject constructor(
    private val calculateDailyHydrationNormUseCase: CalculateDailyHydrationNormUseCase,
    private val appStateHolder: AppStateHolder
) {
    suspend operator fun invoke() {
        val result = calculateDailyHydrationNormUseCase() ?: return
        appStateHolder.appPreferencesStateHolder.saveGoal(result)
        Log.d("HydrationCalc", "Saved hydration goal: $result")
    }
}




