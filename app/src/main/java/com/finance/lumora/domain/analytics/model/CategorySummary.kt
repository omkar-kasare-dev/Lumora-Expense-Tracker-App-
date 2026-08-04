package com.finance.lumora.domain.analytics.model

data class CategorySummary(
    val categoryId: Long,
    val categoryName: String,
    val icon: String,
    val color: Long,
    val totalAmount: Double,
    val percentage: Float
)
