package com.finance.lumora.presentation.ai.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.finance.lumora.data.datastore.AurixPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

data class AurixDrawerUiState(
    val smartInsightsEnabled: Boolean = true,
    val autoContextEnabled: Boolean = true
)

@HiltViewModel
class AurixDrawerViewModel @Inject constructor(
    private val aurixPreferences: AurixPreferences
) : ViewModel() {

    val uiState: StateFlow<AurixDrawerUiState> = combine(
        aurixPreferences.isSmartInsightsEnabled,
        aurixPreferences.isAutoContextEnabled
    ) { smart, autoContext ->
        AurixDrawerUiState(smart, autoContext)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = AurixDrawerUiState()
    )

    fun setSmartInsightsEnabled(enabled: Boolean) {
        viewModelScope.launch { aurixPreferences.setSmartInsightsEnabled(enabled) }
    }

    fun setAutoContextEnabled(enabled: Boolean) {
        viewModelScope.launch { aurixPreferences.setAutoContextEnabled(enabled) }
    }
}