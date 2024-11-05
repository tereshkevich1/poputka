package com.example.poputka.presentation.canvas.common

import android.content.Context
import android.util.TypedValue

object MetricUtils {
    fun dpToFloat(context: Context, dp: Float): Float {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp,
            context.resources.displayMetrics
        )
    }
}