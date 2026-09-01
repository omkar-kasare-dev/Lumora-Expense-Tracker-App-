package com.finance.lumora.core.security.biometric

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class BiometricLifecycleObserver @Inject constructor(
    private val biometricLockManager: BiometricLockManager,
    private val biometricActivityHolder: BiometricActivityHolder
) : DefaultLifecycleObserver {

    /**
     * Indicates that the application has genuinely
     * entered the background.
     *
     * This prevents biometric authentication
     * during the initial application launch.
     */
    private var hasEnteredBackground = false

    override fun onStop(owner: LifecycleOwner) {
        super.onStop(owner)

        owner.lifecycleScope.launch {

            if (
                biometricLockManager
                    .isBiometricLockEnabled()
            ) {

                biometricLockManager.lock()

                hasEnteredBackground = true
            }
        }
    }

    override fun onStart(owner: LifecycleOwner) {
        super.onStart(owner)

        /**
         * Do not authenticate on initial launch.
         */
        if (!hasEnteredBackground) {
            return
        }

        hasEnteredBackground = false

        owner.lifecycleScope.launch {

            if (
                biometricLockManager
                    .isBiometricLockEnabled()
            ) {

                val activity =
                    biometricActivityHolder
                        .getActivity()

                if (activity != null) {

                    biometricLockManager.authenticate(
                        activity
                    )
                }
            }
        }
    }
}