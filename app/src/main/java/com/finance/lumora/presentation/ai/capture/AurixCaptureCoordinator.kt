package com.finance.lumora.presentation.ai.capture

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.finance.lumora.domain.model.ResolvedTransactionDraft

@Composable
fun rememberAurixCaptureCoordinator(): AurixCaptureCoordinator {
    return remember {
        AurixCaptureCoordinator()
    }
}

class AurixCaptureCoordinator {

    var showReceiptCamera by mutableStateOf(false)
        private set

    var showVoiceCapture by mutableStateOf(false)
        private set

    var resolvedDraft by mutableStateOf<ResolvedTransactionDraft?>(null)
        private set

    fun openReceiptCamera() {
        showVoiceCapture = false
        showReceiptCamera = true
    }

    fun openVoiceCapture() {
        showReceiptCamera = false
        showVoiceCapture = true
    }

    fun onDraftReady(
        draft: ResolvedTransactionDraft
    ) {
        showReceiptCamera = false
        showVoiceCapture = false
        resolvedDraft = draft
    }

    fun dismissReceiptCamera() {
        showReceiptCamera = false
    }

    fun dismissVoiceCapture() {
        showVoiceCapture = false
    }

    fun dismissConfirmation() {
        resolvedDraft = null
    }

    fun clear() {
        showReceiptCamera = false
        showVoiceCapture = false
        resolvedDraft = null
    }
}