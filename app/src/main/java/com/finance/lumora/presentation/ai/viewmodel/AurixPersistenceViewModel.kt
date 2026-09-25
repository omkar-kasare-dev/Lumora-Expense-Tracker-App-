package com.finance.lumora.presentation.ai.viewmodel
/*
import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.finance.lumora.domain.model.ResolvedTransactionDraft
import com.finance.lumora.domain.usecase.transaction.SaveAurixTransactionUseCase
import com.finance.lumora.notifications.BudgetAlertCoordinator
import com.finance.lumora.presentation.ai.model.AurixPersistenceState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AurixPersistenceViewModel @Inject constructor(
    private val saveAurixTransactionUseCase: SaveAurixTransactionUseCase,
    private val budgetAlertCoordinator: BudgetAlertCoordinator
) : ViewModel() {

    private val _state =
        MutableStateFlow<AurixPersistenceState>(
            AurixPersistenceState.Idle
        )

    val state: StateFlow<AurixPersistenceState> =
        _state.asStateFlow()

    fun saveTransaction(
        resolvedDraft: ResolvedTransactionDraft
    ) {
        if (_state.value is AurixPersistenceState.Saving) {
            return
        }

        viewModelScope.launch {

            _state.value = AurixPersistenceState.Saving

            try {
                val result =
                    saveAurixTransactionUseCase(resolvedDraft)

                result
                    .onSuccess {
                        evaluateBudgetAlerts()
                        _state.value =
                            AurixPersistenceState.Saved
                    }
                    .onFailure { exception ->
                        _state.value =
                            AurixPersistenceState.Error(
                                exception.message
                                    ?: "Unable to save transaction."
                            )
                    }

            } catch (exception: Exception) {
                _state.value =
                    AurixPersistenceState.Error(
                        exception.message
                            ?: "Unable to save transaction."
                    )
            }
        }
    }

    @SuppressLint("MissingPermission")
    private suspend fun evaluateBudgetAlerts() {
        try {
            budgetAlertCoordinator.evaluate()
        } catch (_: Exception) {
            /*
             * The transaction has already been saved successfully.
             *
             * Budget-alert/notification failure must not
             * make the transaction appear to have failed.
             */
        }
    }

    fun reset() {
        _state.value = AurixPersistenceState.Idle
    }
}

 */


import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.finance.lumora.data.local.enums.TransactionType
import com.finance.lumora.domain.model.ResolvedTransactionDraft
import com.finance.lumora.domain.usecase.transaction.SaveAurixTransactionUseCase
import com.finance.lumora.notifications.BudgetAlertCoordinator
import com.finance.lumora.notifications.TransactionNotifier
import com.finance.lumora.presentation.ai.model.AurixPersistenceState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AurixPersistenceViewModel @Inject constructor(
    private val saveAurixTransactionUseCase: SaveAurixTransactionUseCase,
    private val budgetAlertCoordinator: BudgetAlertCoordinator,
    private val transactionNotifier: TransactionNotifier
) : ViewModel() {

    private val _state =
        MutableStateFlow<AurixPersistenceState>(
            AurixPersistenceState.Idle
        )

    val state: StateFlow<AurixPersistenceState> =
        _state.asStateFlow()

    fun saveTransaction(
        resolvedDraft: ResolvedTransactionDraft
    ) {
        if (_state.value is AurixPersistenceState.Saving) {
            return
        }

        viewModelScope.launch {

            _state.value = AurixPersistenceState.Saving

            try {
                val result =
                    saveAurixTransactionUseCase(resolvedDraft)

                result
                    .onSuccess { savedTransaction ->

                        // ---------------------------------------------------------
                        // In-App Notification (Transaction Added)
                        // ---------------------------------------------------------
                        //
                        // Previously missing entirely - Aurix-saved transactions
                        // (voice or receipt capture) never generated an in-app
                        // "Transaction Added" / "Large Expense" notification,
                        // unlike transactions saved via the manual entry form.
                        // ---------------------------------------------------------

                        try {
                            transactionNotifier.notifyTransactionAdded(
                                transaction = savedTransaction,
                                categoryName = resolvedDraft.category?.name
                                    ?: "Uncategorized"
                            )
                        } catch (e: Exception) {
                            // A notification failure must not make the
                            // transaction appear to have failed to save.
                        }

                        // Budget evaluation only makes sense for expenses -
                        // matches the same gating TransactionViewModel uses
                        // for the manual entry path.
                        if (savedTransaction.type == TransactionType.EXPENSE) {
                            evaluateBudgetAlerts()
                        }

                        _state.value =
                            AurixPersistenceState.Saved
                    }
                    .onFailure { exception ->
                        _state.value =
                            AurixPersistenceState.Error(
                                exception.message
                                    ?: "Unable to save transaction."
                            )
                    }

            } catch (exception: Exception) {
                _state.value =
                    AurixPersistenceState.Error(
                        exception.message
                            ?: "Unable to save transaction."
                    )
            }
        }
    }

    @SuppressLint("MissingPermission")
    private suspend fun evaluateBudgetAlerts() {
        try {
            budgetAlertCoordinator.evaluate()
        } catch (_: Exception) {
            /*
             * The transaction has already been saved successfully.
             *
             * Budget-alert/notification failure must not
             * make the transaction appear to have failed.
             */
        }
    }

    fun reset() {
        _state.value = AurixPersistenceState.Idle
    }
}