package com.finance.lumora.data.local.projection

data class CategoryTotalProjection(
    val categoryId: Long,
    val categoryName: String,
    val icon: String,
    val color: Long,
    val totalAmount: Double
)