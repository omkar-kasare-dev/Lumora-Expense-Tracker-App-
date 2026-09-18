package com.finance.lumora.data

import com.finance.lumora.domain.model.NotificationItem
import com.finance.lumora.domain.model.NotificationType

object SampleNotificationData {

    private const val ONE_HOUR = 60 * 60 * 1000L
    private const val ONE_DAY = 24 * ONE_HOUR

    fun getNotifications(): List<NotificationItem> {
        val now = System.currentTimeMillis()

        return listOf(
            NotificationItem(
                title = "Transaction Added",
                message = "₹450 expense was added to Food.",
                timestampMillis = now - 10 * 60 * 1000L,
                type = NotificationType.TRANSACTION_ALERT,
                isRead = false
            ),

            NotificationItem(
                title = "Income Added",
                message = "₹25,000 income was added successfully.",
                timestampMillis = now - 2 * ONE_HOUR,
                type = NotificationType.INCOME_ADDED,
                isRead = false
            ),

            NotificationItem(
                title = "Budget Alert",
                message = "You have used 80% of your monthly budget.",
                timestampMillis = now - 5 * ONE_HOUR,
                type = NotificationType.BUDGET_ALERT,
                isRead = false
            ),

            NotificationItem(
                title = "Large Expense",
                message = "A large expense of ₹8,500 was recorded.",
                timestampMillis = now - ONE_DAY,
                type = NotificationType.LARGE_EXPENSE_WARNING,
                isRead = true
            ),

            NotificationItem(
                title = "Security Update",
                message = "Your account security settings were updated.",
                timestampMillis = now - ONE_DAY - 2 * ONE_HOUR,
                type = NotificationType.SECURITY,
                isRead = true
            ),

            NotificationItem(
                title = "System Update",
                message = "Lumora has been updated with new improvements.",
                timestampMillis = now - 2 * ONE_DAY,
                type = NotificationType.SYSTEM,
                isRead = true
            ),

            NotificationItem(
                title = "Special Offer",
                message = "Check out the latest Lumora features and offers.",
                timestampMillis = now - 3 * ONE_DAY,
                type = NotificationType.PROMOTION,
                isRead = false
            )
        )
    }
}