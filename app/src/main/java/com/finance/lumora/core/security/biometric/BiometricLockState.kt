package com.finance.lumora.core.security.biometric

sealed interface BiometricLockState {

    data object Initializing : BiometricLockState
    data object Unlocked : BiometricLockState

    data object Locked : BiometricLockState

    data object Authenticating : BiometricLockState
}