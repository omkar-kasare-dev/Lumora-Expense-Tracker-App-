package com.finance.lumora.data.datastore


import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object PreferenceKeys {

    val THEME =
        stringPreferencesKey("theme")

    val MONTHLY_BUDGET =
        doublePreferencesKey("monthly_budget")

    val CURRENCY =
        stringPreferencesKey("currency")


    val FIRST_LAUNCH =
        booleanPreferencesKey("first_launch")

    val NOTIFICATION_ENABLED =
        booleanPreferencesKey("notification_enabled")

    val BUDGET_ALERTS_ENABLED =
        booleanPreferencesKey("budget_alerts_enabled")

    val BIOMETRIC_ENABLED =
        booleanPreferencesKey("biometric_enabled")


    val BUDGET_ALERT_MONTH =
        stringPreferencesKey("budget_alert_month")


    val LAST_BUDGET_ALERT_LEVEL =
        stringPreferencesKey("last_budget_alert_level")

}

