package com.finance.lumora.presentation.notification.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountBalanceWallet
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.LocalOffer
import androidx.compose.material.icons.outlined.PriceCheck
import androidx.compose.material.icons.outlined.Savings
import androidx.compose.material.icons.outlined.Security
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.finance.lumora.domain.model.NotificationType

@Composable
fun getNotificationTypeConfig(type: NotificationType): Triple<ImageVector, Color, Color> {
    return when (type) {
        NotificationType.TRANSACTION_ALERT -> Triple(
            Icons.Outlined.AccountBalanceWallet,
            MaterialTheme.colorScheme.primary,
            MaterialTheme.colorScheme.primaryContainer
        )
        NotificationType.SECURITY -> Triple(
            Icons.Outlined.Security,
            MaterialTheme.colorScheme.error,
            MaterialTheme.colorScheme.errorContainer
        )
        NotificationType.SYSTEM -> Triple(
            Icons.Outlined.Info,
            MaterialTheme.colorScheme.tertiary,
            MaterialTheme.colorScheme.tertiaryContainer
        )
        NotificationType.PROMOTION -> Triple(
            Icons.Outlined.LocalOffer,
            MaterialTheme.colorScheme.secondary,
            MaterialTheme.colorScheme.secondaryContainer
        )
        NotificationType.INCOME_ADDED -> Triple(
            Icons.Outlined.Savings,
            MaterialTheme.colorScheme.primary,
            MaterialTheme.colorScheme.primaryContainer
        )
        NotificationType.BUDGET_ALERT -> Triple(
            Icons.Outlined.Warning,
            MaterialTheme.colorScheme.error,
            MaterialTheme.colorScheme.errorContainer
        )
        NotificationType.LARGE_EXPENSE_WARNING -> Triple(
            Icons.Outlined.PriceCheck,
            MaterialTheme.colorScheme.tertiary,
            MaterialTheme.colorScheme.tertiaryContainer
        )
    }
}