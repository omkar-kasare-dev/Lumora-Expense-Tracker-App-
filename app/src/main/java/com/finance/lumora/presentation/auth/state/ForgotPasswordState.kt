package com.finance.lumora.presentation.auth.state

/**
 * UI State for Forgot Password Screen
 */
data class ForgotPasswordState(

    val email: String = "",

    val emailError: String? = null,

    val isLoading: Boolean = false,

    val resetEmailSent: Boolean = false,

    val sentEmail: String = "",

    val errorMessage: String? = null

)