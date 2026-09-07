package com.finance.lumora.domain.usecase.ai



import com.finance.lumora.domain.model.ai.FinanceComparisonResult
import javax.inject.Inject

class GetFinanceComparisonUseCase @Inject constructor(
    private val compareFinancePeriodsUseCase: CompareFinancePeriodsUseCase
) {

    suspend operator fun invoke(): FinanceComparisonResult {

        val comparison = compareFinancePeriodsUseCase()

        val summary = buildSummary(
            comparison = comparison
        )

        return FinanceComparisonResult(
            comparison = comparison,
            summary = summary
        )
    }

    private fun buildSummary(
        comparison: com.finance.lumora.domain.model.ai.FinanceComparison
    ): String {

        return when {
            comparison.expenseDifference > 0.0 -> {
                "Your expenses increased by " +
                        formatPercentage(
                            comparison.expenseChangePercentage
                        ) +
                        "% compared to the previous month."
            }

            comparison.expenseDifference < 0.0 -> {
                "Your expenses decreased by " +
                        formatPercentage(
                            kotlin.math.abs(
                                comparison.expenseChangePercentage
                            )
                        ) +
                        "% compared to the previous month."
            }

            else -> {
                "Your expenses remained the same compared to the previous month."
            }
        }
    }

    private fun formatPercentage(
        percentage: Double
    ): String {
        return String.format(
            java.util.Locale.US,
            "%.2f",
            percentage
        )
    }
}