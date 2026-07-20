package com.finance.lumora.domain.model


/**
 * Represents category analytics displayed
 * on the Dashboard.
 *
 * This model groups category-related
 * information into a single object.
 */
data class DashboardCategorySummary(

    /**
     * Category with the highest expense.
     *
     * Null when no expense transaction exists.
     */
    val topExpenseCategory: TopExpenseCategory? = null

)