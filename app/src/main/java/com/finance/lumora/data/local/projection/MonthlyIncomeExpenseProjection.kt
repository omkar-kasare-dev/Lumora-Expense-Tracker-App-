package com.finance.lumora.data.local.projection

data class MonthlyIncomeExpenseProjection(
    val year: Int,
    val month: Int,
    val income: Double,
    val expense: Double
)