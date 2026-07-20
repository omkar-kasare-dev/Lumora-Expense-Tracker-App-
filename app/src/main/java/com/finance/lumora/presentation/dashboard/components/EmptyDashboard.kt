package com.finance.lumora.presentation.dashboard.components



import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountBalanceWallet
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight

@Composable
fun EmptyDashboard(

    onAddTransaction: () -> Unit,

    modifier: Modifier = Modifier

) {

    Column(

        modifier = modifier.fillMaxSize(),

        verticalArrangement = Arrangement.Center,

        horizontalAlignment = Alignment.CenterHorizontally

    ) {

        Icon(
            imageVector = Icons.Outlined.AccountBalanceWallet,
            contentDescription = null
        )

        Text(
            text = "No Transactions Yet",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "Add your first income or expense to begin tracking your finances.",
            style = MaterialTheme.typography.bodyMedium
        )

        Button(
            onClick = onAddTransaction
        ) {

            Text("Add Transaction")

        }

    }

}