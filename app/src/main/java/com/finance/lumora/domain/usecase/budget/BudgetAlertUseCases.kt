package com.finance.lumora.domain.usecase.budget

import com.finance.lumora.domain.usecase.transaction.GetMonthlyExpenseUseCase
import javax.inject.Inject

// BudgetAlertUseCases.kt
data class BudgetAlertUseCases @Inject constructor(
    val evaluateBudgetAlert: EvaluateBudgetAlertUseCase,
    val getMonthlyExpenseUseCase: GetMonthlyExpenseUseCase
) {
}
