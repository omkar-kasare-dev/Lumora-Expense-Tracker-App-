package com.finance.lumora.presentation.splash.viewmodel



import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.finance.lumora.domain.usecase.auth.ObserveAuthStateUseCase
import com.finance.lumora.presentation.splash.intent.SplashEvent
import com.finance.lumora.presentation.splash.state.SplashUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val observeAuthState: ObserveAuthStateUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        SplashUiState()
    )

    val uiState: StateFlow<SplashUiState> =
        _uiState.asStateFlow()

    init {
        onEvent(
            SplashEvent.CheckAuthentication
        )
        startSplashTimer()
    }

    //---------------Event section start--------------------
    fun onEvent(
        event: SplashEvent
    ) {
        when (event) {
            SplashEvent.CheckAuthentication ->
                observeAuthentication()
        }
    }
    //---------------Event section END--------------------

    //---------------Helper Function section start--------------------

    /**
     * isLoading stays true until the minimum splash time has passed.
     * SplashScreen waits on it before navigating, so the animations
     * always get to play even when the session resolves instantly.
     */
    private fun startSplashTimer() {
        viewModelScope.launch {
            delay(SPLASH_MIN_DURATION_MS)
            _uiState.update {
                it.copy(
                    isLoading = false
                )
            }
        }
    }

    // Previously this also set isLoading = false, which opened the
    // gate on the first auth emission and made the timer pointless.
    private fun observeAuthentication() {
        viewModelScope.launch {
            observeAuthState()
                .collect { user ->
                    _uiState.update {
                        it.copy(
                            isLoggedIn = user != null
                        )
                    }
                }
        }
    }

    //---------------Helper Function section END--------------------

    private companion object {
        const val SPLASH_MIN_DURATION_MS = 2500L
    }
}