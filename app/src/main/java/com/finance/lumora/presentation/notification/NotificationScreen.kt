package com.finance.lumora.presentation.notification


import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.finance.lumora.domain.model.NotificationItem
import com.finance.lumora.domain.model.NotificationType
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit

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
    val filters = listOf("All", "Unread", "Transactions", "Security")

    val filteredNotifications = remember(notifications, selectedFilter) {
        when (selectedFilter) {
            "Unread" -> notifications.filter { !it.isRead }
            "Transactions" -> notifications.filter { it.type == NotificationType.TRANSACTION_ALERT }
            "Security" -> notifications.filter { it.type == NotificationType.SECURITY }
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
                selectedTabIndex = filters.indexOf(selectedFilter),
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

@Composable
fun NotificationCard(
    notification: NotificationItem,
    onClick: () -> Unit
) {
    val backgroundColor by animateColorAsState(
        targetValue = if (!notification.isRead) {
            MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.25f)
        } else {
            MaterialTheme.colorScheme.surface
        },
        label = "bgColor"
    )

    Surface(
        color = backgroundColor,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 14.dp),
            verticalAlignment = Alignment.Top
        ) {
            // Icon Badge
            val (icon, iconTint, bgTint) = getNotificationTypeConfig(notification.type)
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(bgTint),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = iconTint,
                    modifier = Modifier.size(22.dp)
                )
            }

            Spacer(modifier = Modifier.width(14.dp))

            // Title, Message, & Time
            Column(modifier = Modifier.weight(1f)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = notification.title,
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = if (!notification.isRead) FontWeight.Bold else FontWeight.SemiBold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        text = formatRelativeTime(notification.timestampMillis),
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = notification.message,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }

            // Unread Dot
            if (!notification.isRead) {
                Spacer(modifier = Modifier.width(8.dp))
                Box(
                    modifier = Modifier
                        .padding(top = 6.dp)
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primary)
                )
            }
        }
    }
    HorizontalDivider(
        color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f),
        thickness = 0.5.dp
    )
}

@Composable
fun EmptyNotificationState(filter: String) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(96.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Outlined.NotificationsOff,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(48.dp)
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = if (filter == "All") "No Notifications Yet" else "No $filter Notifications",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "We'll notify you when important financial alerts, budget updates, or security changes occur.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(horizontal = 16.dp),
            fontSize = 14.sp
        )
    }
}

// --- HELPER UTILS ---

@Composable
private fun getNotificationTypeConfig(type: NotificationType): Triple<ImageVector, Color, Color> {
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
    }
}

private fun groupNotificationsByDate(list: List<NotificationItem>): Map<String, List<NotificationItem>> {
    val now = System.currentTimeMillis()
    val oneDayMillis = TimeUnit.DAYS.toMillis(1)

    return list.groupBy { item ->
        val diff = now - item.timestampMillis
        when {
            diff < oneDayMillis -> "Today"
            diff < 2 * oneDayMillis -> "Yesterday"
            else -> "Earlier"
        }
    }
}

private fun formatRelativeTime(timestampMillis: Long): String {
    val diff = System.currentTimeMillis() - timestampMillis
    val minutes = TimeUnit.MILLISECONDS.toMinutes(diff)
    val hours = TimeUnit.MILLISECONDS.toHours(diff)
    val days = TimeUnit.MILLISECONDS.toDays(diff)

    return when {
        minutes < 1 -> "Just now"
        minutes < 60 -> "${minutes}m ago"
        hours < 24 -> "${hours}h ago"
        days < 7 -> "${days}d ago"
        else -> SimpleDateFormat("dd MMM", Locale.getDefault()).format(Date(timestampMillis))
    }
}