package com.finance.lumora.presentation.analytics.components.chart


import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.DrawScope

internal fun DrawScope.drawChartGrid(
    left: Float,
    top: Float,
    right: Float,
    bottom: Float
) {

    val spacing =
        (bottom - top) / ChartConstants.GridLineCount

    repeat(ChartConstants.GridLineCount + 1) { index ->

        val y = bottom - (spacing * index)

        drawLine(

            color = if (index == 0) {

                // Bottom baseline
                ChartColors.Grid.copy(alpha = 0.35f)

            } else {

                // Horizontal grid lines
                ChartColors.Grid.copy(alpha = 0.25f)

            },

            start = Offset(left, y),

            end = Offset(right, y),

            strokeWidth = if (index == 0) 1.6f else 0.8f

        )

    }

}