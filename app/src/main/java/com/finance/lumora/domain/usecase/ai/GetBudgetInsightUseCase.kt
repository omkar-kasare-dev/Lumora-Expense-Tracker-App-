package com.finance.lumora.domain.usecase.ai

import com.finance.lumora.domain.model.ai.BudgetInsight
import com.finance.lumora.domain.usecase.transaction.GetMonthlyExpenseUseCase
import kotlinx.coroutines.flow.first
import javax.inject.Inject

import com.finance.lumora.domain.usecase.settings.GetBudgetUseCase

class GetBudgetInsightUseCase @Inject constructor(
    private val getMonthlyExpenseUseCase: GetMonthlyExpenseUseCase,
    private val getBudgetUseCase: GetBudgetUseCase
) {

    suspend operator fun invoke(): BudgetInsight {

        val currentExpense =
            getMonthlyExpenseUseCase().first()

        val monthlyBudget =
            getBudgetUseCase().first()

        val remainingBudget =
            monthlyBudget - currentExpense

        val budgetUsagePercentage =
            if (monthlyBudget > 0.0) {
                (currentExpense / monthlyBudget) * 100.0
            } else {
                0.0
            }

        val isBudgetExceeded =
            monthlyBudget > 0.0 &&
                    currentExpense > monthlyBudget

        return BudgetInsight(
            monthlyBudget = monthlyBudget,
            currentExpense = currentExpense,
            remainingBudget = remainingBudget,
            budgetUsagePercentage = budgetUsagePercentage,
            isBudgetExceeded = isBudgetExceeded
        )
    }
}