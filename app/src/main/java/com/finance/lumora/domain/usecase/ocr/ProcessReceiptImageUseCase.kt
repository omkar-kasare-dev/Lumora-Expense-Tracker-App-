package com.finance.lumora.domain.usecase.ocr

import android.net.Uri
import com.finance.lumora.domain.model.ReceiptOcrResult
import com.finance.lumora.domain.ocr.ReceiptImagePreprocessor
import com.finance.lumora.domain.ocr.ReceiptOcrProcessor
import javax.inject.Inject

class ProcessReceiptImageUseCase @Inject constructor(
    private val imagePreprocessor: ReceiptImagePreprocessor,
    private val ocrProcessor: ReceiptOcrProcessor
) {

    suspend operator fun invoke(
        imageUri: Uri
    ): ReceiptOcrResult {

        val processedImageUri =
            imagePreprocessor.preprocess(imageUri)

        return ocrProcessor.processImage(
            processedImageUri
        )
    }
}