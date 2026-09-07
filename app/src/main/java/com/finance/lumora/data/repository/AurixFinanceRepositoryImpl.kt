package com.finance.lumora.data.repository



import com.finance.lumora.domain.model.ai.ExpenseTrendInsight
import com.finance.lumora.domain.model.ai.FinanceComparisonResult
import com.finance.lumora.domain.repository.AurixFinanceRepository
import com.finance.lumora.domain.usecase.ai.GetExpenseTrendInsightUseCase
import com.finance.lumora.domain.usecase.ai.GetFinanceComparisonUseCase
import javax.inject.Inject

class AurixFinanceRepositoryImpl @Inject constructor(
    private val getFinanceComparisonUseCase: GetFinanceComparisonUseCase,
    private val getExpenseTrendInsightUseCase: GetExpenseTrendInsightUseCase
) : AurixFinanceRepository {

    override suspend fun getMonthlyExpenseComparison(): FinanceComparisonResult {
        return getFinanceComparisonUseCase()
    }

    override suspend fun getExpenseTrendInsight(): ExpenseTrendInsight { return getExpenseTrendInsightUseCase() }
}