package com.finance.lumora.presentation.ai.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.finance.lumora.data.voice.AndroidVoiceInputProcessor

import com.finance.lumora.domain.model.CaptureSource
import com.finance.lumora.domain.model.ResolvedTransactionDraft
import com.finance.lumora.domain.usecase.category.ResolveTransactionCategoryUseCase
import com.finance.lumora.domain.voice.VoiceInputProcessor
import com.finance.lumora.presentation.ai.model.VoiceInputState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import androidx.lifecycle.viewModelScope
import com.finance.lumora.domain.model.ai.TransactionCaptureParser
import javax.inject.Inject

@HiltViewModel
class VoiceCaptureViewModel @Inject constructor(
    private val voiceInputProcessor: VoiceInputProcessor,
    private val transactionCaptureParser: TransactionCaptureParser,
    private val resolveTransactionCategoryUseCase: ResolveTransactionCategoryUseCase
) : ViewModel() {

    private val _state =
        MutableStateFlow<VoiceInputState>(
            VoiceInputState.Idle
        )

    val state: StateFlow<VoiceInputState> =
        _state.asStateFlow()

    private val _resolvedDraft =
        MutableStateFlow<ResolvedTransactionDraft?>(null)

    val resolvedDraft: StateFlow<ResolvedTransactionDraft?> =
        _resolvedDraft.asStateFlow()

    fun startListening() {

        _resolvedDraft.value = null

        _state.value = VoiceInputState.Listening

        voiceInputProcessor.startListening(
            onPartialResult = { text ->

                _state.value = VoiceInputState.Transcript(
                    text = text,
                    isFinal = false
                )
            },
            onFinalResult = { text ->

                _state.value = VoiceInputState.Transcript(
                    text = text,
                    isFinal = true
                )

                parseTranscript(text)
            },
            onError = { errorCode ->

                _state.value = VoiceInputState.Error(
                    message = mapSpeechRecognizerError(errorCode)
                )
            }
        )
    }

    fun stopListening() {
        voiceInputProcessor.stopListening()
    }

    fun cancelListening() {
        voiceInputProcessor.cancelListening()

        _resolvedDraft.value = null
        _state.value = VoiceInputState.Idle
    }

    fun reset() {
        _resolvedDraft.value = null
        _state.value = VoiceInputState.Idle
    }

    private fun parseTranscript(transcript: String) {

        if (transcript.isBlank()) {
            _state.value = VoiceInputState.Error(
                "No speech was detected."
            )
            return
        }

        viewModelScope.launch {

            _state.value = VoiceInputState.Processing

            try {

                val draft = transactionCaptureParser.parse(
                    input = transcript,
                    source = CaptureSource.VOICE
                )

                val category =
                    resolveTransactionCategoryUseCase(
                        draft.categoryName
                    )

                Log.d(
                    "VoiceCapture",
                    """
    Transcript: $transcript
    Amount: ${draft.amount}
    Currency: ${draft.currency}
    Merchant: ${draft.merchantName}
    Category: ${draft.categoryName}
    Resolved Category ID: ${category?.id}
    Resolved Category Name: ${category?.name}
    Date: ${draft.transactionDate}
    Source: ${draft.source}
    """.trimIndent()
                )

                _resolvedDraft.value =
                    ResolvedTransactionDraft(
                        draft = draft,
                        category = category
                    )

            } catch (exception: Exception) {

                _state.value = VoiceInputState.Error(
                    exception.message
                        ?: "Unable to understand the transaction."
                )
            }
        }
    }

    private fun mapSpeechRecognizerError(
        errorCode: Int
    ): String {

        return when (errorCode) {

            android.speech.SpeechRecognizer.ERROR_AUDIO ->
                "Unable to access the microphone."

            android.speech.SpeechRecognizer.ERROR_CLIENT ->
                "Voice recognition is unavailable."

            android.speech.SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS ->
                "Microphone permission is required."

            android.speech.SpeechRecognizer.ERROR_NETWORK ->
                "Network error while processing speech."

            android.speech.SpeechRecognizer.ERROR_NETWORK_TIMEOUT ->
                "Speech recognition timed out."

            android.speech.SpeechRecognizer.ERROR_NO_MATCH ->
                "I couldn't understand what you said."

            android.speech.SpeechRecognizer.ERROR_RECOGNIZER_BUSY ->
                "Voice recognition is currently busy."

            android.speech.SpeechRecognizer.ERROR_SPEECH_TIMEOUT ->
                "No speech was detected."

            else ->
                "Unable to recognize speech."
        }
    }

    override fun onCleared() {
        super.onCleared()

        if (voiceInputProcessor is AndroidVoiceInputProcessor) {
            voiceInputProcessor.release()
        }
    }
}