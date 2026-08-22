package com.finance.lumora.data.repository
/*
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

 */



import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import com.finance.lumora.data.datastore.SettingsPreferences
import com.finance.lumora.domain.model.AppTheme
import com.finance.lumora.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import com.finance.lumora.domain.model.BudgetAlertLevel
import kotlinx.coroutines.flow.map


class SettingsRepositoryImpl @Inject constructor(
    private val settingsPreferences: SettingsPreferences,
    private val dataStore: DataStore<Preferences>
) : SettingsRepository {

    // -------------------------------------------------------------------------
    // Theme
    override val theme: Flow<AppTheme>
        get() = settingsPreferences.theme

    override suspend fun setTheme(theme: AppTheme) {
        settingsPreferences.setTheme(theme)
    }

    // -------------------------------------------------------------------------
    // Currency
    override val selectedCurrency: Flow<String>
        get() = settingsPreferences.currency

    override suspend fun setCurrency(currency: String) {
        settingsPreferences.setCurrency(currency)
    }

    // -------------------------------------------------------------------------
    // Notifications

    override val isNotificationsEnabled: Flow<Boolean>
        get() = settingsPreferences.notificationEnabled

    override suspend fun setNotificationsEnabled(enabled: Boolean) {
        settingsPreferences.setNotificationEnabled(enabled)
    }

    // -------------------------------------------------------------------------
    // Budget Alerts
    override val isBudgetAlertsEnabled: Flow<Boolean>
        get() = settingsPreferences.budgetAlertsEnabled

    override suspend fun setBudgetAlertsEnabled(enabled: Boolean) {
        settingsPreferences.setBudgetAlertsEnabled(enabled)
    }

    // -------------------------------------------------------------------------
    // Biometric
    override val isBiometricEnabled: Flow<Boolean>
        get() = settingsPreferences.biometricEnabled

    override suspend fun setBiometricEnabled(enabled: Boolean) {
        settingsPreferences.setBiometricEnabled(enabled)
    }

    // -------------------------------------------------------------------------
    // Monthly Budget
    private object PreferencesKeys {
        val MONTHLY_BUDGET = doublePreferencesKey("monthly_budget")
    }

    override val monthlyBudget: Flow<Double> = dataStore.data
        .map { preferences -> preferences[PreferencesKeys.MONTHLY_BUDGET] ?: 0.0 }

    override suspend fun setMonthlyBudget(amount: Double) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.MONTHLY_BUDGET] = amount
        }
    }
//---------------------------------------------------------------------
    override val budgetAlertMonth: Flow<String>
        get() = settingsPreferences.budgetAlertMonth

    override val lastBudgetAlertLevel: Flow<BudgetAlertLevel>
        get() = settingsPreferences.lastBudgetAlertLevel.map { levelName ->
            runCatching {
                BudgetAlertLevel.valueOf(levelName)
            }.getOrDefault(BudgetAlertLevel.NONE)
        }

    override suspend fun setBudgetAlertState(month: String, level: BudgetAlertLevel) {
        settingsPreferences.setBudgetAlertMonth(month)
        settingsPreferences.setLastBudgetAlertLevel(level.name)
    }
}