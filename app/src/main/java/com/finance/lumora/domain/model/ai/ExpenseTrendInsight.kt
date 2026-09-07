package com.finance.lumora.domain.model.ai



data class ExpenseTrendInsight(
    val currentExpense: Double,
    val previousExpense: Double,
    val expenseDifference: Double,
    val expenseChangePercentage: Double,
    val trend: ExpenseTrend
)