package com.finance.lumora.presentation.auth.state

/**
 * UI State for Forgot Password Screen
 */
data class ForgotPasswordState(

    /**
     * User entered email
     */
    val email: String = "",

    /**
     * Email validation error
     */
    val emailError: String? = null,

    /**
     * Loading while Firebase request executes
     */
    val isLoading: Boolean = false,

    /**
     * True when reset email is successfully sent
     */
    val resetEmailSent: Boolean = false,

    /**
     * Email address used for reset.
     * Displayed in success dialog.
     */
    val sentEmail: String = "",

    /**
     * Snackbar / general error message
     */
    val errorMessage: String? = null

)