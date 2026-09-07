package com.finance.lumora.domain.usecase.ai

/**
 * Main use of purpose
 * fetches the required insight
 */


import com.finance.lumora.domain.model.ai.AurixInsightResult
import com.finance.lumora.domain.model.ai.AurixInsightType
import javax.inject.Inject

class ResolveAurixInsightUseCase @Inject constructor(
    private val getAurixExpenseTrendUseCase: GetAurixExpenseTrendUseCase,
    private val getBudgetInsightUseCase: GetBudgetInsightUseCase
) {

    suspend operator fun invoke(
        insightType: AurixInsightType
    ): AurixInsightResult {

        return when (insightType) {

            AurixInsightType.EXPENSE_TREND -> {
                resolveExpenseTrend()
            }

            AurixInsightType.BUDGET -> {
                resolveBudget()
            }

            AurixInsightType.NONE -> {
                AurixInsightResult()
            }
        }
    }

    private suspend fun resolveExpenseTrend(): AurixInsightResult {

        val expenseTrendInsight =
            getAurixExpenseTrendUseCase()

        return AurixInsightResult(
            expenseTrendInsight = expenseTrendInsight
        )
    }

    private suspend fun resolveBudget(): AurixInsightResult {

        val budgetInsight =
            getBudgetInsightUseCase()

        return AurixInsightResult(
            budgetInsight = budgetInsight
        )
    }
}