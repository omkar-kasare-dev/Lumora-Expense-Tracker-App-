package com.finance.lumora.presentation.dashboard.event



sealed interface DashboardNavigationEvent {

    /**
     * Navigate to Add Transaction
     */
    data object NavigateToAddTransaction :
        DashboardNavigationEvent

    /**
     * Navigate to Transaction List
     */
    data object NavigateToTransactions :
        DashboardNavigationEvent

    /**
     * Navigate to Categories
     */
    data object NavigateToCategories :
        DashboardNavigationEvent

    /**
     * Navigate to Reports
     */
    data object NavigateToReports :
        DashboardNavigationEvent

    /**
     * Navigate to Settings
     */
    data object NavigateToSettings :
        DashboardNavigationEvent

    /**
     * Navigate to Transaction Details
     */
    data class NavigateToTransactionDetails(

        val transactionId: Long

    ) : DashboardNavigationEvent

}