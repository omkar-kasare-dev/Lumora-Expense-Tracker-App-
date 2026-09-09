package com.finance.lumora.presentation.ai.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.finance.lumora.domain.model.CaptureSource
import com.finance.lumora.domain.model.ReceiptOcrResult
import com.finance.lumora.domain.model.ResolvedTransactionDraft
import com.finance.lumora.domain.model.ai.TransactionCaptureParser
import com.finance.lumora.domain.usecase.category.ResolveTransactionCategoryUseCase
import com.finance.lumora.presentation.ai.model.AurixCaptureState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReceiptCaptureViewModel @Inject constructor(
    private val transactionCaptureParser: TransactionCaptureParser,
    private val resolveTransactionCategoryUseCase: ResolveTransactionCategoryUseCase
) : ViewModel() {

    private val _state =
        MutableStateFlow<AurixCaptureState>(
            AurixCaptureState.Idle
        )

    val state: StateFlow<AurixCaptureState> =
        _state.asStateFlow()

    private val _resolvedDraft =
        MutableStateFlow<ResolvedTransactionDraft?>(null)

    val resolvedDraft: StateFlow<ResolvedTransactionDraft?> =
        _resolvedDraft.asStateFlow()

    fun processOcrResult(
        ocrResult: ReceiptOcrResult
    ) {
        if (ocrResult.rawText.isBlank()) {
            _state.value =
                AurixCaptureState.Error(
                    "No readable text was found on the receipt."
                )
            return
        }

        _resolvedDraft.value = null

        viewModelScope.launch {

            _state.value =
                AurixCaptureState.Processing

            try {

                val draft =
                    transactionCaptureParser.parse(
                        input = ocrResult.rawText,
                        source = CaptureSource.RECEIPT_OCR
                    )

                val category =
                    resolveTransactionCategoryUseCase(
                        draft.categoryName
                    )

                _resolvedDraft.value =
                    ResolvedTransactionDraft(
                        draft = draft,
                        category = category
                    )

                _state.value =
                    AurixCaptureState.DraftReady(
                        draft = draft
                    )

            } catch (exception: Exception) {

                _state.value =
                    AurixCaptureState.Error(
                        exception.message
                            ?: "Unable to understand the receipt."
                    )
            }
        }
    }

    fun reset() {
        _resolvedDraft.value = null
        _state.value = AurixCaptureState.Idle
    }
}