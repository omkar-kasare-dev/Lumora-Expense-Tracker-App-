package com.finance.lumora.domain.voice

interface VoiceInputProcessor {

    fun startListening(
        onPartialResult: (String) -> Unit,
        onFinalResult: (String) -> Unit,
        onError: (Int) -> Unit
    )

    fun stopListening()

    fun cancelListening()
}