package com.finance.lumora.core.security.biometric

sealed interface BiometricResult {

    data object Success : BiometricResult

    data object Failed : BiometricResult

    data object Cancelled : BiometricResult

    data class Unavailable(
        val reason: String
    ) : BiometricResult

    //data object Unavailable : BiometricResult

    data class Error(
        val code: Int,
        val message: String
    ) : BiometricResult
}