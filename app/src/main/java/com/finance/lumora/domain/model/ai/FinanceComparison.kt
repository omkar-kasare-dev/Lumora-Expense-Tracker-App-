package com.finance.lumora.domain.model.ai



data class FinanceComparison(
    val currentPeriod: String,
    val previousPeriod: String,
    val currentExpense: Double,
    val previousExpense: Double,
    val expenseDifference: Double,
    val expenseChangePercentage: Double
)