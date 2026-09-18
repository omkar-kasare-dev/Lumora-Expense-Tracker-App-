package com.finance.lumora.data.datastore

import android.content.Context
import androidx.datastore.preferences.core.edit
import com.finance.lumora.domain.model.AppTheme
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SettingsPreferences @Inject constructor(

    @ApplicationContext
    private val context: Context

) {

    // =========================================================================
    // Theme
    val theme: Flow<AppTheme> =
        context.dataStore.data.map { preferences ->

            val storedTheme =
                preferences[PreferenceKeys.THEME]

            when (storedTheme) {

                AppTheme.SYSTEM.name ->
                    AppTheme.SYSTEM

                AppTheme.LIGHT.name ->
                    AppTheme.LIGHT

                AppTheme.DARK.name ->
                    AppTheme.DARK

                else ->
                    AppTheme.SYSTEM
            }
        }

    // =========================================================================
    // Monthly Budget
    val monthlyBudget: Flow<Double> =
        context.dataStore.data.map { preferences ->

            preferences[
                PreferenceKeys.MONTHLY_BUDGET
            ] ?: 0.0
        }

    // =========================================================================
    // Currency
    val currency: Flow<String> =
        context.dataStore.data.map { preferences ->

            preferences[
                PreferenceKeys.CURRENCY
            ] ?: "INR (₹)"
        }

    // =========================================================================
    // First Launch
    val firstLaunch: Flow<Boolean> =
        context.dataStore.data.map { preferences ->

            preferences[
                PreferenceKeys.FIRST_LAUNCH
            ] ?: true
        }
    // =========================================================================
    // Biometric
    val biometricEnabled: Flow<Boolean> =
        context.dataStore.data.map { preferences ->

            preferences[
                PreferenceKeys.BIOMETRIC_ENABLED
            ] ?: false
        }
    // =========================================================================
    // Notifications

    val notificationEnabled: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[PreferenceKeys.NOTIFICATION_ENABLED] ?: true
    }

    val budgetAlertsEnabled: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[PreferenceKeys.BUDGET_ALERTS_ENABLED] ?: false
    }

    val budgetAlertMonth: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[PreferenceKeys.BUDGET_ALERT_MONTH] ?: ""
    }

    val lastBudgetAlertLevel: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[PreferenceKeys.LAST_BUDGET_ALERT_LEVEL] ?: ""
    }

    // Large Expense Threshold
    val largeExpenseThreshold: Flow<Double> =
        context.dataStore.data.map { preferences ->

            preferences[
                PreferenceKeys.LARGE_EXPENSE_THRESHOLD
            ] ?: DEFAULT_LARGE_EXPENSE_THRESHOLD
        }

    // =========================================================================
    // Save Theme
    suspend fun setTheme(
        theme: AppTheme
    ) {

        context.dataStore.edit { preferences ->

            preferences[
                PreferenceKeys.THEME
            ] = theme.name
        }
    }

    suspend fun setBudgetAlertMonth(month: String) {
        context.dataStore.edit { preferences ->
            preferences[PreferenceKeys.BUDGET_ALERT_MONTH] = month
        }
    }

    suspend fun setLastBudgetAlertLevel(level: String) {
        context.dataStore.edit { preferences ->
            preferences[PreferenceKeys.LAST_BUDGET_ALERT_LEVEL] = level
        }
    }

    // =========================================================================
    // Save Currency
    // =========================================================================

    suspend fun setCurrency(
        currency: String
    ) {

        context.dataStore.edit { preferences ->

            preferences[
                PreferenceKeys.CURRENCY
            ] = currency
        }
    }

    // =========================================================================
    // Save First Launch
    suspend fun setFirstLaunch(
        isFirstLaunch: Boolean
    ) {

        context.dataStore.edit { preferences ->

            preferences[
                PreferenceKeys.FIRST_LAUNCH
            ] = isFirstLaunch
        }
    }

    // =========================================================================
    // Save Notifications
    suspend fun setNotificationEnabled(
        enabled: Boolean
    ) {

        context.dataStore.edit { preferences ->

            preferences[
                PreferenceKeys.NOTIFICATION_ENABLED
            ] = enabled
        }
    }

    // =========================================================================
    // Save Budget Alerts
    suspend fun setBudgetAlertsEnabled(
        enabled: Boolean
    ) {

        context.dataStore.edit { preferences ->

            preferences[
                PreferenceKeys.BUDGET_ALERTS_ENABLED
            ] = enabled
        }
    }

    // =========================================================================
    // Save Biometric
    suspend fun setBiometricEnabled(
        enabled: Boolean
    ) {

        context.dataStore.edit { preferences ->

            preferences[
                PreferenceKeys.BIOMETRIC_ENABLED
            ] = enabled
        }
    }

    // Save Large Expense Threshold
    suspend fun setLargeExpenseThreshold(
        amount: Double
    ) {

        context.dataStore.edit { preferences ->

            preferences[
                PreferenceKeys.LARGE_EXPENSE_THRESHOLD
            ] = amount
        }
    }

    private companion object {
        const val DEFAULT_LARGE_EXPENSE_THRESHOLD = 5000.0
    }

}


