package com.finance.lumora
/*

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.finance.lumora.ui.theme.LumoraTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LumoraTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

 */
/*
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.finance.lumora.navigation.LumoraNavGraph
import com.finance.lumora.ui.theme.LumoraTheme

import dagger.hilt.android.AndroidEntryPoint

/**
 * Main entry point of the Lumora application.
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            LumoraTheme {
                LumoraNavGraph(
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}


 */


import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.fragment.app.FragmentActivity
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.finance.lumora.navigation.LumoraNavGraph
import com.finance.lumora.presentation.security.biometric.BiometricLockGate
import com.finance.lumora.presentation.theme.viewmodel.ThemeViewModel
import com.finance.lumora.ui.theme.LumoraTheme
import dagger.hilt.android.AndroidEntryPoint

/**
 * Main entry point of the Lumora application.
 */
@AndroidEntryPoint
class MainActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {

            val themeViewModel: ThemeViewModel = hiltViewModel()

            val themeState by themeViewModel.uiState
                .collectAsStateWithLifecycle()

            LumoraTheme(
                appTheme = themeState.theme
            ) {

                // Root biometric gate protects the entire app navigation
                BiometricLockGate {
                    LumoraNavGraph(
                        modifier = Modifier.fillMaxSize()
                    )
                }

            }
        }
    }
}
