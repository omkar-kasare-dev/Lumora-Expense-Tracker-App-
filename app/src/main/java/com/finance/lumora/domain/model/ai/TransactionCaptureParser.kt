package com.finance.lumora.domain.model.ai


import com.finance.lumora.domain.model.CaptureSource
import com.finance.lumora.domain.model.DraftTransaction

interface TransactionCaptureParser {

    suspend fun parse(
        input: String,
        source: CaptureSource
    ): DraftTransaction
}