package com.finance.lumora.data.repository



import com.finance.lumora.data.SampleNotificationData
import com.finance.lumora.domain.model.NotificationItem
import com.finance.lumora.domain.repository.NotificationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotificationRepositoryImpl @Inject constructor() :
    NotificationRepository {

    //private val notifications = MutableStateFlow<List<NotificationItem>>(emptyList())
    private val notifications =
        MutableStateFlow(SampleNotificationData.getNotifications())

/*
    private val notifications = MutableStateFlow(
        listOf(
            NotificationItem(
                title = "Salary Credited",
                message = "Your monthly salary of $4,500 has been credited.",
                timestampMillis = System.currentTimeMillis() - 1000 * 60 * 15, // 15 mins ago
                type = NotificationType.INCOME_ADDED,
                isRead = false
            ),
            NotificationItem(
                title = "Large Expense Warning",
                message = "You spent $299.99 at Tech Store.",
                timestampMillis = System.currentTimeMillis() - 1000 * 60 * 60 * 3, // 3 hours ago
                type = NotificationType.LARGE_EXPENSE_WARNING,
                isRead = false
            ),
            NotificationItem(
                title = "Budget Threshold Reached",
                message = "You've used 85% of your monthly 'Dining Out' budget.",
                timestampMillis = System.currentTimeMillis() - 1000 * 60 * 60 * 6, // 6 hours ago
                type = NotificationType.BUDGET_ALERT,
                isRead = false
            ),
            NotificationItem(
                title = "System Maintenance",
                message = "Scheduled system maintenance on Sunday from 2 AM to 4 AM UTC.",
                timestampMillis = System.currentTimeMillis() - 1000 * 60 * 60 * 12, // 12 hours ago
                type = NotificationType.SYSTEM,
                isRead = true
            ),
            NotificationItem(
                title = "Security Alert",
                message = "Password changed successfully.",
                timestampMillis = System.currentTimeMillis() - 1000 * 60 * 60 * 25, // Yesterday
                type = NotificationType.SECURITY,
                isRead = true
            ),
            NotificationItem(
                title = "Exclusive Cashback Offer!",
                message = "Get 5% extra cashback on all travel bookings made this weekend.",
                timestampMillis = System.currentTimeMillis() - 1000 * 60 * 60 * 48, // 2 days ago
                type = NotificationType.PROMOTION,
                isRead = false
            )
        )
    )

 */

    override fun getNotifications(): Flow<List<NotificationItem>> {
        return notifications.asStateFlow()
    }

    override suspend fun markAsRead(notificationId: String) {
        notifications.update { currentNotifications ->
            currentNotifications.map { notification ->
                if (notification.id == notificationId) {
                    notification.copy(isRead = true)
                } else {
                    notification
                }
            }
        }
    }

    override suspend fun markAllAsRead() {
        notifications.update { currentNotifications ->
            currentNotifications.map { notification ->
                notification.copy(isRead = true)
            }
        }
    }

    override suspend fun clearAll() {
        notifications.value = emptyList()
    }
}