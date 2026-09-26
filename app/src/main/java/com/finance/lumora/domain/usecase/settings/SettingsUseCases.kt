package com.finance.lumora.domain.usecase.settings



data class SettingsUseCases(

    val getTheme: GetThemeUseCase,
    val saveTheme: SaveThemeUseCase,

    val getCurrency: GetCurrencyUseCase,
    val saveCurrency: SaveCurrencyUseCase,

    val getNotifications: GetNotificationsUseCase,
    val saveNotifications: SaveNotificationsUseCase,

    val getBudgetAlerts: GetBudgetAlertsUseCase,
    val saveBudgetAlerts: SaveBudgetAlertsUseCase,

    val getBiometric: GetBiometricUseCase,
    val saveBiometric: SaveBiometricUseCase,

    val getLargeExpenseThreshold: GetLargeExpenseThresholdUseCase,
    val saveLargeExpenseThreshold: SaveLargeExpenseThresholdUseCase
)