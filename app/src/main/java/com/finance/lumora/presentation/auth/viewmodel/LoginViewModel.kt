package com.finance.lumora.presentation.auth.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.finance.lumora.domain.repository.AuthRepository
import com.finance.lumora.domain.repository.UserRepository
import com.finance.lumora.domain.usecase.auth.GetUserProfileUseCase
import com.finance.lumora.presentation.auth.intent.LoginEvent
import com.finance.lumora.presentation.auth.state.LoginUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository,
    private val getUserProfile: GetUserProfileUseCase

) : ViewModel() {

    // -------------------------------
    // UI State
    private val _uiState = MutableStateFlow(LoginUiState())

    val uiState: StateFlow<LoginUiState> =
        _uiState.asStateFlow()

    // -------------------------------
    // Event Handler
    fun onEvent(event: LoginEvent) {

        when (event) {

            is LoginEvent.EmailChanged -> {

                _uiState.update {

                    it.copy(
                        email = event.email,
                        emailError = null,
                        errorMessage = null
                    )

                }

            }

            is LoginEvent.PasswordChanged -> {

                _uiState.update {

                    it.copy(
                        password = event.password,
                        passwordError = null,
                        errorMessage = null
                    )

                }

            }

            LoginEvent.TogglePasswordVisibility -> {

                _uiState.update {

                    it.copy(
                        isPasswordVisible =
                            !it.isPasswordVisible
                    )

                }

            }

            LoginEvent.LoginClicked -> {

                login()

            }

            LoginEvent.ClearError -> {

                _uiState.update {

                    it.copy(
                        errorMessage = null
                    )

                }

            }

            LoginEvent.RegisterClicked -> {

                // Navigation handled by UI

            }

            LoginEvent.ForgotPasswordClicked -> {

                // Navigation handled by UI

            }

        }

    }

    // -------------------------------
    // Login
    private fun login() {

        if (!validateInputs()) return

        viewModelScope.launch {

            _uiState.update {

                it.copy(
                    isLoading = true,
                    errorMessage = null
                )

            }

            authRepository.login(
                email = _uiState.value.email,
                password = _uiState.value.password

            ).onSuccess { user ->

                userRepository
                    .updateLastLogin(user.uid)
                    .onFailure {
                        // Optional:
                        // Log.e("Login", "Failed to update last login", it)
                    }

                getUserProfile(user.uid)

                    .onSuccess { profile ->

                        _uiState.update {

                            it.copy(

                                isLoading = false,

                                loginSuccess = true,

                                //userProfile = profile

                            )

                        }

                    }

                _uiState.update {

                    it.copy(

                        isLoading = false,

                        loginSuccess = true

                    )

                }

            }.onFailure { exception ->

                _uiState.update {

                    it.copy(

                        isLoading = false,

                        errorMessage = exception.message
                            ?: "Login failed"

                    )

                }

            }

        }

    }

    // -------------------------------
    // Validation
    private fun validateInputs(): Boolean {

        val email =
            _uiState.value.email.trim()

        val password =
            _uiState.value.password

        var emailError: String? = null

        var passwordError: String? = null

        if (email.isEmpty()) {

            emailError = "Email is required."

        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email)
                .matches()
        ) {

            emailError = "Invalid email address."

        }

        if (password.isEmpty()) {

            passwordError =
                "Password is required."

        } else if (password.length < 6) {

            passwordError =
                "Password must be at least 6 characters."

        }

        _uiState.update {

            it.copy(
                emailError = emailError,
                passwordError = passwordError
            )

        }

        return emailError == null &&
                passwordError == null

    }

}