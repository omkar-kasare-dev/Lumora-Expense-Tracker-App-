package com.finance.lumora.data.repository



import com.finance.lumora.data.datastore.SettingsPreferences
import com.finance.lumora.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Repository implementation for user settings.
 *
 * Delegates all preference operations to DataStore.
 */
class SettingsRepositoryImpl @Inject constructor(
    private val settingsPreferences: SettingsPreferences
) : SettingsRepository {

    override val isDarkModeEnabled: Flow<Boolean>
        get() = settingsPreferences.darkMode

    override val monthlyBudget: Flow<Double>
        get() = settingsPreferences.monthlyBudget

    override val selectedCurrency: Flow<String>
        get() = settingsPreferences.currency

    override suspend fun setDarkMode(
        enabled: Boolean
    ) {
        settingsPreferences.setDarkMode(enabled)
    }

    override suspend fun setMonthlyBudget(
        amount: Double
    ) {
        settingsPreferences.setMonthlyBudget(amount)
    }

    override suspend fun setCurrency(
        currency: String
    ) {
        settingsPreferences.setCurrency(currency)
    }
}