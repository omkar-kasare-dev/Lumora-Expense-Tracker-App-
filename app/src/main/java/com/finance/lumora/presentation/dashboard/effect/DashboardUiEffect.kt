package com.finance.lumora.presentation.dashboard.effect
/*

/**
 * Represents one-time UI effects for the Dashboard screen.
 *
 * Unlike DashboardState, these events are consumed only once
 * and are not preserved during recomposition.
 */
sealed interface DashboardUiEffect {

    // Snackbar
    // ----------------------------------------------------

    /**
     * Displays a snackbar message.
     */
    data class ShowSnackbar(
        val message: String
    ) : DashboardUiEffect

    // Navigation
    // ----------------------------------------------------

    /**
     * Navigate to the Transaction screen.
     */
    data object NavigateToTransactions : DashboardUiEffect

    /**
     * Navigate to the Add Transaction screen.
     */
    data object NavigateToAddTransaction : DashboardUiEffect

    /**
     * Navigate to the Categories screen.
     */
    data object NavigateToCategories : DashboardUiEffect

    /**
     * Navigate to the Analytics screen.
     */
    data object NavigateToAnalytics : DashboardUiEffect

    /**
     * Navigate to the Settings screen.
     */
    data object NavigateToSettings : DashboardUiEffect

    /**
     * Navigate to Transaction Details screen.
     */
    data class NavigateToTransactionDetails(
        val transactionId: Long
    ) : DashboardUiEffect

}

 */



import com.finance.lumora.domain.model.TransactionWithCategory

/**
 * Represents one-time UI events for
 * the Dashboard screen.
 *
 * Unlike DashboardState, these effects
 * are consumed once and are not retained
 * after configuration changes.
 */
sealed interface DashboardUiEffect {

    /**
     * Displays a Snackbar message.
     */
    data class ShowSnackbar(

        val message: String

    ) : DashboardUiEffect

    /**
     * Navigate to Add Transaction screen.
     */
    data object NavigateToAddTransaction : DashboardUiEffect

    /**
     * Navigate to All Transactions screen.
     */
    data object NavigateToTransactions : DashboardUiEffect

    /**
     * Navigate to Transaction Details.
     */
    data class NavigateToTransactionDetails(

        val transaction: TransactionWithCategory

    ) : DashboardUiEffect

}