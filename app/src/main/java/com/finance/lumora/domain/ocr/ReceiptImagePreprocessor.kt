package com.finance.lumora.domain.ocr

import android.net.Uri

interface ReceiptImagePreprocessor {

    suspend fun preprocess(
        imageUri: Uri
    ): Uri
}