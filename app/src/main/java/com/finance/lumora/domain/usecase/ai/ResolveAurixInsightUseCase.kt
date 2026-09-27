package com.finance.lumora.domain.usecase.ai

import com.finance.lumora.domain.model.ai.AurixInsightResult
import com.finance.lumora.domain.model.ai.AurixInsightType
import com.finance.lumora.domain.model.ai.FinanceContext
import com.finance.lumora.domain.model.ai.FinancePeriod
import javax.inject.Inject

class ResolveAurixInsightUseCase @Inject constructor(
    private val getAurixExpenseTrendUseCase: GetAurixExpenseTrendUseCase,
    private val getBudgetInsightUseCase: GetBudgetInsightUseCase,
    private val getSpendingConcentrationInsightUseCase: GetSpendingConcentrationInsightUseCase
) {

    suspend operator fun invoke(
        insightType: AurixInsightType,
        financeContext: FinanceContext,
        period: FinancePeriod
    ): AurixInsightResult {

        return when (insightType) {

            AurixInsightType.EXPENSE_TREND -> {
                resolveExpenseTrend(period)
            }

            AurixInsightType.BUDGET -> {
                resolveBudget(period)
            }

            AurixInsightType.SPENDING_CONCENTRATION -> {
                resolveSpendingConcentration(
                    financeContext = financeContext
                )
            }

            AurixInsightType.NONE -> {
                AurixInsightResult()
            }
        }
    }

    /**
     * Expense trend is inherently a "current vs previous month"
     * comparison - GetAurixExpenseTrendUseCase has no period
     * parameter of its own. Attaching it for a question about a
     * specific past month, today, or this week would silently
     * compare the wrong periods, so it's only resolved when the
     * question is genuinely about the current month.
     */
    private suspend fun resolveExpenseTrend(
        period: FinancePeriod
    ): AurixInsightResult {

        if (period != FinancePeriod.CURRENT_MONTH) {
            return AurixInsightResult()
        }

        val expenseTrendInsight =
            getAurixExpenseTrendUseCase()

        return AurixInsightResult(
            expenseTrendInsight = expenseTrendInsight
        )
    }

    /**
     * Budget is a single ongoing setting, not a per-period record -
     * there's no "August's budget" to compare against. Matches the
     * same CURRENT_MONTH-only gating FinanceContextBuilder already
     * applies to FinanceContext.monthlyBudget, so both sections of
     * the prompt stay consistent with each other.
     */
    private suspend fun resolveBudget(
        period: FinancePeriod
    ): AurixInsightResult {

        if (period != FinancePeriod.CURRENT_MONTH) {
            return AurixInsightResult()
        }

        val budgetInsight =
            getBudgetInsightUseCase()

        return AurixInsightResult(
            budgetInsight = budgetInsight
        )
    }

    private suspend fun resolveSpendingConcentration(
        financeContext: FinanceContext
    ): AurixInsightResult {

        val spendingConcentrationInsight =
            getSpendingConcentrationInsightUseCase(
                financeContext = financeContext
            )

        return AurixInsightResult(
            spendingConcentrationInsight = spendingConcentrationInsight
        )
    }
}