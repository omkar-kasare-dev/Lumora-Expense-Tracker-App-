package com.finance.lumora.presentation.ai.capture

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.finance.lumora.domain.model.ResolvedTransactionDraft
import com.finance.lumora.presentation.ai.model.AurixPersistenceState
import com.finance.lumora.presentation.ai.viewmodel.AurixPersistenceViewModel

@Composable
fun TransactionConfirmationHost(
    resolvedDraft: ResolvedTransactionDraft?,
    onDismiss: () -> Unit,
    onSaved: () -> Unit,
    onEdit: (ResolvedTransactionDraft) -> Unit,
    viewModel: AurixPersistenceViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    resolvedDraft?.let { draft ->

        TransactionConfirmationBottomSheet(
            resolvedDraft = draft,

            onConfirm = {
                viewModel.saveTransaction(draft)
            },

            onEdit = {
                onEdit(draft)
            },

            onDismiss = {
                viewModel.reset()
                onDismiss()
            },

            isSaving = state is AurixPersistenceState.Saving
        )

        LaunchedEffect(state) {
            if (state is AurixPersistenceState.Saved) {
                viewModel.reset()
                onSaved()
            }
        }
    }
}