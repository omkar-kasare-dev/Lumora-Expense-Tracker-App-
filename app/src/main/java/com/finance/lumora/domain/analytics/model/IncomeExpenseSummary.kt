package com.finance.lumora.domain.analytics.model

data class IncomeExpenseSummary(
    val year: Int,
    val month: Int,
    val income: Double,
    val expense: Double
)