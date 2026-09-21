package com.finance.lumora.data.mapper



import com.finance.lumora.data.local.entity.NotificationEntity
import com.finance.lumora.domain.model.NotificationItem

fun NotificationEntity.toDomain(): NotificationItem {
    return NotificationItem(
        id = id,
        title = title,
        message = message,
        timestampMillis = timestampMillis,
        type = type,
        isRead = isRead,
        actionUrl = actionUrl
    )
}

fun List<NotificationEntity>.toDomainList(): List<NotificationItem> {
    return map { it.toDomain() }
}

fun NotificationItem.toEntity(): NotificationEntity {
    return NotificationEntity(
        id = id,
        title = title,
        message = message,
        timestampMillis = timestampMillis,
        isRead = isRead,
        type = type,
        actionUrl = actionUrl
    )
}