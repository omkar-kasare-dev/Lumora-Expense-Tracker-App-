package com.finance.lumora.presentation.notification

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.finance.lumora.domain.model.NotificationItem
import com.finance.lumora.presentation.notification.viewmodel.NotificationViewModel

@Composable
fun NotificationRoute(
    onBackClick: () -> Unit = {},
    onNotificationClick: (NotificationItem) -> Unit = {}
) {
    val viewModel: NotificationViewModel = hiltViewModel()

    val notifications = viewModel.notifications.collectAsState()

    NotificationScreen(
        notifications = notifications.value,
        onBackClick = onBackClick,
        onNotificationClick = { notification ->
            viewModel.markAsRead(notification.id)
            onNotificationClick(notification)
        },
        onMarkAllAsReadClick = {
            viewModel.markAllAsRead()
        },
        onClearAllClick = {
            viewModel.clearAll()
        }
    )
}