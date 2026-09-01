package com.finance.lumora.core.security.biometric


import androidx.fragment.app.FragmentActivity
import com.finance.lumora.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BiometricLockManager @Inject constructor(
    private val settingsRepository: SettingsRepository,
    private val biometricAuthenticator: BiometricAuthenticator
) {

    // -------------------------------------------------------------------------
    // Current lock state
    // -------------------------------------------------------------------------

    private val _lockState =
        MutableStateFlow<BiometricLockState>(
            BiometricLockState.Initializing
        )

    val lockState: Flow<BiometricLockState> =
        _lockState.asStateFlow()

    // -------------------------------------------------------------------------
    // Check whether biometric lock is enabled
    // -------------------------------------------------------------------------

    suspend fun isBiometricLockEnabled(): Boolean {
        return settingsRepository
            .isBiometricEnabled
            .first()
    }

    // -------------------------------------------------------------------------
    // Initialize lock state
    // -------------------------------------------------------------------------

    suspend fun initialize() {

        val biometricEnabled =
            settingsRepository
                .isBiometricEnabled
                .first()

        _lockState.value =
            if (biometricEnabled) {
                BiometricLockState.Locked
            } else {
                BiometricLockState.Unlocked
            }
    }

    // -------------------------------------------------------------------------
    // Lock application
    // -------------------------------------------------------------------------

    fun lock() {

        _lockState.value =
            BiometricLockState.Locked
    }

    // -------------------------------------------------------------------------
    // Authenticate user
    // -------------------------------------------------------------------------

    fun authenticate(
        activity: FragmentActivity
    ) {

        if (_lockState.value ==
            BiometricLockState.Authenticating
        ) {
            return
        }

        _lockState.value =
            BiometricLockState.Authenticating

        biometricAuthenticator.authenticate(
            activity = activity
        ) { result ->

            when (result) {

                BiometricResult.Success -> {

                    _lockState.value =
                        BiometricLockState.Unlocked
                }

                BiometricResult.Failed -> {

                    _lockState.value =
                        BiometricLockState.Locked
                }

                BiometricResult.Cancelled -> {

                    _lockState.value =
                        BiometricLockState.Locked
                }

                is BiometricResult.Unavailable -> {

                    _lockState.value =
                        BiometricLockState.Locked
                }

                is BiometricResult.Error -> {

                    _lockState.value =
                        BiometricLockState.Locked
                }
            }
        }
    }

    // -------------------------------------------------------------------------
    // Unlock application
    // -------------------------------------------------------------------------

    fun unlock() {

        _lockState.value =
            BiometricLockState.Unlocked
    }
}