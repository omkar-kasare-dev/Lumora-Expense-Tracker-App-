package com.finance.lumora.presentation.auth.intent


sealed interface ForgotPasswordEvent {


    data class EmailChanged(
        val email: String
    ) : ForgotPasswordEvent


    data object SendResetEmailClicked : ForgotPasswordEvent


    data object ClearError : ForgotPasswordEvent

    data object ResetSuccessState : ForgotPasswordEvent
}