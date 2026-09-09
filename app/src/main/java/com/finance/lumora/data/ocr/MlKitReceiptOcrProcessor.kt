package com.finance.lumora.data.ocr

import android.content.Context
import android.net.Uri
import android.util.Log
import com.finance.lumora.domain.model.ReceiptOcrResult
import com.finance.lumora.domain.ocr.ReceiptOcrProcessor
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class MlKitReceiptOcrProcessor @Inject constructor(
    @ApplicationContext private val context: Context
) : ReceiptOcrProcessor {

    private val recognizer by lazy {
        TextRecognition.getClient(
            TextRecognizerOptions.DEFAULT_OPTIONS
        )
    }

    override suspend fun processImage(
        imageUri: Uri
    ): ReceiptOcrResult =
        suspendCancellableCoroutine { continuation ->

            try {

                Log.d(
                    "MlKitOCR",
                    "Received URI: $imageUri"
                )
                val image = InputImage.fromFilePath(
                    context,
                    imageUri
                )
                Log.d(
                    "MlKitOCR",
                    "InputImage created successfully"
                )

                recognizer.process(image)
                    .addOnSuccessListener { result ->

                        Log.d(
                            "MlKitOCR",
                            "OCR completed. Text length=${result.text.length}"
                        )

                        Log.d(
                            "MlKitOCR",
                            "OCR text=${result.text}"
                        )

                        if (continuation.isActive) {
                            continuation.resume(
                                ReceiptOcrResult(
                                    rawText = result.text.trim()
                                )
                            )
                        }
                    }
                    .addOnFailureListener { exception ->

                        Log.e(
                            "MlKitOCR",
                            "ML Kit processing failed",
                            exception
                        )

                        if (continuation.isActive) {
                            continuation.resumeWithException(
                                exception
                            )
                        }
                    }

            } catch (exception: Exception) {

                if (continuation.isActive) {
                    continuation.resumeWithException(
                        exception
                    )
                }
            }
        }
}