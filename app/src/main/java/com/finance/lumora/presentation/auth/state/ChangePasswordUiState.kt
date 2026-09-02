package com.finance.lumora.presentation.auth.state



data class ChangePasswordUiState(
    val currentPassword: String = "",
    val newPassword: String = "",
    val confirmPassword: String = "",

    val isLoading: Boolean = false,

    val error: String? = null,

    val isPasswordChanged: Boolean = false
)

