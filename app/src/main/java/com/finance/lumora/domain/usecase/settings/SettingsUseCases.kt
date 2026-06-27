package com.finance.lumora.domain.usecase.settings


data class SettingsUseCases(

    val getBudget: GetBudgetUseCase,

    val saveBudget: SaveBudgetUseCase,

    val getTheme: GetThemeUseCase,

    val saveTheme: SaveThemeUseCase,

    val getCurrency: GetCurrencyUseCase,

    val saveCurrency: SaveCurrencyUseCase
)