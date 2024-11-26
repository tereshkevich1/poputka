package com.example.poputka.presentation.canvas.custom_circular_progress_indicator.render

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import kotlin.math.cos
import kotlin.math.sin

internal fun DrawScope.drawCircularProgressIndicator(
    startAngle: Float,
    sweep: Float,
    color: Color,
    indicatorColor: Color,
    stroke: Stroke
) {
    val diameterOffset = stroke.width / 2
    val arcDimen = size.width - 2 * diameterOffset

    drawArc(
        color = color,
        startAngle = startAngle,
        sweepAngle = sweep,
        useCenter = false,
        topLeft = Offset(diameterOffset, diameterOffset),
        size = Size(arcDimen, arcDimen),
        style = stroke
    )

    if (sweep>360f) {
        val radius = arcDimen / 2
        val centerX = diameterOffset + radius
        val centerY = diameterOffset + radius

        // Final angle in radians
        val angle = Math.toRadians((startAngle + sweep).toDouble())

        // Calculating slider coordinates
        val thumbX = centerX + (radius * cos(angle)).toFloat()
        val thumbY = centerY + (radius * sin(angle)).toFloat()

        val m = stroke.width + 6f
        drawArc(
            color = indicatorColor,
            startAngle = 270f + sweep,
            sweepAngle = 180f,
            useCenter = false,
            topLeft = Offset(thumbX - m / 2, thumbY - m / 2),
            size = Size(m, m),
            style = Stroke(width = 6f, cap = StrokeCap.Round)
        )
    }
}