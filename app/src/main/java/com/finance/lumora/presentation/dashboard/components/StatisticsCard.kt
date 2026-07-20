package com.finance.lumora.presentation.dashboard.components


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
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
import java.text.NumberFormat
import java.util.Locale

@Composable
fun StatisticsCard(

    transactionCount: Int,

    largestIncome: Double,

    largestExpense: Double,

    modifier: Modifier = Modifier

) {

    Card(

        modifier = modifier.fillMaxWidth(),

        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),

        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerHigh
        )

    ) {

        Column(

            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)

        ) {

            Text(

                text = "Statistics",

                style = MaterialTheme.typography.titleLarge,

                fontWeight = FontWeight.Bold

            )

            HorizontalDivider(
                modifier = Modifier.padding(vertical = 16.dp)
            )

            StatisticItem(

                title = "Transactions",

                value = transactionCount.toString()

            )

            HorizontalDivider()

            StatisticItem(

                title = "Largest Income",

                value = formatCurrency(largestIncome)

            )

            HorizontalDivider()

            StatisticItem(

                title = "Largest Expense",

                value = formatCurrency(largestExpense)

            )

        }

    }

}

@Composable
private fun StatisticItem(

    title: String,

    value: String

) {

    Row(

        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 14.dp),

        horizontalArrangement = Arrangement.SpaceBetween,

        verticalAlignment = Alignment.CenterVertically

    ) {

        Text(

            text = title,

            style = MaterialTheme.typography.bodyLarge,

            color = MaterialTheme.colorScheme.onSurfaceVariant

        )

        Text(

            text = value,

            style = MaterialTheme.typography.titleMedium,

            fontWeight = FontWeight.SemiBold,

            color = MaterialTheme.colorScheme.onSurface

        )

    }

}

private fun formatCurrency(

    amount: Double

): String {

    return NumberFormat
        .getCurrencyInstance(Locale("en", "IN"))
        .format(amount)

}