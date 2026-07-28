package com.finance.lumora.presentation.auth.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import com.finance.lumora.presentation.auth.components.AuthFooter
import com.finance.lumora.presentation.auth.components.AuthHeader
import com.finance.lumora.presentation.auth.components.AuthLoading
import com.finance.lumora.presentation.auth.components.AuthPrimaryButton
import com.finance.lumora.presentation.auth.components.AuthTextField
import com.finance.lumora.presentation.auth.intent.ForgotPasswordEvent
import com.finance.lumora.presentation.auth.viewmodel.ForgotPasswordViewModel
import com.finance.lumora.presentation.auth.components.PasswordResetSuccessDialog

@Composable
fun ForgotPasswordScreen(

    onNavigateBack: () -> Unit,

    onResetEmailSent: (String) -> Unit,

    viewModel: ForgotPasswordViewModel = hiltViewModel()

) {

    val uiState by viewModel.uiState.collectAsState()

    val snackbarHostState = remember {

        SnackbarHostState()

    }

    /**
     * Error Snackbar
     */
    LaunchedEffect(uiState.errorMessage) {

        uiState.errorMessage?.let { message ->

            snackbarHostState.showSnackbar(message)

            viewModel.onEvent(
                ForgotPasswordEvent.ClearError
            )

        }

    }

    /**
     * Success
     */
    LaunchedEffect(uiState.resetEmailSent) {

        if (uiState.resetEmailSent) {

            onResetEmailSent(
                uiState.sentEmail
            )

            viewModel.onEvent(
                ForgotPasswordEvent.ResetSuccessState
            )

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

                    title = "Forgot Password",

                    subtitle = "Enter your registered email address. We'll send you a password reset link."

                )

                Spacer(
                    modifier = Modifier.height(32.dp)
                )

                AuthTextField(

                    value = uiState.email,

                    onValueChange = {

                        viewModel.onEvent(

                            ForgotPasswordEvent.EmailChanged(it)

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
                    modifier = Modifier.height(28.dp)
                )

                AuthPrimaryButton(

                    text = "Send Reset Link",

                    loading = uiState.isLoading,

                    onClick = {

                        viewModel.onEvent(

                            ForgotPasswordEvent.SendResetEmailClicked

                        )

                    }

                )

                Spacer(
                    modifier = Modifier.height(24.dp)
                )

                AuthFooter(

                    message = "Remember your password?",

                    actionText = "Back to Login",

                    onActionClick = onNavigateBack

                )

            }

        }

    }

    if (uiState.resetEmailSent) {

        PasswordResetSuccessDialog(

            email = uiState.sentEmail,

            onDismiss = {

                viewModel.onEvent(

                    ForgotPasswordEvent.ResetSuccessState

                )

                onResetEmailSent(
                    uiState.sentEmail
                )

            }

        )

    }

    if (uiState.isLoading) {

        AuthLoading(
            message = "Sending reset email..."
        )

    }



}