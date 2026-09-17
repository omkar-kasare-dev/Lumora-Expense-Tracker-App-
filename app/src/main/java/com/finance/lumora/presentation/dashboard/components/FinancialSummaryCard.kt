package com.finance.lumora.presentation.dashboard.components




import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.NumberFormat
import java.util.Locale

@Composable
fun FinancialSummaryCard(
    monthlyIncome: Double,
    monthlyExpense: Double,
    modifier: Modifier = Modifier
) {
    val monthlySavings = monthlyIncome - monthlyExpense

    OutlinedCard(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        border = BorderStroke(
            width = 0.8.dp,
            color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.35f)
        ),
        colors = CardDefaults.outlinedCardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerLow
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
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
                    text = "Financial Overview",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontSize = 15.sp,
                        letterSpacing = (-0.1).sp
                    ),
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f)
                ) {
                    Text(
                        text = "This Month",
                        style = MaterialTheme.typography.labelMedium.copy(fontSize = 10.sp),
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            //--------------------------------------------------
            // Summary Metrics
            //--------------------------------------------------
            SummaryRow(
                icon = {
                    Icon(
                        imageVector = Icons.Default.ArrowUpward,
                        contentDescription = "Monthly Income",
                        tint = Color(0xFF059669),
                        modifier = Modifier.size(16.dp)
                    )
                },
                iconContainerColor = Color(0xFF10B981).copy(alpha = 0.12f),
                title = "Income",
                amount = monthlyIncome,
                amountColor = Color(0xFF10B981)
            )

            HorizontalDivider(
                modifier = Modifier.padding(start = 44.dp),
                thickness = 0.6.dp,
                color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.25f)
            )

            SummaryRow(
                icon = {
                    Icon(
                        imageVector = Icons.Default.ArrowDownward,
                        contentDescription = "Monthly Expense",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(16.dp)
                    )
                },
                iconContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f),
                title = "Expense",
                amount = monthlyExpense,
                amountColor = MaterialTheme.colorScheme.onSurface
            )

            HorizontalDivider(
                modifier = Modifier.padding(start = 44.dp),
                thickness = 0.6.dp,
                color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.25f)
            )

            SummaryRow(
                icon = {
                    Icon(
                        imageVector = Icons.Default.AccountBalanceWallet,
                        contentDescription = "Monthly Savings",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(16.dp)
                    )
                },
                iconContainerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.12f),
                title = "Savings",
                amount = monthlySavings,
                amountColor = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
private fun SummaryRow(
    icon: @Composable () -> Unit,
    iconContainerColor: Color,
    title: String,
    amount: Double,
    amountColor: Color
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(iconContainerColor),
                contentAlignment = Alignment.Center
            ) {
                icon()
            }

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontSize = 13.5.sp,
                    letterSpacing = 0.1.sp
                ),
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface
            )
        }

        Text(
            text = formatCurrency(amount),
            style = MaterialTheme.typography.titleMedium.copy(
                fontSize = 14.5.sp,
                letterSpacing = (-0.2).sp
            ),
            fontWeight = FontWeight.SemiBold,
            color = amountColor
        )
    }
}

private fun formatCurrency(amount: Double): String {
    return NumberFormat
        .getCurrencyInstance(Locale("en", "IN"))
        .format(amount)
}