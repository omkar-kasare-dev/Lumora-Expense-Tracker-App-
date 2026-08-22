package com.finance.lumora.domain.model



/**
 * Represents the severity level of a monthly budget alert.
 *
 * The alert level is determined from the percentage
 * of the monthly budget that has already been spent.
 */
enum class BudgetAlertLevel {

    /**
     * Spending is below the warning threshold.
     */
    NONE,

    /**
     * Spending has reached the first warning threshold.
     */
    WARNING,

    /**
     * Spending has reached the critical threshold.
     */
    CRITICAL,

    /**
     * Spending has reached or exceeded the monthly budget.
     */
    EXCEEDED
}

