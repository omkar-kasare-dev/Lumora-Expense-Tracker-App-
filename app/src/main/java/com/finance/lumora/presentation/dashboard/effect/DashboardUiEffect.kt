package com.finance.lumora.presentation.dashboard.effect

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

    data class ShowSnackbar(

        val message: String

    ) : DashboardUiEffect


    data object NavigateToAddTransaction : DashboardUiEffect


    data object NavigateToTransactions : DashboardUiEffect


    data class NavigateToTransactionDetails(

        val transaction: TransactionWithCategory

    ) : DashboardUiEffect

}