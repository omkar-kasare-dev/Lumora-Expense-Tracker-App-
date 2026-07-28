package com.finance.lumora.presentation.auth.intent



sealed interface RegisterEvent {

    data class NameChanged(
        val name: String
    ) : RegisterEvent

    data class EmailChanged(
        val email: String
    ) : RegisterEvent

    data class PasswordChanged(
        val password: String
    ) : RegisterEvent

    data class ConfirmPasswordChanged(
        val confirmPassword: String
    ) : RegisterEvent

    data object TogglePasswordVisibility : RegisterEvent

    data object ToggleConfirmPasswordVisibility : RegisterEvent

    data object RegisterClicked : RegisterEvent

    data object ClearError : RegisterEvent

}