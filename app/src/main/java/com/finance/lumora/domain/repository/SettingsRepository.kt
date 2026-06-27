package com.finance.lumora.domain.repository



import kotlinx.coroutines.flow.Flow

/**
 * Repository contract for user preferences.
 */
interface SettingsRepository {

    val isDarkModeEnabled: Flow<Boolean>

    val monthlyBudget: Flow<Double>

    val selectedCurrency: Flow<String>

    suspend fun setDarkMode(enabled: Boolean)

    suspend fun setMonthlyBudget(amount: Double)

    suspend fun setCurrency(currency: String)
}