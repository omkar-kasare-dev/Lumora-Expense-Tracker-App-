package com.finance.lumora.data.ai.model


import kotlinx.serialization.Serializable

@Serializable
data class TransactionCaptureResponse(
    val amount: Double? = null,
    val currency: String? = null,
    val merchantName: String? = null,
    val categoryName: String? = null,
    val transactionDate: String? = null
)