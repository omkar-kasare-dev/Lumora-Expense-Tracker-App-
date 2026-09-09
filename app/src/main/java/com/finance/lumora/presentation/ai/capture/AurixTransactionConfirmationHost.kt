package com.finance.lumora.presentation.ai.capture

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import com.finance.lumora.domain.model.ResolvedTransactionDraft
import com.finance.lumora.presentation.ai.viewmodel.AurixPersistenceViewModel

@Composable
fun AurixTransactionConfirmationHost(
    resolvedDraft: ResolvedTransactionDraft,
    onDismiss: () -> Unit,
    onSaved: () -> Unit,
    onEdit: (ResolvedTransactionDraft) -> Unit,
    viewModel: AurixPersistenceViewModel = hiltViewModel()
) {
    val state = viewModel.state

    TransactionConfirmationBottomSheet(
        resolvedDraft = resolvedDraft,

        onConfirm = {
            viewModel.saveTransaction(resolvedDraft)
        },

        onEdit = {
            onEdit(resolvedDraft)
        },

        onDismiss = {
            viewModel.reset()
            onDismiss()
        }
    )

    LaunchedEffect(state) {
        when (state.value) {
            is com.finance.lumora.presentation.ai.model.AurixPersistenceState.Saved -> {
                viewModel.reset()
                onSaved()
            }

            else -> Unit
        }
    }
}