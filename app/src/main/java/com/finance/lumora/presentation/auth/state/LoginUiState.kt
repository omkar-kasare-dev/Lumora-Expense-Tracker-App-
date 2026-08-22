package com.finance.lumora.presentation.auth.state


/**
 * Represents the UI state of the Login Screen.
 *
 * This state is observed by the LoginScreen and updated
 * by the LoginViewModel.
 */
data class LoginUiState(

    val email: String = "",

    val password: String = "",

    val isPasswordVisible: Boolean = false,
    
    val isLoading: Boolean = false,

    val errorMessage: String? = null,

    val loginSuccess: Boolean = false,

    val emailError: String? = null,

    val passwordError: String? = null

)