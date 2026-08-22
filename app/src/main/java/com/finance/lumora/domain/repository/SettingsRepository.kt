package com.finance.lumora.domain.repository
/*
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

 */


import com.finance.lumora.domain.model.AppTheme
import com.finance.lumora.domain.model.BudgetAlertLevel
import kotlinx.coroutines.flow.Flow

/**
 * Repository contract for user application settings.
 *
 * The repository exposes settings as reactive Flow values
 * and provides operations to persist user changes.
 */
interface SettingsRepository {

    val theme: Flow<AppTheme>

    suspend fun setTheme(theme: AppTheme)

    val selectedCurrency: Flow<String>

    suspend fun setCurrency(currency: String)

    val isNotificationsEnabled: Flow<Boolean>

    suspend fun setNotificationsEnabled(enabled: Boolean)

    val isBudgetAlertsEnabled: Flow<Boolean>

    suspend fun setBudgetAlertsEnabled(enabled: Boolean)


    val isBiometricEnabled: Flow<Boolean>

    suspend fun setBiometricEnabled(enabled: Boolean)


    val monthlyBudget: Flow<Double>

    suspend fun setMonthlyBudget(amount: Double)

    // Alert Tracking
    val budgetAlertMonth: Flow<String>
    val lastBudgetAlertLevel: Flow<BudgetAlertLevel>
    suspend fun setBudgetAlertState(month: String, level: BudgetAlertLevel)


}