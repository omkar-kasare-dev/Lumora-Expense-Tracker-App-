package com.finance.lumora.presentation.auth.viewmodel



import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.finance.lumora.domain.repository.AuthRepository
import com.finance.lumora.presentation.auth.intent.ForgotPasswordEvent
import com.finance.lumora.presentation.auth.state.ForgotPasswordState
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(

    private val repository: AuthRepository

) : ViewModel() {

    private val _uiState = MutableStateFlow(
        ForgotPasswordState()
    )

    val uiState: StateFlow<ForgotPasswordState> =
        _uiState.asStateFlow()

    fun onEvent(
        event: ForgotPasswordEvent
    ) {

        when (event) {

            is ForgotPasswordEvent.EmailChanged -> {

                _uiState.update {

                    it.copy(

                        email = event.email,

                        emailError = null

                    )

                }

            }

            ForgotPasswordEvent.SendResetEmailClicked -> {

                sendResetEmail()

            }

            ForgotPasswordEvent.ClearError -> {

                _uiState.update {

                    it.copy(
                        errorMessage = null
                    )

                }

            }

            ForgotPasswordEvent.ResetSuccessState -> {

                _uiState.update {

                    it.copy(

                        resetEmailSent = false,

                        sentEmail = ""

                    )

                }

            }

        }

    }

    /**
     * Sends password reset email.
     */
    private fun sendResetEmail() {

        val email = _uiState.value.email.trim()

        // Validation

        if (email.isBlank()) {

            _uiState.update {

                it.copy(

                    emailError = "Email is required."

                )

            }

            return

        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {

            _uiState.update {

                it.copy(

                    emailError = "Enter a valid email address."

                )

            }

            return

        }

        viewModelScope.launch {

            _uiState.update {

                it.copy(

                    isLoading = true,

                    emailError = null,

                    errorMessage = null

                )

            }

            repository
                .sendPasswordReset(email)
                .onSuccess {

                    _uiState.update {

                        it.copy(

                            isLoading = false,

                            resetEmailSent = true,

                            sentEmail = email

                        )

                    }

                }
                .onFailure { exception ->

                    _uiState.update {

                        it.copy(

                            isLoading = false,

                            errorMessage = exception.message
                                ?: "Unable to send reset email."

                        )

                    }

                }

        }

    }

}