package com.finance.lumora.data.datastore


import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object PreferenceKeys {

    val DARK_MODE =
        booleanPreferencesKey("dark_mode")

    val MONTHLY_BUDGET =
        doublePreferencesKey("monthly_budget")

    val CURRENCY =
        stringPreferencesKey("currency")

    val FIRST_LAUNCH =
        booleanPreferencesKey("first_launch")

    val NOTIFICATION_ENABLED =
        booleanPreferencesKey("notification_enabled")
}