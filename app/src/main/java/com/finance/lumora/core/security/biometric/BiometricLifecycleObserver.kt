package com.finance.lumora.core.security.biometric

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class BiometricLifecycleObserver @Inject constructor(
    private val biometricLockManager: BiometricLockManager
) : DefaultLifecycleObserver {

    private var wasInBackground = false

    override fun onStop(owner: LifecycleOwner) {
        super.onStop(owner)

        wasInBackground = true

        owner.lifecycleScope.launch {
            if (
                biometricLockManager.isBiometricLockEnabled()
            ) {
                biometricLockManager.lock()
            }
        }
    }

    override fun onStart(owner: LifecycleOwner) {
        super.onStart(owner)

        if (!wasInBackground) {
            return
        }

        wasInBackground = false

        val activity = owner as? FragmentActivity
            ?: return

        owner.lifecycleScope.launch {

            if (
                biometricLockManager.isBiometricLockEnabled()
            ) {
                biometricLockManager.authenticate(
                    activity
                )
            }
        }
    }
}