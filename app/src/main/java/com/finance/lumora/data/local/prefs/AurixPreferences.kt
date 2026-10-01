package com.finance.lumora.data.local.prefs

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import com.finance.lumora.data.datastore.dataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private object AurixPreferenceKeys {
    val SMART_INSIGHTS_ENABLED = booleanPreferencesKey("aurix_smart_insights_enabled")
    val AUTO_CONTEXT_ENABLED = booleanPreferencesKey("aurix_auto_context_enabled")
}

class AurixPreferences @Inject constructor(
    @ApplicationContext private val context: Context
) {
    val isSmartInsightsEnabled: Flow<Boolean> =
        context.dataStore.data.map { it[AurixPreferenceKeys.SMART_INSIGHTS_ENABLED] ?: true }

    val isAutoContextEnabled: Flow<Boolean> =
        context.dataStore.data.map { it[AurixPreferenceKeys.AUTO_CONTEXT_ENABLED] ?: true }

    suspend fun setSmartInsightsEnabled(enabled: Boolean) {
        context.dataStore.edit { it[AurixPreferenceKeys.SMART_INSIGHTS_ENABLED] = enabled }
    }

    suspend fun setAutoContextEnabled(enabled: Boolean) {
        context.dataStore.edit { it[AurixPreferenceKeys.AUTO_CONTEXT_ENABLED] = enabled }
    }
}