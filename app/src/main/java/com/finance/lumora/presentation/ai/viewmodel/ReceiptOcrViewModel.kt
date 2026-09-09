package com.finance.lumora.presentation.ai.viewmodel

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.finance.lumora.domain.model.ReceiptOcrResult
import com.finance.lumora.domain.usecase.ocr.ProcessReceiptImageUseCase
import com.finance.lumora.presentation.ai.capture.ReceiptOcrState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject



@HiltViewModel
class ReceiptOcrViewModel @Inject constructor(
    private val processReceiptImageUseCase: ProcessReceiptImageUseCase
) : ViewModel() {

    private val _state =
        MutableStateFlow<ReceiptOcrState>(
            ReceiptOcrState.Idle
        )

    val state: StateFlow<ReceiptOcrState> =
        _state.asStateFlow()

    fun processReceipt(
        imageUri: Uri
    ) {
        Log.d(
            "ReceiptOCR",
            "Processing image URI: $imageUri"
        )
        viewModelScope.launch {

            _state.value =
                ReceiptOcrState.Processing

            try {

                val result =
                    processReceiptImageUseCase(imageUri)

                if (result.rawText.isBlank()) {

                    _state.value =
                        ReceiptOcrState.Error(
                            "No readable text was found on the receipt."
                        )

                } else {

                    _state.value =
                        ReceiptOcrState.Success(result)
                    Log.d(
                        "ReceiptOCR",
                        "OCR raw text: ${result.rawText}"
                    )
                }

            } catch (exception: Exception) {
                Log.e(
                    "ReceiptOCR",
                    "OCR processing failed",
                    exception
                )

                _state.value =
                    ReceiptOcrState.Error(
                        exception.message
                            ?: "Unable to read the receipt."
                    )
            }
        }
    }

    fun reset() {
        _state.value =
            ReceiptOcrState.Idle
    }
}