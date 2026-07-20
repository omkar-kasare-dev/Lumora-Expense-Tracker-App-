package com.finance.lumora.presentation.dashboard.event

/*
import com.finance.lumora.domain.model.Transaction

/**
 * Represents all user actions that can occur
 * on the Dashboard screen.
 */
sealed interface DashboardEvent {

    // Data Loading
    // ----------------------------------------------------

    /**
     * Loads all dashboard data.
     */
    data object LoadDashboard : DashboardEvent

    /**
     * Refreshes dashboard data.
     */
    data object RefreshDashboard : DashboardEvent

    // Recent Transactions
    // ----------------------------------------------------

    /**
     * User tapped a recent transaction.
     */
    data class OpenTransaction(
        val transaction: Transaction
    ) : DashboardEvent

    /**
     * Navigate to complete transaction history.
     */
    data object ViewAllTransactions : DashboardEvent

    // Navigation
    // ----------------------------------------------------

    /**
     * Navigate to Add Transaction screen.
     */
    data object AddTransaction : DashboardEvent

    /**
     * Navigate to Categories screen.
     */
    data object OpenCategories : DashboardEvent

    /**
     * Navigate to Reports / Analytics.
     */
    data object OpenAnalytics : DashboardEvent

    /**
     * Navigate to Settings.
     */
    data object OpenSettings : DashboardEvent

    // UI
    // ----------------------------------------------------

    /**
     * Clears any error message shown on screen.
     */
    data object ClearError : DashboardEvent

}

*/



import com.finance.lumora.domain.model.TransactionWithCategory

/**
 * Represents all user actions
 * performed on the Dashboard screen.
 */
sealed interface DashboardEvent {

    /**
     * Load dashboard data.
     */
    data object LoadDashboard : DashboardEvent

    /**
     * Refresh dashboard data.
     */
    data object RefreshDashboard : DashboardEvent

    /**
     * Retry after an error.
     */
    data object Retry : DashboardEvent

    /**
     * User clicked a recent transaction.
     */
    data class TransactionClicked(

        val transaction: TransactionWithCategory

    ) : DashboardEvent

    /**
     * User clicked "View All Transactions".
     */
    data object ViewAllTransactions : DashboardEvent

    /**
     * User clicked the Add Transaction button.
     */
    data object AddTransaction : DashboardEvent


    data object AddTransactionClicked : DashboardEvent

    data object ViewAllTransactionsClicked : DashboardEvent

    data object CategoriesClicked : DashboardEvent

    data object ReportsClicked : DashboardEvent

    data object SettingsClicked : DashboardEvent





}