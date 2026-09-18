package com.finance.lumora.presentation.notification.components

import com.finance.lumora.domain.model.NotificationItem
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit

fun groupNotificationsByDate(list: List<NotificationItem>): Map<String, List<NotificationItem>> {
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

fun formatRelativeTime(timestampMillis: Long): String {
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