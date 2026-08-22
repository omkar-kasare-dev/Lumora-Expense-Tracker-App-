package com.finance.lumora.domain.usecase.budget

import com.finance.lumora.domain.model.BudgetAlertLevel
import com.finance.lumora.domain.usecase.transaction.GetMonthlyExpenseUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class EvaluateBudgetAlertUseCase @Inject constructor(
    private val getMonthlyExpenseUseCase: GetMonthlyExpenseUseCase,
    private val budgetAlertEvaluator: BudgetAlertEvaluator
) {
    operator fun invoke(monthlyBudget: Double): Flow<BudgetAlertLevel> {
        return getMonthlyExpenseUseCase().map { expense ->
            // HARDCODED FOR TESTING: Force a budget of 8000.0 instead of 0.0/unset budget
            val testBudget = if (monthlyBudget <= 0.0) 8000.0 else monthlyBudget

            budgetAlertEvaluator(
                monthlyExpense = expense,
                monthlyBudget = testBudget
            )
        }
    }
}





