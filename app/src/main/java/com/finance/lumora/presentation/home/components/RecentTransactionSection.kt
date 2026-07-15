package com.finance.lumora.presentation.home.components



import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.finance.lumora.presentation.home.model.TransactionUi

@Composable
fun RecentTransactionSection(

    modifier: Modifier = Modifier,

    onSeeAllClick: () -> Unit = {},

    onTransactionClick: (TransactionUi) -> Unit = {}

) {

    val transactions = listOf(

        TransactionUi(
            id = 1,
            title = "Shopping",
            category = "Shopping",
            amount = "- ₹450",
            date = "Today • 10:30 AM",
            icon = Icons.Default.ShoppingCart,
            iconBackground = Color(0xFF42A5F5),
            amountColor = Color(0xFFE53935),
            isIncome = false
        ),

        TransactionUi(
            id = 2,
            title = "Food",
            category = "Food",
            amount = "- ₹220",
            date = "Yesterday • 8:10 PM",
            icon = Icons.Default.Fastfood,
            iconBackground = Color(0xFFFF9800),
            amountColor = Color(0xFFE53935),
            isIncome = false
        ),

        TransactionUi(
            id = 3,
            title = "Salary",
            category = "Salary",
            amount = "+ ₹25,000",
            date = "01 Jul • 09:00 AM",
            icon = Icons.Default.AccountBalanceWallet,
            iconBackground = Color(0xFF43A047),
            amountColor = Color(0xFF2E7D32),
            isIncome = true
        ),

        TransactionUi(
            id = 4,
            title = "Rent",
            category = "Home",
            amount = "- ₹8,000",
            date = "30 Jun • 06:00 PM",
            icon = Icons.Default.Home,
            iconBackground = Color(0xFF7E57C2),
            amountColor = Color(0xFFE53935),
            isIncome = false
        )

    )

    Column(
        modifier = modifier.fillMaxWidth()
    ) {

        //--------------------------------------------------
        // Header
        //--------------------------------------------------

        androidx.compose.foundation.layout.Row(

            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),

            horizontalArrangement = Arrangement.SpaceBetween

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
        // List
        //--------------------------------------------------

        Column(

            modifier = Modifier.padding(top = 8.dp),

            verticalArrangement = Arrangement.spacedBy(12.dp)

        ) {

            transactions.forEach { transaction ->

                TransactionItem(

                    modifier = Modifier.padding(horizontal = 20.dp),

                    transaction = transaction,

                    onClick = {

                        onTransactionClick(transaction)

                    }

                )

            }

        }

    }

}