package com.finance.lumora.notifications



import com.finance.lumora.domain.model.BudgetAlertEvent

/**
 * Converts domain budget-alert events into
 * user-facing notification content.
 *
 * This class does not display notifications.
 * It only prepares the content required by
 * LumoraNotificationManager.
 */
object BudgetAlertNotificationMapper {

    data class NotificationContent(
        val title: String,
        val message: String
    )

    /**
     * Maps a BudgetAlertEvent to notification content.
     *
     * Returns null when no notification should be shown.
     */
    fun map(
        event: BudgetAlertEvent
    ): NotificationContent? {

        return when (event) {

            BudgetAlertEvent.None -> {
                null
            }

            BudgetAlertEvent.Warning -> {
                NotificationContent(
                    title = "Budget Warning",
                    message = "You've used 80% of your monthly budget."
                )
            }

            BudgetAlertEvent.Critical -> {
                NotificationContent(
                    title = "Budget Alert",
                    message = "You've used 90% of your monthly budget."
                )
            }

            BudgetAlertEvent.Exceeded -> {
                NotificationContent(
                    title = "Budget Exceeded",
                    message = "You've reached or exceeded your monthly budget."
                )
            }
        }
    }
}

