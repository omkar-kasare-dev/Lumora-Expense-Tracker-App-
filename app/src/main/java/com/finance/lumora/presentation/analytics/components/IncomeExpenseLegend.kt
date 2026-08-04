package com.finance.lumora.presentation.analytics.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.finance.lumora.presentation.analytics.components.chart.ChartColors

private val IncomeColor = ChartColors.Income
private val ExpenseColor = ChartColors.Expense

@Composable
fun IncomeExpenseLegend(
    modifier: Modifier = Modifier
) {

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(24.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        LegendItem(
            color = IncomeColor,
            title = "Income"
        )

        LegendItem(
            color = ExpenseColor,
            title = "Expense"
        )

    }

}

@Composable
private fun LegendItem(
    color: Color,
    title: String
) {

    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Box(
            modifier = Modifier
                .size(12.dp)
                .background(
                    color = color,
                    shape = CircleShape
                )
        )

        Text(
            text = title,
            style = MaterialTheme.typography.bodyMedium
        )

    }

}