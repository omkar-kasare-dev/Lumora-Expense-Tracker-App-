package com.finance.lumora.presentation.theme.state

import com.finance.lumora.domain.model.AppTheme

/**
 * Represents the application-wide theme state.
 *
 * This state is independent of the Settings screen.
 * It allows the application theme to react to the
 * persisted user preference.
 */
data class ThemeState(
    val theme: AppTheme = AppTheme.SYSTEM,
    val isLoading: Boolean = true,
    val error: String? = null
)