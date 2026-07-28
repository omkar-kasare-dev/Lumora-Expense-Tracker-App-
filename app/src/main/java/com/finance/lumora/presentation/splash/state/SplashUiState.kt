package com.finance.lumora.presentation.splash.state



/**
 * Represents the UI state of the Splash Screen.
 */
data class SplashUiState(

    /**
     * True while the splash screen is visible.
     */

    val isLoading: Boolean = true,

    val isLoggedIn: Boolean = false

)