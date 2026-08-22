package com.finance.lumora.presentation.settings.intent

import com.finance.lumora.domain.model.AppTheme

sealed interface SettingsEvent {

    data class ChangeCurrency(
        val currency: String
    ) : SettingsEvent

    data class ChangeTheme(
        val theme: AppTheme
    ) : SettingsEvent

    data class ToggleNotifications(
        val enabled: Boolean
    ) : SettingsEvent

    data class ToggleBudgetAlerts(
        val enabled: Boolean
    ) : SettingsEvent

    data class ToggleBiometric(
        val enabled: Boolean
    ) : SettingsEvent

    data class ChangeBudget(
        val amount: Double
    ) : SettingsEvent
}