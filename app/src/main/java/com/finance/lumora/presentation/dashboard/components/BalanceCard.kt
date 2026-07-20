package com.finance.lumora.presentation.dashboard.components



import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
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
fun BalanceCard(

    totalBalance: Double,

    totalIncome: Double,

    totalExpense: Double,

    modifier: Modifier = Modifier

) {

    Card(

        modifier = modifier
            .fillMaxWidth(),

        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),

        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )

    ) {

        Column(

            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),

            horizontalAlignment = Alignment.CenterHorizontally

        ) {

            //------------------------------------------
            // Title
            //------------------------------------------

            Text(

                text = "Total Balance",

                style = MaterialTheme.typography.titleMedium,

                color = MaterialTheme.colorScheme.onPrimaryContainer

            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            //------------------------------------------
            // Balance
            //------------------------------------------

            Text(

                text = formatCurrency(totalBalance),

                style = MaterialTheme.typography.headlineMedium,

                fontWeight = FontWeight.Bold,

                color = MaterialTheme.colorScheme.onPrimaryContainer

            )

            Spacer(
                modifier = Modifier.height(20.dp)
            )

            Divider()

            Spacer(
                modifier = Modifier.height(16.dp)
            )

            //------------------------------------------
            // Income & Expense
            //------------------------------------------

            Row(

                modifier = Modifier.fillMaxWidth(),

                horizontalArrangement = Arrangement.SpaceEvenly

            ) {

                SummaryItem(

                    title = "Income",

                    amount = totalIncome

                )

                SummaryItem(

                    title = "Expense",

                    amount = totalExpense

                )

            }

        }

    }

}

@Composable
private fun SummaryItem(

    title: String,

    amount: Double

) {

    Column(

        horizontalAlignment = Alignment.CenterHorizontally

    ) {

        Text(

            text = title,

            style = MaterialTheme.typography.bodyMedium

        )

        Spacer(
            modifier = Modifier.height(4.dp)
        )

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

    val formatter = NumberFormat.getCurrencyInstance(
        Locale("en", "IN")
    )

    return formatter.format(amount)

}