package com.finance.lumora.presentation.settings.state

import com.finance.lumora.domain.model.UserSettings

data class SettingsUiState(

    val settings: UserSettings = UserSettings(),

    val largeExpenseThreshold: Double = 5000.0,

    val isLoading: Boolean = false,

    val error: String? = null

)