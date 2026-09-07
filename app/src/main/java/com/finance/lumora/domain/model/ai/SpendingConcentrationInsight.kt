package com.finance.lumora.domain.model.ai



data class SpendingConcentrationInsight(
    val topCategoryName: String,
    val topCategoryAmount: Double,
    val topCategoryPercentage: Double,
    val totalExpense: Double
)