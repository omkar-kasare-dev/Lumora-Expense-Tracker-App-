package com.finance.lumora.presentation.analytics.components


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.finance.lumora.domain.analytics.model.IncomeExpenseSummary

@Composable
fun IncomeExpenseCard(
    monthlyData: List<IncomeExpenseSummary>,
    modifier: Modifier = Modifier
) {

    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {

        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            Text(
                text = "Income vs Expense",
                style = MaterialTheme.typography.titleMedium
            )

            HorizontalDivider()

            if (monthlyData.isEmpty()) {

                EmptyIncomeExpense()

            } else {

                IncomeExpenseChart(
                    monthlyData = monthlyData
                )

                HorizontalDivider()

                IncomeExpenseLegend(
                    modifier = Modifier.fillMaxWidth()
                )

            }

        }

    }

}