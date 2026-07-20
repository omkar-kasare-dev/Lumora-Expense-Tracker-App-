package com.finance.lumora.domain.repository


import com.finance.lumora.domain.model.DashboardSummary
import kotlinx.coroutines.flow.Flow

/**
 * Repository contract for Dashboard operations.
 *
 * Provides aggregated financial information required
 * by the Dashboard screen.
 */
interface DashboardRepository {

    // ----------------------------------------------------
    // Dashboard Summary
    // ----------------------------------------------------

    /**
     * Returns the complete dashboard summary.
     *
     * This includes:
     * - Total Balance
     * - Total Income
     * - Total Expense
     * - Monthly Summary
     * - Statistics
     * - Top Expense Category
     * - Recent Transactions
     */
    fun getDashboardSummary(): Flow<DashboardSummary>

}