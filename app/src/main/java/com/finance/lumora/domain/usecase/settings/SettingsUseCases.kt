package com.finance.lumora.domain.usecase.settings
/*
data class SettingsUseCases(

    val getBudget: GetBudgetUseCase,

    val saveBudget: SaveBudgetUseCase,

    val getTheme: GetThemeUseCase,

    val saveTheme: SaveThemeUseCase,

    val getCurrency: GetCurrencyUseCase,

    val saveCurrency: SaveCurrencyUseCase
)

 */


data class SettingsUseCases(

    val getBudget: GetBudgetUseCase,
    val saveBudget: SaveBudgetUseCase,

    val getTheme: GetThemeUseCase,
    val saveTheme: SaveThemeUseCase,

    val getCurrency: GetCurrencyUseCase,
    val saveCurrency: SaveCurrencyUseCase,

    val getNotifications: GetNotificationsUseCase,
    val saveNotifications: SaveNotificationsUseCase,

    val getBudgetAlerts: GetBudgetAlertsUseCase,
    val saveBudgetAlerts: SaveBudgetAlertsUseCase,

    val getBiometric: GetBiometricUseCase,
    val saveBiometric: SaveBiometricUseCase
)