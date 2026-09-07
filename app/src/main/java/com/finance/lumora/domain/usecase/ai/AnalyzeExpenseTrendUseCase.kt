package com.finance.lumora.domain.usecase.ai



import com.finance.lumora.domain.model.ai.ExpenseTrend
import javax.inject.Inject

class AnalyzeExpenseTrendUseCase @Inject constructor(
    private val compareFinancePeriodsUseCase: CompareFinancePeriodsUseCase
) {

    suspend operator fun invoke(): ExpenseTrend {

        val comparison = compareFinancePeriodsUseCase()

        return when {
            comparison.expenseDifference > 0.0 -> {
                ExpenseTrend.INCREASING
            }

            comparison.expenseDifference < 0.0 -> {
                ExpenseTrend.DECREASING
            }

            else -> {
                ExpenseTrend.STABLE
            }
        }
    }
}