package com.finance.lumora.domain.model


/**
 * Represents the notification state of the current month's budget.
 * This state is used to prevent Lumora from repeatedly sending
 * the same budget alert whenever the transaction data changes.
 *
 * Example:
 * 80% reached  -> WARNING notification sent
 * 81% reached  -> No new WARNING notification
 * 90% reached  -> CRITICAL notification sent
 * 91% reached  -> No new CRITICAL notification
 * 100% reached -> EXCEEDED notification sent
 */
data class BudgetAlertState(

    val monthKey: String = "",

    val lastNotifiedLevel: BudgetAlertLevel =
        BudgetAlertLevel.NONE
)

