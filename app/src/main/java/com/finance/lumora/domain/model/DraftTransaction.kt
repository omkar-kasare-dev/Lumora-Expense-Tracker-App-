package com.finance.lumora.domain.model

import com.finance.lumora.data.local.enums.TransactionType

data class DraftTransaction(
    val amount: Double,
    val currency: String?,
    val merchantName: String?,
    val categoryName: String?,
    val transactionDate: String,
    val type: TransactionType,
    val source: CaptureSource
)

enum class CaptureSource {
    RECEIPT_OCR,
    VOICE
}