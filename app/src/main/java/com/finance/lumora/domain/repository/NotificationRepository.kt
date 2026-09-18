package com.finance.lumora.domain.repository

import com.finance.lumora.domain.model.NotificationItem
import kotlinx.coroutines.flow.Flow

interface NotificationRepository {



    fun getNotifications(): Flow<List<NotificationItem>>

    suspend fun markAsRead(notificationId: String)

    suspend fun markAllAsRead()

    suspend fun clearAll()
}