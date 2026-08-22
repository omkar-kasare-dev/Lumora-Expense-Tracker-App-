package com.finance.lumora.presentation.settings.state

data class SetBudgetUiState(
    val budgetInput: String = "",
    val errorMessage: String? = null,
    val isSaved: Boolean = false
)