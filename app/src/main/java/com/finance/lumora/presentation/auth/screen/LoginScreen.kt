package com.finance.lumora.presentation.auth.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.finance.lumora.presentation.auth.components.AuthBackground
import com.finance.lumora.presentation.auth.components.AuthDivider
import com.finance.lumora.presentation.auth.components.AuthFooter
import com.finance.lumora.presentation.auth.components.AuthHeader
import com.finance.lumora.presentation.auth.components.AuthLoading
import com.finance.lumora.presentation.auth.components.AuthPasswordField
import com.finance.lumora.presentation.auth.components.AuthPrimaryButton
import com.finance.lumora.presentation.auth.components.AuthSecondaryButton
import com.finance.lumora.presentation.auth.components.AuthTextField
import com.finance.lumora.presentation.auth.intent.LoginEvent
import com.finance.lumora.presentation.auth.viewmodel.LoginViewModel

@Composable
fun LoginScreen(

    onNavigateToRegister: () -> Unit,

    onNavigateToHome: () -> Unit,

    onForgotPassword: () -> Unit,

    viewModel: LoginViewModel = hiltViewModel()

) {

    val uiState by viewModel.uiState.collectAsState()

    val snackbarHostState = remember {
        SnackbarHostState()
    }

    /**
     * Show Error Snackbar
     */
    LaunchedEffect(uiState.errorMessage) {

        uiState.errorMessage?.let { message ->

            snackbarHostState.showSnackbar(message)

            viewModel.onEvent(
                LoginEvent.ClearError
            )

        }

    }

    /**
     * Login Success
     */
    LaunchedEffect(uiState.loginSuccess) {

        if (uiState.loginSuccess) {

            onNavigateToHome()

        }

    }

    AuthBackground {

        Scaffold(

            containerColor = MaterialTheme.colorScheme.background,

            snackbarHost = {

                SnackbarHost(
                    hostState = snackbarHostState
                )

            }

        ) { padding ->

            Column(

                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(horizontal = 24.dp)
                    .verticalScroll(
                        rememberScrollState()
                    )
                    .imePadding(),

                verticalArrangement = Arrangement.Center

            ) {

                AuthHeader(

                    title = "Welcome Back",

                    subtitle = "Login to continue managing your expenses"

                )

                Spacer(
                    modifier = Modifier.height(32.dp)
                )

                AuthTextField(

                    value = uiState.email,

                    onValueChange = {

                        viewModel.onEvent(
                            LoginEvent.EmailChanged(it)
                        )

                    },

                    label = "Email",

                    leadingIcon = Icons.Default.Email,

                    placeholder = "Enter your email",

                    keyboardType = KeyboardType.Email,

                    isError = uiState.emailError != null,

                    errorMessage = uiState.emailError

                )

                Spacer(
                    modifier = Modifier.height(16.dp)
                )

                AuthPasswordField(

                    value = uiState.password,

                    onValueChange = {

                        viewModel.onEvent(
                            LoginEvent.PasswordChanged(it)
                        )

                    },

                    passwordVisible = uiState.isPasswordVisible,

                    onTogglePasswordVisibility = {

                        viewModel.onEvent(
                            LoginEvent.TogglePasswordVisibility
                        )

                    },

                    placeholder = "Enter your password",

                    isError = uiState.passwordError != null,

                    errorMessage = uiState.passwordError

                )

                Spacer(
                    modifier = Modifier.height(12.dp)
                )

                AuthSecondaryButton(

                    text = "Forgot Password?",

                    modifier = Modifier.fillMaxWidth(),

                    onClick = {

                        onForgotPassword()

                    }

                )

                Spacer(
                    modifier = Modifier.height(24.dp)
                )

                AuthPrimaryButton(

                    text = "Login",

                    onClick = {

                        viewModel.onEvent(
                            LoginEvent.LoginClicked
                        )

                    },

                    enabled = !uiState.isLoading,

                    loading = uiState.isLoading

                )

                Spacer(
                    modifier = Modifier.height(24.dp)
                )

                AuthDivider()

                Spacer(
                    modifier = Modifier.height(24.dp)
                )

                AuthFooter(

                    message = "Don't have an account?",

                    actionText = "Register",

                    onActionClick = {

                        onNavigateToRegister()

                    }

                )

            }

        }

    }

    if (uiState.isLoading) {

        AuthLoading(
            message = "Signing in..."
        )

    }

}