package com.finance.lumora.domain.usecase.ai


import com.finance.lumora.domain.model.ai.ExpenseTrendInsight
import javax.inject.Inject

class GetExpenseTrendInsightUseCase @Inject constructor(
    private val compareFinancePeriodsUseCase: CompareFinancePeriodsUseCase,
    private val analyzeExpenseTrendUseCase: AnalyzeExpenseTrendUseCase
) {

    suspend operator fun invoke(): ExpenseTrendInsight {

        val comparison = compareFinancePeriodsUseCase()

        val trend = analyzeExpenseTrendUseCase()

        return ExpenseTrendInsight(
            currentExpense = comparison.currentExpense,
            previousExpense = comparison.previousExpense,
            expenseDifference = comparison.expenseDifference,
            expenseChangePercentage = comparison.expenseChangePercentage,
            trend = trend
        )
    }
}

