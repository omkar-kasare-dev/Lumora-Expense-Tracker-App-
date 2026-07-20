package com.finance.lumora.domain.model

import com.finance.lumora.data.local.enums.TransactionType

/**
 * Domain model representing a financial transaction.
 *
 * This model is used throughout the domain and presentation
 * layers and remains independent of Room.
 */
data class Transaction(

    val id: Long = 0L,

    val amount: Double,

    val type: TransactionType,

    val categoryId: Long,

    val note: String? = null,

    val transactionDate: Long,

    val createdAt: Long = System.currentTimeMillis(),

    val updatedAt: Long = System.currentTimeMillis()
)