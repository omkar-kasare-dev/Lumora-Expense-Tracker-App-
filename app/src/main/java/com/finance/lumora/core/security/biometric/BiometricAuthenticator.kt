package com.finance.lumora.core.security.biometric


import android.content.Context
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class BiometricAuthenticator @Inject constructor(
    @ApplicationContext
    private val context: Context
) {

    /**
     * Biometric authenticators supported by Lumora.
     * BIOMETRIC_WEAK is intentionally used here instead of
     * BIOMETRIC_STRONG so that Lumora can work with a wider
     * range of Android devices and emulators.
     */
    private val authenticators =
        BiometricManager.Authenticators.BIOMETRIC_WEAK

    /**
     * Checks whether biometric authentication is available.
     */
    fun canAuthenticate(): Boolean {

        val biometricManager =
            BiometricManager.from(context)

        return biometricManager.canAuthenticate(
            authenticators
        ) == BiometricManager.BIOMETRIC_SUCCESS
    }

    /**
     * Starts biometric authentication.
     */
    fun authenticate(
        activity: FragmentActivity,
        onResult: (BiometricResult) -> Unit
    ) {

        val biometricManager =
            BiometricManager.from(context)

        val result =
            biometricManager.canAuthenticate(
                authenticators
            )

        when (result) {

            BiometricManager.BIOMETRIC_SUCCESS -> {

                showBiometricPrompt(
                    activity = activity,
                    onResult = onResult
                )
            }

            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> {

                onResult(
                    BiometricResult.Unavailable(
                        reason = "This device does not have biometric hardware."
                    )
                )
            }

            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> {

                onResult(
                    BiometricResult.Unavailable(
                        reason = "Biometric hardware is currently unavailable."
                    )
                )
            }

            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {

                onResult(
                    BiometricResult.Unavailable(
                        reason = "No biometric is enrolled on this device."
                    )
                )
            }

            else -> {

                onResult(
                    BiometricResult.Unavailable(
                        reason = "Biometric authentication is not available."
                    )
                )
            }
        }
    }

    /**
     * Displays Android biometric authentication prompt.
     */
    private fun showBiometricPrompt(
        activity: FragmentActivity,
        onResult: (BiometricResult) -> Unit
    ) {

        val executor =
            ContextCompat.getMainExecutor(context)

        val callback =
            object : BiometricPrompt.AuthenticationCallback() {

                override fun onAuthenticationSucceeded(
                    result: BiometricPrompt.AuthenticationResult
                ) {

                    super.onAuthenticationSucceeded(result)

                    onResult(
                        BiometricResult.Success
                    )
                }

                override fun onAuthenticationFailed() {

                    super.onAuthenticationFailed()

                    onResult(
                        BiometricResult.Failed
                    )
                }

                override fun onAuthenticationError(
                    errorCode: Int,
                    errString: CharSequence
                ) {

                    super.onAuthenticationError(
                        errorCode,
                        errString
                    )

                    when (errorCode) {

                        BiometricPrompt.ERROR_USER_CANCELED,
                        BiometricPrompt.ERROR_NEGATIVE_BUTTON -> {

                            onResult(
                                BiometricResult.Cancelled
                            )
                        }

                        else -> {

                            onResult(
                                BiometricResult.Error(
                                    code = errorCode,
                                    message = errString.toString()
                                )
                            )
                        }
                    }
                }
            }

        val biometricPrompt =
            BiometricPrompt(
                activity,
                executor,
                callback
            )

        val promptInfo =
            BiometricPrompt.PromptInfo.Builder()
                .setTitle("Unlock Lumora")
                .setSubtitle(
                    "Authenticate to enable biometric lock"
                )
                .setDescription(
                    "Use your fingerprint or face to protect your Lumora account."
                )
                .setNegativeButtonText("Cancel")
                .build()

        biometricPrompt.authenticate(
            promptInfo
        )
    }
}