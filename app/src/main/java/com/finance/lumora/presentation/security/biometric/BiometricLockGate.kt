package com.finance.lumora.presentation.security.biometric



import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.LaunchedEffect
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

        BiometricLockState.Unlocked -> {
            content()
        }

        BiometricLockState.Locked -> {

            BiometricLockScreen(
                onAuthenticateClick = {

                    activity?.let {
                        viewModel.authenticate(it)
                    }
                }
            )
        }

        BiometricLockState.Authenticating -> {

            BiometricLockScreen(
                onAuthenticateClick = {

                    activity?.let {
                        viewModel.authenticate(it)
                    }
                }
            )
        }
    }
}