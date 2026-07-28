package com.finance.lumora.domain.model


enum class AppTheme {
    SYSTEM,
    LIGHT,
    DARK
}

data class UserSettings(
    val currency: String = "INR (₹)",
    val theme: AppTheme = AppTheme.SYSTEM,
    val isBiometricEnabled: Boolean = false,
    val isNotificationsEnabled: Boolean = true,
    val isBudgetAlertsEnabled: Boolean = true
)