package com.finance.lumora.domain.usecase.ai



import com.finance.lumora.domain.model.ai.SpendingConcentrationInsight
import com.finance.lumora.domain.model.ai.FinanceContext
import javax.inject.Inject

class GetSpendingConcentrationInsightUseCase @Inject constructor() {

    operator fun invoke(
        financeContext: FinanceContext
    ): SpendingConcentrationInsight? {

        val categorySummaries =
            financeContext.categorySummaries

        if (categorySummaries.isEmpty()) {
            return null
        }

        val topCategory =
            categorySummaries.maxByOrNull {
                it.totalAmount
            } ?: return null

        return SpendingConcentrationInsight(
            topCategoryName = topCategory.categoryName,
            topCategoryAmount = topCategory.totalAmount,
            topCategoryPercentage = topCategory.percentage,
            totalExpense = financeContext.totalExpense
        )
    }
}