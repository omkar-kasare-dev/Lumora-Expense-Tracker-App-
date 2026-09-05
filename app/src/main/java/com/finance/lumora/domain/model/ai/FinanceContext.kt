package com.finance.lumora.domain.model.ai


data class FinanceContext(
    val period: String,
    val currency: String,
    val totalIncome: Double,
    val totalExpense: Double,
    val balance: Double,
    val transactionCount: Int,
    val monthlyBudget: Double?,
    val budgetRemaining: Double?,
    val budgetUsagePercentage: Double?,
    val categorySummaries: List<FinanceCategorySummary>
)

data class FinanceCategorySummary(
    val categoryName: String,
    val totalAmount: Double,
    val percentage: Double
)