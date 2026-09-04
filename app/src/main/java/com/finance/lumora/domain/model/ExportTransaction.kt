package com.finance.lumora.domain.model



import com.finance.lumora.data.local.enums.TransactionType

data class ExportTransaction(
    val id: Long,
    val amount: Double,
    val type: TransactionType,
    val categoryName: String,
    val note: String?,
    val transactionDate: Long,
    val createdAt: Long,
    val updatedAt: Long
)
