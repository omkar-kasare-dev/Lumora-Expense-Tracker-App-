package com.finance.lumora.presentation.security.biometric.viewmodel

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.finance.lumora.core.security.biometric.BiometricLockManager
import com.finance.lumora.core.security.biometric.BiometricLockState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.launch

@HiltViewModel
class BiometricLockViewModel @Inject constructor(
    private val biometricLockManager: BiometricLockManager
) : ViewModel() {

    val lockState: StateFlow<BiometricLockState> =
        biometricLockManager.lockState
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = BiometricLockState.Unlocked
            )

    fun authenticate(
        activity: FragmentActivity
    ) {
        biometricLockManager.authenticate(
            activity
        )
    }

    fun initialize() {

        viewModelScope.launch {

            val enabled =
                biometricLockManager
                    .isBiometricLockEnabled()

            if (!enabled) {
                biometricLockManager.unlock()
            }
        }
    }
}