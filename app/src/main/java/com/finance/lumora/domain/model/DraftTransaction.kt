package com.finance.lumora.domain.model

data class DraftTransaction(
    val amount: Double,
    val currency: String?,
    val merchantName: String?,
    val categoryName: String?,
    val transactionDate: String,
    val source: CaptureSource
)

enum class CaptureSource {
    RECEIPT_OCR,
    VOICE
}