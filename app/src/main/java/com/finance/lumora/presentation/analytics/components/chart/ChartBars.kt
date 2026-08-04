package com.finance.lumora.presentation.analytics.components.chart



import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.unit.dp
import com.finance.lumora.domain.analytics.model.IncomeExpenseSummary
import kotlin.math.max

private val IncomeBarColor = Color(0xFF4CAF50)
private val ExpenseBarColor = Color(0xFFF44336)



internal fun DrawScope.drawGroupedBars(
    monthlyData: List<IncomeExpenseSummary>,
    chartLeft: Float,
    chartTop: Float,
    chartRight: Float,
    chartBottom: Float,
    animationProgress: Float
){

    if (monthlyData.isEmpty()) return

    val chartWidth = chartRight - chartLeft
    val chartHeight = chartBottom - chartTop

    val maxValue = max(
        monthlyData.maxOf { it.income },
        monthlyData.maxOf { it.expense }
    )

    if (maxValue <= 0.0) return

    val groupWidth = chartWidth / monthlyData.size

    val barWidth = groupWidth * 0.16f

    val spacing = groupWidth * 0.14f

    monthlyData.forEachIndexed { index, item ->

        val incomeHeight =
            ((item.income / maxValue) * chartHeight).toFloat()

        val expenseHeight =
            ((item.expense / maxValue) * chartHeight).toFloat()


        val groupStart = chartLeft + (index * groupWidth)

        val totalBarsWidth =
            (barWidth * 2) + spacing

        val startX =
            groupStart +
                    (groupWidth - totalBarsWidth) / 2f

        //----------------------------------
        // Income
        //----------------------------------

        drawRoundRect(
            color = IncomeBarColor,

            topLeft = Offset(
                x = startX,
                y = chartBottom - incomeHeight * animationProgress
            ),

            size = Size(
                width = barWidth,
                height = incomeHeight * animationProgress
            ),

            cornerRadius = CornerRadius(
                x = 6.dp.toPx(),
                y = 6.dp.toPx()
            ),

            style = Fill
        )

        //----------------------------------
        // Expense
        //----------------------------------

        drawRoundRect(
            color = ExpenseBarColor,

            topLeft = Offset(
                x = startX + barWidth + spacing,
                y = chartBottom - expenseHeight * animationProgress
            ),

            size = Size(
                width = barWidth,
                height = expenseHeight * animationProgress
            ),

            cornerRadius = CornerRadius(
                x = 6.dp.toPx(),
                y = 6.dp.toPx()
            ),

            style = Fill
        )

    }

}