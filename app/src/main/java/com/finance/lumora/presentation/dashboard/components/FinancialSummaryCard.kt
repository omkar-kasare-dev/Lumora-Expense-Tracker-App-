package com.finance.lumora.presentation.dashboard.components



import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
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
fun FinancialSummaryCard(

    monthlyIncome: Double,

    monthlyExpense: Double,

    modifier: Modifier = Modifier

) {

    val monthlySavings = monthlyIncome - monthlyExpense

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

                text = "This Month",

                style = MaterialTheme.typography.titleLarge,

                fontWeight = FontWeight.Bold

            )

            Spacer(
                modifier = Modifier.height(20.dp)
            )

            SummaryRow(

                icon = {

                    Icon(

                        imageVector = Icons.Default.ArrowUpward,

                        contentDescription = "Monthly Income"

                    )

                },

                title = "Income",

                amount = monthlyIncome

            )

            Divider()

            SummaryRow(

                icon = {

                    Icon(

                        imageVector = Icons.Default.ArrowDownward,

                        contentDescription = "Monthly Expense"

                    )

                },

                title = "Expense",

                amount = monthlyExpense

            )

            Divider()

            SummaryRow(

                icon = {

                    Icon(

                        imageVector = Icons.Default.AccountBalanceWallet,

                        contentDescription = "Monthly Savings"

                    )

                },

                title = "Savings",

                amount = monthlySavings

            )

        }

    }

}

@Composable
private fun SummaryRow(

    icon: @Composable () -> Unit,

    title: String,

    amount: Double

) {

    Row(

        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 14.dp),

        horizontalArrangement = Arrangement.SpaceBetween,

        verticalAlignment = Alignment.CenterVertically

    ) {

        Row(

            verticalAlignment = Alignment.CenterVertically

        ) {

            icon()

            Spacer(
                modifier = Modifier.height(0.dp)
            )

            Text(

                text = title,

                modifier = Modifier.padding(start = 12.dp),

                style = MaterialTheme.typography.bodyLarge

            )

        }

        Text(

            text = formatCurrency(amount),

            style = MaterialTheme.typography.titleMedium,

            fontWeight = FontWeight.SemiBold

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