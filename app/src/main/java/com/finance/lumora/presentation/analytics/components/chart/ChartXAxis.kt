package com.finance.lumora.presentation.analytics.components.chart

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.finance.lumora.domain.analytics.model.IncomeExpenseSummary
import java.time.Month
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun ChartXAxis(
    monthlyData: List<IncomeExpenseSummary>,
    modifier: Modifier = Modifier
) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 8.dp),

        horizontalArrangement = Arrangement.SpaceBetween,

        verticalAlignment = Alignment.CenterVertically

    ) {

        monthlyData.forEach { item ->

            Text(

                modifier = Modifier.weight(1f),

                text = item.month.toMonthName(),

                textAlign = TextAlign.Center,

                style = MaterialTheme.typography.labelSmall,

                fontWeight = FontWeight.Medium,

                color = MaterialTheme.colorScheme.onSurfaceVariant

            )

        }

    }

}

private fun Int.toMonthName(): String {

    return Month
        .of(this)
        .getDisplayName(
            TextStyle.SHORT,
            Locale.getDefault()
        )

}