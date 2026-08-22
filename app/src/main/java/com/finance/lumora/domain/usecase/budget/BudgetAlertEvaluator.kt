package com.finance.lumora.domain.usecase.budget

import com.finance.lumora.domain.model.BudgetAlertLevel
import javax.inject.Inject

/**
 * Evaluates the current monthly spending against
 * the configured monthly budget.
 *
 * This class belongs to the Domain layer and therefore
 * contains no Android-specific notification logic.
 */
class BudgetAlertEvaluator @Inject constructor() {

    companion object {
        /**
         * Spending percentage at which the first
         * budget warning is triggered.
         */
        private const val WARNING_THRESHOLD = 0.80

        /**
         * Spending percentage at which the critical
         * budget warning is triggered.
         */
        private const val CRITICAL_THRESHOLD = 0.90

        /**
         * Spending percentage at which the budget is
         * considered exceeded.
         */
        private const val EXCEEDED_THRESHOLD = 1.00
    }


    operator fun invoke(
        monthlyExpense: Double,
        monthlyBudget: Double
    ): BudgetAlertLevel {

        // No valid budget means there is nothing to evaluate.
        if (monthlyBudget <= 0.0) {
            return BudgetAlertLevel.NONE
        }

        // Negative expense should never produce a budget alert.
        if (monthlyExpense < 0.0) {
            return BudgetAlertLevel.NONE
        }

        val spendingRatio =
            monthlyExpense / monthlyBudget

        return when {

            spendingRatio >= EXCEEDED_THRESHOLD ->
                BudgetAlertLevel.EXCEEDED

            spendingRatio >= CRITICAL_THRESHOLD ->
                BudgetAlertLevel.CRITICAL

            spendingRatio >= WARNING_THRESHOLD ->
                BudgetAlertLevel.WARNING

            else ->
                BudgetAlertLevel.NONE
        }
    }
}

