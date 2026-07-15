package com.finance.lumora.presentation.home.components



import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.finance.lumora.presentation.home.model.SummaryItem

@Composable
fun SummarySection(

    modifier: Modifier = Modifier

) {

    val summaryItems = listOf(

        SummaryItem(

            title = "Income",

            amount = "₹ 3,450",

            subtitle = "2 transactions",

            icon = Icons.Default.ArrowUpward,

            iconBackground = Color(0xFFDDF6E5),

            amountColor = Color(0xFF2EAD55)

        ),

        SummaryItem(

            title = "Expense",

            amount = "₹ 2,150",

            subtitle = "3 transactions",

            icon = Icons.Default.ArrowDownward,

            iconBackground = Color(0xFFFFE4E4),

            amountColor = Color(0xFFE53935)

        ),

        SummaryItem(

            title = "Balance",

            amount = "₹ 1,300",

            subtitle = "as of today",

            icon = Icons.Default.AccountBalanceWallet,

            iconBackground = Color(0xFFE9E1FF),

            amountColor = Color(0xFF6C4CE3)

        )

    )

    Column(

        modifier = modifier

    ) {

        Text(

            text = "Today's Summary",

            style = MaterialTheme.typography.titleLarge

        )

        Row(

            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),

            horizontalArrangement = Arrangement.spacedBy(12.dp)

        ) {

            summaryItems.forEach { item ->

                SummaryCard(

                    item = item,

                    modifier = Modifier
                        .weight(1f)
                        .height(170.dp)

                )

            }

        }

    }

}