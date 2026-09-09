package com.finance.lumora.domain.ocr

import android.net.Uri
import com.finance.lumora.domain.model.ReceiptOcrResult

interface ReceiptOcrProcessor {

    suspend fun processImage(
        imageUri: Uri
    ): ReceiptOcrResult
}