package com.finance.lumora.presentation.analytics.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.finance.lumora.domain.analytics.model.MonthlySummary
import com.finance.lumora.presentation.home.model.SummaryItem
import java.text.NumberFormat

@Composable
fun MonthlySummaryCard(
    summary: MonthlySummary,
    modifier: Modifier = Modifier
) {

    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {

        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Text(
                text = "Monthly Summary",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(
                modifier = Modifier.height(20.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                SummaryItem(
                    title = "Income",
                    value = summary.totalIncome,
                    valueColor = MaterialTheme.colorScheme.primary
                )

                SummaryItem(
                    title = "Expense",
                    value = summary.totalExpense,
                    valueColor = MaterialTheme.colorScheme.primary
                )

                val balanceColor = when {
                    summary.balance > 0 -> MaterialTheme.colorScheme.primary
                    summary.balance < 0 -> MaterialTheme.colorScheme.error
                    else -> MaterialTheme.colorScheme.onSurface
                }
                SummaryItem(
                    title = "Balance",
                    value = summary.balance,
                    valueColor = balanceColor
                )
            }

            HorizontalDivider(
                modifier = Modifier.padding(vertical = 16.dp)
            )

            Text(
                text = "Transactions: ${summary.transactionCount}",
                style = MaterialTheme.typography.bodyMedium
            )

        }

    }

}

@Composable
private fun SummaryItem(
    title: String,
    value: Double,
    valueColor: androidx.compose.ui.graphics.Color
){

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = title,
            style = MaterialTheme.typography.labelMedium
        )

        Spacer(
            modifier = Modifier.height(6.dp)
        )

        Text(
            text = value.toCurrency(),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )

    }

}


