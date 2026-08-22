package com.finance.lumora.presentation.dashboard.components



import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.finance.lumora.data.local.enums.TransactionType
import com.finance.lumora.domain.model.TransactionWithCategory
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun RecentTransactionItem(
    transaction: TransactionWithCategory,
    modifier: Modifier = Modifier,
    onClick: (TransactionWithCategory) -> Unit
) {
    val transactionData = transaction.transaction
    val category = transaction.category

    val amountColor = when (transactionData.type) {
        TransactionType.INCOME -> MaterialTheme.colorScheme.primary
        TransactionType.EXPENSE -> MaterialTheme.colorScheme.error
    }

    val amountPrefix = when (transactionData.type) {
        TransactionType.INCOME -> "+"
        TransactionType.EXPENSE -> "-"
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick(transaction) }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            //------------------------------------------
            // Category Icon (Emphasized Visual Anchor)
            //------------------------------------------

            /*
            Text(
                text = category.icon,
                style = MaterialTheme.typography.headlineSmall
            )

             */
            Icon(
                imageVector = getIconFromName(category.icon),
                contentDescription = category.name,
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(32.dp) // Gives it clean, deliberate proportions
            )

            Spacer(modifier = Modifier.width(16.dp))

            //------------------------------------------
            // Transaction Details
            //------------------------------------------
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                Text(
                    text = category.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                // Secondary Info Row (Cleans up vertical space)
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        text = formatDate(transactionData.transactionDate),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    transactionData.note?.takeIf { it.isNotBlank() }?.let { noteText ->
                        Text(
                            text = "•",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = noteText,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.weight(1f, fill = false)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            //------------------------------------------
            // Financial Amount
            //------------------------------------------
            Text(
                text = amountPrefix + formatCurrency(transactionData.amount),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = amountColor,
                maxLines = 1
            )
        }

        HorizontalDivider(
            modifier = Modifier.padding(horizontal = 16.dp),
            color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f)
        )
    }
}

private fun formatCurrency(amount: Double): String {
    return NumberFormat
        .getCurrencyInstance(Locale("en", "IN"))
        .format(amount)
}

private fun formatDate(timestamp: Long): String {
    return SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(Date(timestamp))
}

private fun getIconFromName(iconName: String): ImageVector {
    return when (iconName) {
        "Filled.ShoppingCart" -> Icons.Default.ShoppingCart
        "Filled.Home" -> Icons.Default.Home
        // Add the other string mappings you save in your database here...
        else -> Icons.Default.List // Fallback icon so your app never crashes
    }
}