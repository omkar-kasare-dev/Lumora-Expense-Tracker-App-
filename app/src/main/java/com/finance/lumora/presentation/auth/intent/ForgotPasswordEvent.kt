package com.finance.lumora.presentation.auth.intent

/**
 * User actions on the Forgot Password screen.
 */
sealed interface ForgotPasswordEvent {

    /**
     * Triggered when the email text changes.
     */
    data class EmailChanged(
        val email: String
    ) : ForgotPasswordEvent

    /**
     * Triggered when the user taps
     * "Send Reset Link".
     */
    data object SendResetEmailClicked : ForgotPasswordEvent

    /**
     * Clears the current error message
     * after Snackbar has been shown.
     */
    data object ClearError : ForgotPasswordEvent

    /**
     * Resets the success state after
     * the success dialog is dismissed.
     */
    data object ResetSuccessState : ForgotPasswordEvent
}