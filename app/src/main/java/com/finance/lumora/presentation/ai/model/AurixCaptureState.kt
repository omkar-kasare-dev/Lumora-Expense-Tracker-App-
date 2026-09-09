package com.finance.lumora.presentation.ai.model

import com.finance.lumora.domain.model.DraftTransaction

sealed interface AurixCaptureState {

    data object Idle : AurixCaptureState

    data object Processing : AurixCaptureState

    data class DraftReady(
        val draft: DraftTransaction
    ) : AurixCaptureState

    data class Error(
        val message: String
    ) : AurixCaptureState
}