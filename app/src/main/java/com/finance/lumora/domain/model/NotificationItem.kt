package com.finance.lumora.domain.model



import java.util.UUID

enum class NotificationType {
    TRANSACTION_ALERT,  // Large expense, budget warning, salary credit
    SECURITY,           // Password changed, new login
    SYSTEM,             // App updates, maintenance
    PROMOTION  ,         // Feature announcements, offers
    INCOME_ADDED,         // Added
    BUDGET_ALERT,         // Added
    LARGE_EXPENSE_WARNING // Added
}

data class NotificationItem(
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val message: String,
    val timestampMillis: Long,
    val type: NotificationType,
    val isRead: Boolean = false,
    val actionUrl: String? = null
)


/*
data class NotificationItem(
    val id: String,
    val title: String,
    val message: String,
    val timestampMillis: Long,
    val isRead: Boolean = false,
    val type: NotificationType
)

 */