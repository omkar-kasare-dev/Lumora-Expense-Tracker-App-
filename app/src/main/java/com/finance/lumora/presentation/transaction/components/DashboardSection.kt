package com.finance.lumora.presentation.transaction.components



import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.material3.MaterialTheme

/**
 * Displays the transaction dashboard.
 *
 * Shows:
 * - Total Income
 * - Total Expense
 * - Current Balance
 */
@Composable
fun DashboardSection(

    totalIncome: Double,

    totalExpense: Double,

    modifier: Modifier = Modifier

) {

    val currentBalance = totalIncome - totalExpense

    Column(

        modifier = modifier.fillMaxWidth(),

        verticalArrangement = Arrangement.spacedBy(12.dp)

    ) {

        TransactionSummaryCard(

            title = "Total Income",

            amount = totalIncome,

            amountColor = Color(0xFF2E7D32)

        )

        TransactionSummaryCard(

            title = "Total Expense",

            amount = totalExpense,

            amountColor = Color(0xFFC62828)

        )

        TransactionSummaryCard(

            title = "Current Balance",

            amount = currentBalance,

            amountColor = MaterialTheme.colorScheme.primary

        )

    }

}