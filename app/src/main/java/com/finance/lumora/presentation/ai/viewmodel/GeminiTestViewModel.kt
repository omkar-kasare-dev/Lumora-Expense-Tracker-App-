package com.finance.lumora.presentation.ai.viewmodel



import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.finance.lumora.domain.usecase.ai.GenerateGeminiResponseUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class GeminiTestViewModel @Inject constructor(
    private val generateGeminiResponseUseCase: GenerateGeminiResponseUseCase
) : ViewModel() {

    private val _response = MutableStateFlow("")
    val response: StateFlow<String> = _response.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    fun testGemini() {

        viewModelScope.launch {

            _isLoading.value = true
            _error.value = null
            _response.value = ""

            try {

                val result =
                    generateGeminiResponseUseCase(
                        """
                        You are AURIX, the personal AI assistant
                        inside Lumora, a smart expense tracker.

                        This is a connection test.

                        Reply with exactly:
                        "AURIX Gemini connection successful."
                        """.trimIndent()
                    )

                _response.value = result

            } catch (exception: Exception) {

                _error.value =
                    exception.message
                        ?: "Unknown Gemini error."

            } finally {

                _isLoading.value = false
            }
        }
    }
}