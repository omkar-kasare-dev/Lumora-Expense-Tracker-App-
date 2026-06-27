package com.finance.lumora.domain.model


data class Expense(
    val id: Long = 0L,
    val title: String,
    val amount: Double,
    val categoryId: Long,
    val categoryName: String = "",
    val note: String = "",
    val date: Long,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)