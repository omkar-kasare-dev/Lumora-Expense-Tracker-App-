package com.finance.lumora.presentation.transaction.effect



/**
 * Represents one-time UI actions.
 *
 * Unlike TransactionState, these events
 * should be consumed only once by the UI.
 */
sealed interface TransactionUiEffect {

    /**
     * Display a Snackbar.
     */
    data class ShowSnackbar(
        val message: String
    ) : TransactionUiEffect

    /**
     * Navigate back after
     * successfully saving/updating.
     */
    data object NavigateBack : TransactionUiEffect

    /**
     * Navigate to Edit screen.
     */
    data class NavigateToEdit(
        val transactionId: Long
    ) : TransactionUiEffect

    /**
     * Open Delete Confirmation Dialog.
     */
    data class ShowDeleteConfirmation(
        val transactionId: Long
    ) : TransactionUiEffect

    /**
     * Hide Delete Confirmation Dialog.
     */
    data object DismissDeleteConfirmation : TransactionUiEffect

    /**
     * Scroll transaction list to top.
     */
    data object ScrollToTop : TransactionUiEffect
}