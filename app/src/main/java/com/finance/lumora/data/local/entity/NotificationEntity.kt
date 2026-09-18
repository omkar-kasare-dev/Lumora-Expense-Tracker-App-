package com.finance.lumora.data.local.entity


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.finance.lumora.domain.model.NotificationType

@Entity(tableName = "notifications")
data class NotificationEntity(
    @PrimaryKey val id: String,
    val title: String,
    val message: String,
    val timestampMillis: Long,
    val isRead: Boolean,
    val type: NotificationType,
    val actionUrl: String? = null
)