package com.finance.lumora.domain.model


/**
 * Represents statistical information
 * shown on the Dashboard.
 *
 * This model groups all transaction
 * statistics into a single object.
 */
data class DashboardStatistics(

    val transactionCount: Int,

    val largestIncome: Double,

    val largestExpense: Double

)