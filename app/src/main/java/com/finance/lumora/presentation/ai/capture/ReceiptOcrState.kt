package com.finance.lumora.presentation.ai.capture

import com.finance.lumora.domain.model.ReceiptOcrResult

sealed interface ReceiptOcrState {

    data object Idle : ReceiptOcrState

    data object Processing : ReceiptOcrState

    data class Success(
        val result: ReceiptOcrResult
    ) : ReceiptOcrState

    data class Error(
        val message: String
    ) : ReceiptOcrState
}