package com.finance.lumora.presentation.theme.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.finance.lumora.domain.model.AppTheme
import com.finance.lumora.domain.usecase.settings.GetThemeUseCase
import com.finance.lumora.presentation.theme.state.ThemeState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

@HiltViewModel
class ThemeViewModel @Inject constructor(
    getThemeUseCase: GetThemeUseCase
) : ViewModel() {

    val uiState: StateFlow<ThemeState> =
        getThemeUseCase()
            .map<AppTheme, ThemeState> { theme ->

                ThemeState(
                    theme = theme,
                    isLoading = false,
                    error = null
                )
            }
            .catch { throwable ->

                emit(
                    ThemeState(
                        theme = AppTheme.SYSTEM,
                        isLoading = false,
                        error = throwable.message
                            ?: "Unable to load application theme."
                    )
                )
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(
                    stopTimeoutMillis = 5_000
                ),
                initialValue = ThemeState()
            )
}