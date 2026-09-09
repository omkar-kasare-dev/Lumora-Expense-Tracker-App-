package com.finance.lumora.presentation.ai.model

import com.finance.lumora.domain.model.ResolvedTransactionDraft

data class TransactionConfirmationState(
    val draft: ResolvedTransactionDraft,
    val amount: String,
    val categoryName: String,
    val merchantName: String,
    val date: String
)