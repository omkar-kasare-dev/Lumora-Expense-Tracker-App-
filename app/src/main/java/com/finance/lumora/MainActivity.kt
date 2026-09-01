package com.finance.lumora

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.fragment.app.FragmentActivity
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.finance.lumora.core.security.biometric.BiometricActivityHolder
import com.finance.lumora.navigation.LumoraNavGraph
import com.finance.lumora.presentation.security.biometric.BiometricLockGate
import com.finance.lumora.presentation.theme.viewmodel.ThemeViewModel
import com.finance.lumora.ui.theme.LumoraTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : FragmentActivity() {

    @Inject
    lateinit var biometricActivityHolder: BiometricActivityHolder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {

            val themeViewModel: ThemeViewModel =
                hiltViewModel()

            val themeState by themeViewModel.uiState
                .collectAsStateWithLifecycle()

            LumoraTheme(
                appTheme = themeState.theme
            ) {

                BiometricLockGate {

                    LumoraNavGraph(
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()

        biometricActivityHolder.register(this)
    }

    override fun onPause() {

        biometricActivityHolder.unregister(this)

        super.onPause()
    }
}