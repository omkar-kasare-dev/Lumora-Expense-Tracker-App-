package com.finance.lumora.presentation.dashboard.components



import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Row
import androidx.compose.ui.Alignment
import com.finance.lumora.domain.model.TransactionWithCategory

@Composable
fun RecentTransactionsSection(

    recentTransactions: List<TransactionWithCategory>,

    modifier: Modifier = Modifier,

    onSeeAllClick: () -> Unit,

    onTransactionClick: (TransactionWithCategory) -> Unit

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

            modifier = Modifier.padding(16.dp)

        ) {

            //--------------------------------------------------
            // Header
            //--------------------------------------------------

            Row(

                modifier = Modifier.fillMaxWidth(),

                horizontalArrangement = Arrangement.SpaceBetween,

                verticalAlignment = Alignment.CenterVertically

            ) {

                Text(

                    text = "Recent Transactions",

                    style = MaterialTheme.typography.titleLarge,

                    fontWeight = FontWeight.Bold

                )

                TextButton(

                    onClick = onSeeAllClick

                ) {

                    Text("See All")

                }

            }

            //--------------------------------------------------
            // Empty State
            //--------------------------------------------------

            if (recentTransactions.isEmpty()) {

                EmptyRecentTransactions()

            } else {

                //--------------------------------------------------
                // Transaction List
                //--------------------------------------------------

                LazyColumn(

                    modifier = Modifier.heightIn(max = 500.dp),

                    verticalArrangement = Arrangement.spacedBy(4.dp)

                ) {

                    items(

                        items = recentTransactions,

                        key = {

                            it.transaction.id

                        }

                    ) { transaction ->

                        RecentTransactionItem(

                            transaction = transaction,

                            onClick = onTransactionClick

                        )

                    }

                }

            }

        }

    }

}

@Composable
private fun EmptyRecentTransactions() {

    Column(

        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 32.dp),

        horizontalAlignment = Alignment.CenterHorizontally

    ) {

        Text(

            text = "No recent transactions",

            style = MaterialTheme.typography.titleMedium,

            fontWeight = FontWeight.SemiBold

        )

        Text(

            text = "Start by adding your first transaction.",

            style = MaterialTheme.typography.bodyMedium,

            color = MaterialTheme.colorScheme.onSurfaceVariant

        )

    }

}