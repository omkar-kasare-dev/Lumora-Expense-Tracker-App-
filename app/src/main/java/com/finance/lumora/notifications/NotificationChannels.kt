package com.finance.lumora.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build

object NotificationChannels {

    /**
     * Creates all Lumora notification channels.
     * Calling this method multiple times is safe.
     * Android ignores creation requests for channels
     * that already exist.
     */
    fun createChannels(context: Context) {

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            return
        }

        val notificationManager =
            context.getSystemService(
                Context.NOTIFICATION_SERVICE
            ) as NotificationManager

        // ---------------------------------------------------------------------
        // General Notifications
        val generalChannel =
            NotificationChannel(
                NotificationConstants.GENERAL_CHANNEL_ID,
                "General Notifications",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {

                description =
                    "General notifications from Lumora"
            }

        // ---------------------------------------------------------------------
        // Budget Alerts
        val budgetAlertsChannel =
            NotificationChannel(
                NotificationConstants.BUDGET_CHANNEL_ID,
                "Budget Alerts",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {

                description =
                    "Notifications related to your budget and spending"
            }

        // ---------------------------------------------------------------------
        // Register Channels
        notificationManager.createNotificationChannel(
            generalChannel
        )

        notificationManager.createNotificationChannel(
            budgetAlertsChannel
        )
    }
}

