package com.finance.lumora.domain.repository



import com.finance.lumora.domain.model.ai.ExpenseTrendInsight
import com.finance.lumora.domain.model.ai.FinanceComparisonResult

interface AurixFinanceRepository {

    suspend fun getMonthlyExpenseComparison(): FinanceComparisonResult

    suspend fun getExpenseTrendInsight(): ExpenseTrendInsight
}