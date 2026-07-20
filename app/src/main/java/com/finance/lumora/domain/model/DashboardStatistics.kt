package com.finance.lumora.domain.model


/**
 * Represents statistical information
 * shown on the Dashboard.
 *
 * This model groups all transaction
 * statistics into a single object.
 */
data class DashboardStatistics(

    /**
     * Total number of transactions.
     */
    val transactionCount: Int,

    /**
     * Largest income transaction.
     */
    val largestIncome: Double,

    /**
     * Largest expense transaction.
     */
    val largestExpense: Double

)