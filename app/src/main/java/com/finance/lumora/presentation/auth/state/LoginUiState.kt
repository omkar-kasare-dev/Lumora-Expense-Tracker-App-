package com.finance.lumora.presentation.auth.state


/**
 * Represents the UI state of the Login Screen.
 *
 * This state is observed by the LoginScreen and updated
 * by the LoginViewModel.
 */
data class LoginUiState(

    /**
     * User email.
     */
    val email: String = "",

    /**
     * User password.
     */
    val password: String = "",

    /**
     * Controls password visibility.
     */
    val isPasswordVisible: Boolean = false,

    /**
     * Indicates whether a login request is in progress.
     */
    val isLoading: Boolean = false,

    /**
     * General authentication error.
     */
    val errorMessage: String? = null,

    /**
     * True after successful authentication.
     */
    val loginSuccess: Boolean = false,

    /**
     * Email validation error.
     */
    val emailError: String? = null,

    /**
     * Password validation error.
     */
    val passwordError: String? = null

)