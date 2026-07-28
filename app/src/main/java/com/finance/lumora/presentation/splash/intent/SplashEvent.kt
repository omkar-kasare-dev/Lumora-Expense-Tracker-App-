package com.finance.lumora.presentation.splash.intent



sealed interface SplashEvent {

    data object CheckAuthentication : SplashEvent

}