package com.finance.lumora.data.voice

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import com.finance.lumora.domain.voice.VoiceInputProcessor
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AndroidVoiceInputProcessor @Inject constructor(
    @ApplicationContext private val context: Context
) : VoiceInputProcessor {

    private var speechRecognizer: SpeechRecognizer? = null

    override fun startListening(
        onPartialResult: (String) -> Unit,
        onFinalResult: (String) -> Unit,
        onError: (Int) -> Unit
    ) {
        if (!SpeechRecognizer.isRecognitionAvailable(context)) {
            onError(SpeechRecognizer.ERROR_CLIENT)
            return
        }

        speechRecognizer?.destroy()

        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(context).apply {

            setRecognitionListener(
                object : RecognitionListener {

                    override fun onReadyForSpeech(params: Bundle?) {
                    }

                    override fun onBeginningOfSpeech() {
                    }

                    override fun onRmsChanged(rmsdB: Float) {
                    }

                    override fun onBufferReceived(buffer: ByteArray?) {
                    }

                    override fun onEndOfSpeech() {
                    }

                    override fun onError(error: Int) {
                        onError(error)
                    }

                    override fun onResults(results: Bundle?) {
                        val matches =
                            results?.getStringArrayList(
                                SpeechRecognizer.RESULTS_RECOGNITION
                            )

                        val finalText = matches
                            ?.firstOrNull()
                            ?.trim()
                            .orEmpty()

                        if (finalText.isNotBlank()) {
                            onFinalResult(finalText)
                        }
                    }

                    override fun onPartialResults(
                        partialResults: Bundle?
                    ) {
                        val matches =
                            partialResults?.getStringArrayList(
                                SpeechRecognizer.RESULTS_RECOGNITION
                            )

                        val partialText = matches
                            ?.firstOrNull()
                            ?.trim()
                            .orEmpty()

                        if (partialText.isNotBlank()) {
                            onPartialResult(partialText)
                        }
                    }

                    override fun onEvent(
                        eventType: Int,
                        params: Bundle?
                    ) {
                    }
                }
            )

            val intent = Intent(
                RecognizerIntent.ACTION_RECOGNIZE_SPEECH
            ).apply {
                putExtra(
                    RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                    RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
                )

                putExtra(
                    RecognizerIntent.EXTRA_PARTIAL_RESULTS,
                    true
                )

                putExtra(
                    RecognizerIntent.EXTRA_MAX_RESULTS,
                    1
                )
            }

            startListening(intent)
        }
    }

    override fun stopListening() {
        speechRecognizer?.stopListening()
    }

    override fun cancelListening() {
        speechRecognizer?.cancel()
    }

    fun release() {
        speechRecognizer?.destroy()
        speechRecognizer = null
    }
}