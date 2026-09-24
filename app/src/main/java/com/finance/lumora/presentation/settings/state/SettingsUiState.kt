package com.finance.lumora.presentation.settings.state

import com.finance.lumora.domain.model.UserSettings

data class SettingsUiState(

    val settings: UserSettings = UserSettings(),

    val isLoading: Boolean = false,

    val error: String? = null

)