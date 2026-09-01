package com.finance.lumora.presentation.security.biometric

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.fragment.app.FragmentActivity
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.finance.lumora.core.security.biometric.BiometricLockState
import com.finance.lumora.presentation.security.biometric.viewmodel.BiometricLockViewModel

@Composable
fun BiometricLockGate(
    viewModel: BiometricLockViewModel = hiltViewModel(),
    content: @Composable () -> Unit
) {

    val lockState by viewModel.lockState
        .collectAsStateWithLifecycle()

    val context = LocalContext.current

    val activity =
        context as? FragmentActivity

    LaunchedEffect(Unit) {
        viewModel.initialize()
    }

    when (lockState) {

        /**
         * ----------------------------------------------------
         * INITIALIZING
         * ----------------------------------------------------
         *
         * We don't expose the application UI until the
         * biometric state has been determined.
         */
        BiometricLockState.Initializing -> {

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {

                CircularProgressIndicator()
            }
        }

        /**
         * ----------------------------------------------------
         * UNLOCKED
         * ----------------------------------------------------
         */
        BiometricLockState.Unlocked -> {

            content()
        }

        /**
         * ----------------------------------------------------
         * LOCKED
         * ----------------------------------------------------
         */
        BiometricLockState.Locked -> {

            BiometricLockScreen(
                onAuthenticateClick = {

                    activity?.let { currentActivity ->

                        viewModel.authenticate(
                            currentActivity
                        )
                    }
                }
            )
        }

        /**
         * ----------------------------------------------------
         * AUTHENTICATING
         * ----------------------------------------------------
         */
        BiometricLockState.Authenticating -> {

            BiometricLockScreen(
                onAuthenticateClick = {

                    activity?.let { currentActivity ->

                        viewModel.authenticate(
                            currentActivity
                        )
                    }
                }
            )
        }
    }
}