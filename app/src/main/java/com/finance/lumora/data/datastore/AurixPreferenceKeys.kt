package com.finance.lumora.data.datastore

import androidx.datastore.preferences.core.booleanPreferencesKey

object AurixPreferenceKeys {
    val SMART_INSIGHTS_ENABLED = booleanPreferencesKey("aurix_smart_insights_enabled")
    val AUTO_CONTEXT_ENABLED = booleanPreferencesKey("aurix_auto_context_enabled")
}