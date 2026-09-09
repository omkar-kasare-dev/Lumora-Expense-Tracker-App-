package com.finance.lumora.presentation.ai.model

sealed interface VoiceInputState {

    data object Idle : VoiceInputState

    data object Listening : VoiceInputState

    data object Processing : VoiceInputState

    data class Transcript(
        val text: String,
        val isFinal: Boolean
    ) : VoiceInputState

    data class Error(
        val message: String
    ) : VoiceInputState
}