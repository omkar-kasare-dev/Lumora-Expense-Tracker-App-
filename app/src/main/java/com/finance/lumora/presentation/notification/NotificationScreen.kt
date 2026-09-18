package com.finance.lumora.presentation.notification

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.finance.lumora.domain.model.NotificationItem
import com.finance.lumora.domain.model.NotificationType
import com.finance.lumora.presentation.notification.components.EmptyNotificationState
import com.finance.lumora.presentation.notification.components.NotificationCard
import com.finance.lumora.presentation.notification.components.groupNotificationsByDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationScreen(
    notifications: List<NotificationItem>,
    onBackClick: () -> Unit = {},
    onNotificationClick: (NotificationItem) -> Unit = {},
    onMarkAllAsReadClick: () -> Unit = {},
    onClearAllClick: () -> Unit = {}
) {
    // Filter / Tab State
    var selectedFilter by remember { mutableStateOf("All") }
    val filters = listOf(
        "All",
        "Unread",
        "Transactions",
        "Income",
        "Budget",
        "Security",
        "System",
        "Promotions"
    )

    val filteredNotifications = remember(notifications, selectedFilter) {
        when (selectedFilter) {
            "Unread" -> notifications.filter { !it.isRead }
            "Transactions" -> notifications.filter {
                it.type == NotificationType.TRANSACTION_ALERT || it.type == NotificationType.LARGE_EXPENSE_WARNING
            }
            "Income" -> notifications.filter { it.type == NotificationType.INCOME_ADDED }
            "Budget" -> notifications.filter { it.type == NotificationType.BUDGET_ALERT }
            "Security" -> notifications.filter { it.type == NotificationType.SECURITY }
            "System" -> notifications.filter { it.type == NotificationType.SYSTEM }
            "Promotions" -> notifications.filter { it.type == NotificationType.PROMOTION }
            else -> notifications
        }
    }

    val unreadCount = remember(notifications) { notifications.count { !it.isRead } }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("Notifications", fontWeight = FontWeight.Bold)
                        if (unreadCount > 0) {
                            Spacer(modifier = Modifier.width(8.dp))
                            Badge(
                                containerColor = MaterialTheme.colorScheme.primary,
                                contentColor = MaterialTheme.colorScheme.onPrimary
                            ) {
                                Text("$unreadCount", modifier = Modifier.padding(horizontal = 4.dp))
                            }
                        }
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    var showMenu by remember { mutableStateOf(false) }
                    IconButton(onClick = { showMenu = !showMenu }) {
                        Icon(Icons.Outlined.MoreVert, contentDescription = "More Options")
                    }
                    DropdownMenu(
                        expanded = showMenu,
                        onDismissRequest = { showMenu = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("Mark all as read") },
                            leadingIcon = { Icon(Icons.Outlined.DoneAll, contentDescription = null) },
                            onClick = {
                                onMarkAllAsReadClick()
                                showMenu = false
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Clear all") },
                            leadingIcon = { Icon(Icons.Outlined.DeleteSweep, contentDescription = null) },
                            onClick = {
                                onClearAllClick()
                                showMenu = false
                            }
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // --- FILTER CHIPS ---
            ScrollableTabRow(
                selectedTabIndex = filters.indexOf(selectedFilter).coerceAtLeast(0),
                edgePadding = 16.dp,
                divider = {},
                indicator = {}
            ) {
                filters.forEach { filter ->
                    val isSelected = filter == selectedFilter
                    FilterChip(
                        selected = isSelected,
                        onClick = { selectedFilter = filter },
                        label = { Text(filter) },
                        modifier = Modifier.padding(end = 8.dp),
                        shape = RoundedCornerShape(20.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // --- NOTIFICATION LIST OR EMPTY STATE ---
            if (filteredNotifications.isEmpty()) {
                EmptyNotificationState(filter = selectedFilter)
            } else {
                val groupedNotifications = remember(filteredNotifications) {
                    groupNotificationsByDate(filteredNotifications)
                }

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(bottom = 16.dp)
                ) {
                    groupedNotifications.forEach { (header, items) ->
                        item {
                            Text(
                                text = header,
                                style = MaterialTheme.typography.labelLarge,
                                color = MaterialTheme.colorScheme.primary,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
                            )
                        }
                        items(items, key = { it.id }) { notification ->
                            NotificationCard(
                                notification = notification,
                                onClick = { onNotificationClick(notification) }
                            )
                        }
                    }
                }
            }
        }
    }
}