package com.finance.lumora.notifications

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.annotation.RequiresPermission
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.finance.lumora.R
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LumoraNotificationManager @Inject constructor(
    @ApplicationContext
    private val context: Context
) {

    private val notificationManager =
        NotificationManagerCompat.from(context)

    // =========================================================================
    // GENERAL NOTIFICATION
    /**
     * Displays a general Lumora notification.
     * Permission is checked before attempting to post
     * the notification.
     */
    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    fun showGeneralNotification(
        title: String,
        message: String
    ) {

        if (!canPostNotifications()) {
            return
        }

        val notification =
            NotificationCompat.Builder(
                context,
                NotificationConstants.GENERAL_CHANNEL_ID
            )
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(
                    NotificationCompat.PRIORITY_DEFAULT
                )
                .setAutoCancel(true)
                .build()

        notificationManager.notify(
            NotificationConstants.GENERAL_NOTIFICATION_ID,
            notification
        )
    }

    // =========================================================================
    // BUDGET ALERT NOTIFICATION
    /**
     * Displays a budget-related financial alert.
     * Budget notifications use a dedicated notification channel
     * and notification ID so they do not overwrite general
     */
    // LumoraNotificationManager.kt
    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    fun showBudgetAlertNotification(
        title: String,
        message: String
    ): Boolean {
        if (!canPostNotifications()) {
            return false
        }

        val notification = NotificationCompat.Builder(
            context,
            NotificationConstants.BUDGET_CHANNEL_ID
        )
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(title)
            .setContentText(message)
            .setStyle(NotificationCompat.BigTextStyle().bigText(message))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(
            NotificationConstants.BUDGET_NOTIFICATION_ID,
            notification
        )
        return true
    }

    // =========================================================================
    // NOTIFICATION PERMISSION
    private fun canPostNotifications(): Boolean {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {

            return context.checkSelfPermission(
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        }

        return notificationManager.areNotificationsEnabled()
    }
}

