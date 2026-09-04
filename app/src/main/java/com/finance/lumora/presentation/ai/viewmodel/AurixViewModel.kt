package com.finance.lumora.presentation.ai.viewmodel



import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.finance.lumora.domain.usecase.ai.AskAurixUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class AurixViewModel @Inject constructor(
    private val askAurixUseCase: AskAurixUseCase
) : ViewModel() {

    private val _response =
        MutableStateFlow("")

    val response: StateFlow<String> =
        _response.asStateFlow()

    private val _isLoading =
        MutableStateFlow(false)

    val isLoading: StateFlow<Boolean> =
        _isLoading.asStateFlow()

    private val _error =
        MutableStateFlow<String?>(null)

    val error: StateFlow<String?> =
        _error.asStateFlow()

    fun askQuestion(question: String) {

        val trimmedQuestion =
            question.trim()

        if (trimmedQuestion.isBlank()) {
            return
        }

        viewModelScope.launch {

            _isLoading.value = true
            _error.value = null
            _response.value = ""

            try {

                val result =
                    askAurixUseCase(
                        trimmedQuestion
                    )

                _response.value = result

            } catch (exception: Exception) {

                _error.value =
                    exception.message
                        ?: "Unable to get a response from AURIX."

            } finally {

                _isLoading.value = false
            }
        }
    }

    fun clearResponse() {
        _response.value = ""
        _error.value = null
    }
}