package com.finance.lumora.presentation.auth.intent


/**
 * Represents all user interactions on the Login Screen.
 *
 * These events are consumed by the LoginViewModel,
 * which updates the LoginUiState accordingly.
 */
sealed interface LoginEvent {

    /**
     * Triggered when the email text changes.
     */
    data class EmailChanged(
        val email: String
    ) : LoginEvent

    /**
     * Triggered when the password text changes.
     */
    data class PasswordChanged(
        val password: String
    ) : LoginEvent

    /**
     * Triggered when the password visibility icon is clicked.
     */
    data object TogglePasswordVisibility : LoginEvent

    /**
     * Triggered when the Login button is clicked.
     */
    data object LoginClicked : LoginEvent

    /**
     * Triggered when the Forgot Password button is clicked.
     */
    data object ForgotPasswordClicked : LoginEvent

    /**
     * Triggered when the Register link is clicked.
     */
    data object RegisterClicked : LoginEvent

    /**
     * Clears the current authentication error.
     */
    data object ClearError : LoginEvent

}