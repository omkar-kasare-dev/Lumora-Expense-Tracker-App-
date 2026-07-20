package com.finance.lumora.presentation.dashboard.state

import com.finance.lumora.domain.model.DashboardCategorySummary
import com.finance.lumora.domain.model.DashboardFinancialSummary
import com.finance.lumora.domain.model.DashboardStatistics
import com.finance.lumora.domain.model.TransactionWithCategory

/**
 * Represents the UI state of the Dashboard screen.
 */
data class DashboardState(

    /**
     * Indicates whether dashboard data
     * is currently loading.
     */
    val isLoading: Boolean = false,

    /**
     * Financial summary.
     */
    val financial: DashboardFinancialSummary =
        DashboardFinancialSummary(

            totalIncome = 0.0,

            totalExpense = 0.0,

            totalBalance = 0.0,

            monthlyIncome = 0.0,

            monthlyExpense = 0.0

        ),

    /**
     * Dashboard statistics.
     */
    val statistics: DashboardStatistics =
        DashboardStatistics(

            transactionCount = 0,

            largestIncome = 0.0,

            largestExpense = 0.0

        ),

    /**
     * Category analytics.
     */
    val category: DashboardCategorySummary =
        DashboardCategorySummary(),

    /**
     * Recent transactions.
     */
    val recentTransactions:
    List<TransactionWithCategory> = emptyList(),

    /**
     * Error message.
     */
    val error: String? = null

)