package com.finance.lumora.notifications

/*
import com.finance.lumora.data.local.enums.TransactionType
import com.finance.lumora.domain.model.NotificationItem
import com.finance.lumora.domain.model.NotificationType
import com.finance.lumora.domain.model.Transaction
import com.finance.lumora.domain.repository.NotificationRepository
import com.finance.lumora.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Builds and stores in-app notifications for transaction-related
 * events (Steps 8.1-8.3). This class only writes to the in-app
 * Notification screen (via NotificationRepository) - it never
 * posts a system tray notification. System tray notifications
 * remain the responsibility of LumoraNotificationManager /
 * BudgetAlertCoordinator (Steps 8.4-8.5).
 */
@Singleton
class TransactionNotifier @Inject constructor(
    private val notificationRepository: NotificationRepository,
    private val settingsRepository: SettingsRepository
) {

    /**
     * Step 8.1 - Transaction added.
     *
     * Fires only for EXPENSE transactions today. INCOME transactions
     * are intentionally NOT handled here - Step 8.2 will add that
     * branch. This avoids a transaction ever producing two
     * notifications once 8.2/8.3 land.
     */
    suspend fun notifyTransactionAdded(
        transaction: Transaction,
        categoryName: String
    ) {
        if (!settingsRepository.isNotificationsEnabled.first()) {
            return
        }

        if (transaction.type != TransactionType.EXPENSE) {
            // INCOME is handled by Step 8.2, not yet implemented.
            return
        }

        val notification = NotificationItem(
            title = "Transaction Added",
            message = "${formatAmount(transaction.amount)} expense was added to $categoryName.",
            timestampMillis = System.currentTimeMillis(),
            type = NotificationType.TRANSACTION_ALERT,
            isRead = false
        )

        notificationRepository.addNotification(notification)
    }

    private fun formatAmount(amount: Double): String {
        return "₹${"%,.2f".format(amount)}"
    }
}

 */


import com.finance.lumora.data.local.enums.TransactionType
import com.finance.lumora.domain.model.NotificationItem
import com.finance.lumora.domain.model.NotificationType
import com.finance.lumora.domain.model.Transaction
import com.finance.lumora.domain.repository.NotificationRepository
import com.finance.lumora.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class TransactionNotifier @Inject constructor(
    private val notificationRepository: NotificationRepository,
    private val settingsRepository: SettingsRepository
) {


    suspend fun notifyTransactionAdded(
        transaction: Transaction,
        categoryName: String
    ) {
        if (!settingsRepository.isNotificationsEnabled.first()) {
            return
        }

        val notification = when (transaction.type) {

            TransactionType.EXPENSE -> {
                val threshold = settingsRepository.largeExpenseThreshold.first()

                if (transaction.amount >= threshold) {
                    NotificationItem(
                        title = "Large Expense",
                        message = "A large expense of ${formatAmount(transaction.amount)} was recorded in $categoryName.",
                        timestampMillis = System.currentTimeMillis(),
                        type = NotificationType.LARGE_EXPENSE_WARNING,
                        isRead = false
                    )
                } else {
                    NotificationItem(
                        title = "Transaction Added",
                        message = "${formatAmount(transaction.amount)} expense was added to $categoryName.",
                        timestampMillis = System.currentTimeMillis(),
                        type = NotificationType.TRANSACTION_ALERT,
                        isRead = false
                    )
                }
            }

            TransactionType.INCOME -> {
                NotificationItem(
                    title = "Income Added",
                    message = "${formatAmount(transaction.amount)} income was added successfully.",
                    timestampMillis = System.currentTimeMillis(),
                    type = NotificationType.INCOME_ADDED,
                    isRead = false
                )
            }
        }

        notificationRepository.addNotification(notification)
    }

    private fun formatAmount(amount: Double): String {
        return "₹${"%,.2f".format(amount)}"
    }
}