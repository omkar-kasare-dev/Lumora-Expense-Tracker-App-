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
import androidx.compose.material.icons.filled.Person
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
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.finance.lumora.presentation.auth.components.AuthBackground
import com.finance.lumora.presentation.auth.components.AuthFooter
import com.finance.lumora.presentation.auth.components.AuthHeader
import com.finance.lumora.presentation.auth.components.AuthLoading
import com.finance.lumora.presentation.auth.components.AuthPasswordField
import com.finance.lumora.presentation.auth.components.AuthPrimaryButton
import com.finance.lumora.presentation.auth.components.AuthTextField
import com.finance.lumora.presentation.auth.intent.RegisterEvent
import com.finance.lumora.presentation.auth.viewmodel.RegisterViewModel

@Composable
fun RegisterScreen(

    onNavigateToLogin: () -> Unit,

    onRegisterSuccess: () -> Unit,

    viewModel: RegisterViewModel = hiltViewModel()

) {

    val uiState by viewModel.uiState.collectAsState()

    val snackbarHostState = remember {
        SnackbarHostState()
    }

    /**
     * Snackbar Errors
     */
    LaunchedEffect(uiState.errorMessage) {

        uiState.errorMessage?.let { message ->

            snackbarHostState.showSnackbar(message)

            viewModel.onEvent(
                RegisterEvent.ClearError
            )

        }

    }

    /**
     * Registration Success
     */
    LaunchedEffect(uiState.registrationSuccess) {

        if (uiState.registrationSuccess) {

            onRegisterSuccess()

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

                    title = "Create Account",

                    subtitle = "Start managing your finances with Lumora"

                )

                Spacer(
                    modifier = Modifier.height(32.dp)
                )

                /**
                 * Full Name
                 */
                AuthTextField(

                    value = uiState.name,

                    onValueChange = {

                        viewModel.onEvent(
                            RegisterEvent.NameChanged(it)
                        )

                    },

                    label = "Full Name",

                    leadingIcon = Icons.Default.Person,

                    placeholder = "Enter your full name",

                    capitalization = KeyboardCapitalization.Words,

                    isError = uiState.nameError != null,

                    errorMessage = uiState.nameError

                )

                Spacer(
                    modifier = Modifier.height(16.dp)
                )

                /**
                 * Email
                 */
                AuthTextField(

                    value = uiState.email,

                    onValueChange = {

                        viewModel.onEvent(
                            RegisterEvent.EmailChanged(it)
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

                /**
                 * Password
                 */
                AuthPasswordField(

                    value = uiState.password,

                    onValueChange = {

                        viewModel.onEvent(
                            RegisterEvent.PasswordChanged(it)
                        )

                    },

                    passwordVisible = uiState.isPasswordVisible,

                    onTogglePasswordVisibility = {

                        viewModel.onEvent(
                            RegisterEvent.TogglePasswordVisibility
                        )

                    },

                    placeholder = "Create a password",

                    isError = uiState.passwordError != null,

                    errorMessage = uiState.passwordError

                )

                Spacer(
                    modifier = Modifier.height(16.dp)
                )

                /**
                 * Confirm Password
                 */
                AuthPasswordField(

                    value = uiState.confirmPassword,

                    onValueChange = {

                        viewModel.onEvent(
                            RegisterEvent.ConfirmPasswordChanged(it)
                        )

                    },

                    passwordVisible = uiState.isConfirmPasswordVisible,

                    onTogglePasswordVisibility = {

                        viewModel.onEvent(
                            RegisterEvent.ToggleConfirmPasswordVisibility
                        )

                    },

                    label = "Confirm Password",

                    placeholder = "Confirm your password",

                    isError = uiState.confirmPasswordError != null,

                    errorMessage = uiState.confirmPasswordError

                )

                Spacer(
                    modifier = Modifier.height(28.dp)
                )

                AuthPrimaryButton(

                    text = "Create Account",

                    onClick = {

                        viewModel.onEvent(
                            RegisterEvent.RegisterClicked
                        )

                    },

                    enabled = !uiState.isLoading,

                    loading = uiState.isLoading

                )

                Spacer(
                    modifier = Modifier.height(24.dp)
                )

                AuthFooter(

                    message = "Already have an account?",

                    actionText = "Login",

                    onActionClick = onNavigateToLogin

                )

            }

        }

    }

    if (uiState.isLoading) {

        AuthLoading(
            message = "Creating account..."
        )

    }

}