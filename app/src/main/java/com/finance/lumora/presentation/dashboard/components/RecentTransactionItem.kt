package com.finance.lumora.presentation.dashboard.components




import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.finance.lumora.data.local.enums.TransactionType
import com.finance.lumora.domain.model.TransactionWithCategory
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun RecentTransactionItem(
    transaction: TransactionWithCategory,
    modifier: Modifier = Modifier,
    onClick: (TransactionWithCategory) -> Unit
) {
    val transactionData = transaction.transaction
    val category = transaction.category

    val isIncome = transactionData.type == TransactionType.INCOME

    val amountColor = if (isIncome) {
        Color(0xFF10B981) // Refined Emerald Green for Income
    } else {
        MaterialTheme.colorScheme.onSurface // Clean native neutral for Expenses (or standard error)
    }

    val iconBgColor = if (isIncome) {
        Color(0xFF10B981).copy(alpha = 0.12f)
    } else {
        MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f)
    }

    val iconTint = if (isIncome) {
        Color(0xFF059669)
    } else {
        MaterialTheme.colorScheme.onSurfaceVariant
    }

    val amountPrefix = if (isIncome) "+" else "-"

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .clickable { onClick(transaction) },
        color = Color.Transparent
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                //------------------------------------------
                // Category Icon Container (Compact & Professional)
                //------------------------------------------
                Box(
                    modifier = Modifier
                        .size(42.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(iconBgColor),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = getIconFromName(category.icon),
                        contentDescription = category.name,
                        tint = iconTint,
                        modifier = Modifier.size(20.dp)
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                //------------------------------------------
                // Transaction Details (Minimal Typography)
                //------------------------------------------
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(1.dp)
                ) {
                    Text(
                        text = category.name,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontSize = 13.5.sp,
                            letterSpacing = 0.1.sp
                        ),
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurface,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            text = formatDate(transactionData.transactionDate),
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontSize = 11.sp,
                                letterSpacing = 0.2.sp
                            ),
                            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.65f)
                        )

                        transactionData.note?.takeIf { it.isNotBlank() }?.let { noteText ->
                            Text(
                                text = "•",
                                style = MaterialTheme.typography.labelMedium.copy(fontSize = 10.sp),
                                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.4f)
                            )
                            Text(
                                text = noteText,
                                style = MaterialTheme.typography.labelMedium.copy(
                                    fontSize = 11.sp,
                                    letterSpacing = 0.1.sp
                                ),
                                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.65f),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier.weight(1f, fill = false)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.width(12.dp))

                //------------------------------------------
                // Financial Amount
                //------------------------------------------
                Text(
                    text = "$amountPrefix${formatCurrency(transactionData.amount)}",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontSize = 14.sp,
                        letterSpacing = (-0.2).sp
                    ),
                    fontWeight = FontWeight.SemiBold,
                    color = amountColor,
                    maxLines = 1
                )
            }

            HorizontalDivider(
                modifier = Modifier.padding(start = 68.dp, end = 14.dp),
                thickness = 0.6.dp,
                color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.25f)
            )
        }
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
        else -> Icons.Default.List
    }
}