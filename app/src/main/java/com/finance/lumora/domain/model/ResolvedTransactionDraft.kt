package com.finance.lumora.domain.model

data class ResolvedTransactionDraft(
    val draft: DraftTransaction,
    val category: Category?
)