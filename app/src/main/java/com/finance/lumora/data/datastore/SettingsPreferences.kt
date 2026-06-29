package com.finance.lumora.data.datastore


import android.content.Context
import androidx.datastore.preferences.core.edit
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SettingsPreferences @Inject constructor(
    @ApplicationContext
    private val context: Context
) {

    val darkMode: Flow<Boolean> =
        context.dataStore.data.map { preferences ->
            preferences[PreferenceKeys.DARK_MODE] ?: false
        }

    val monthlyBudget: Flow<Double> =
        context.dataStore.data.map { preferences ->
            preferences[PreferenceKeys.MONTHLY_BUDGET] ?: 0.0
        }

    val currency: Flow<String> =
        context.dataStore.data.map { preferences ->
            preferences[PreferenceKeys.CURRENCY] ?: "₹"
        }

    val firstLaunch: Flow<Boolean> =
        context.dataStore.data.map { preferences ->
            preferences[PreferenceKeys.FIRST_LAUNCH] ?: true
        }

    val notificationEnabled: Flow<Boolean> =
        context.dataStore.data.map { preferences ->
            preferences[PreferenceKeys.NOTIFICATION_ENABLED] ?: true
        }

    suspend fun setDarkMode(enabled: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[PreferenceKeys.DARK_MODE] = enabled
        }
    }

    suspend fun setMonthlyBudget(amount: Double) {
        context.dataStore.edit { preferences ->
            preferences[PreferenceKeys.MONTHLY_BUDGET] = amount
        }
    }

    suspend fun setCurrency(currency: String) {
        context.dataStore.edit { preferences ->
            preferences[PreferenceKeys.CURRENCY] = currency
        }
    }

    suspend fun setFirstLaunch(isFirstLaunch: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[PreferenceKeys.FIRST_LAUNCH] = isFirstLaunch
        }
    }

    suspend fun setNotificationEnabled(enabled: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[PreferenceKeys.NOTIFICATION_ENABLED] = enabled
        }
    }
}