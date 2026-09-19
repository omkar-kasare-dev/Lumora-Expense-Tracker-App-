package com.finance.lumora.data.repository


import com.finance.lumora.data.local.dao.NotificationDao
import com.finance.lumora.data.mapper.toDomainList
import com.finance.lumora.data.mapper.toEntity
import com.finance.lumora.domain.model.NotificationItem
import com.finance.lumora.domain.repository.NotificationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotificationRepositoryImpl @Inject constructor(
    private val notificationDao: NotificationDao
) : NotificationRepository {

    override fun getNotifications(): Flow<List<NotificationItem>> {
        return notificationDao
            .getAllNotifications()
            .map { it.toDomainList() }
    }

    override suspend fun addNotification(notification: NotificationItem) {
        notificationDao.insertNotification(notification.toEntity())
    }

    override suspend fun markAsRead(notificationId: String) {
        notificationDao.markAsRead(notificationId)
    }

    override suspend fun markAllAsRead() {
        notificationDao.markAllAsRead()
    }

    override suspend fun clearAll() {
        notificationDao.clearAll()
    }
}