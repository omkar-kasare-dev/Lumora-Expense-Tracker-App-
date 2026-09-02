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
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.finance.lumora.presentation.auth.viewmodel.ChangePasswordViewModel
import androidx.activity.compose.BackHandler

import androidx.compose.material3.MaterialTheme

import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChangePasswordScreen(
    onBackClick: () -> Unit,
    onPasswordChanged: () -> Unit,
    viewModel: ChangePasswordViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    var currentPasswordVisible by remember { mutableStateOf(false) }
    var newPasswordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }

    // Wrapped side effect safely in LaunchedEffect
    LaunchedEffect(uiState.isPasswordChanged) {
        if (uiState.isPasswordChanged) {
            delay(1200)

            viewModel.clearPasswordFields()
            viewModel.clearResult()

            onPasswordChanged()
        }
    }

    BackHandler {
        viewModel.clearPasswordFields()
        onBackClick()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Change Password") },
                navigationIcon = {
                    IconButton(onClick = {
                        viewModel.clearPasswordFields()
                        onBackClick()
                    }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 20.dp)
                .verticalScroll(rememberScrollState())
                .imePadding(),
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            Text(text = "Update your password")

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Enter your current password and choose a new password for your account.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Current Password Field
            OutlinedTextField(
                value = uiState.currentPassword,
                onValueChange = viewModel::onCurrentPasswordChanged,
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Current Password") },
                singleLine = true,
                enabled = !uiState.isLoading && !uiState.isPasswordChanged,
                visualTransformation = if (currentPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(onClick = { currentPasswordVisible = !currentPasswordVisible }) {
                        Icon(
                            imageVector = if (currentPasswordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                            contentDescription = if (currentPasswordVisible) "Hide current password" else "Show current password"
                        )
                    }
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // New Password Field
            OutlinedTextField(
                value = uiState.newPassword,
                onValueChange = viewModel::onNewPasswordChanged,
                modifier = Modifier.fillMaxWidth(),
                label = { Text("New Password") },
                singleLine = true,
                enabled = !uiState.isLoading && !uiState.isPasswordChanged,
                visualTransformation = if (newPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(onClick = { newPasswordVisible = !newPasswordVisible }) {
                        Icon(
                            imageVector = if (newPasswordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                            contentDescription = if (newPasswordVisible) "Hide new password" else "Show new password"
                        )
                    }
                }
            )

            Text(
                text = "Password must be at least 6 characters.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Confirm Password Field
            OutlinedTextField(
                value = uiState.confirmPassword,
                onValueChange = viewModel::onConfirmPasswordChanged,
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Confirm New Password") },
                singleLine = true,
                enabled = !uiState.isLoading && !uiState.isPasswordChanged,
                visualTransformation = if (confirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(onClick = { confirmPasswordVisible = !confirmPasswordVisible }) {
                        Icon(
                            imageVector = if (confirmPasswordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                            contentDescription = if (confirmPasswordVisible) "Hide confirm password" else "Show confirm password"
                        )
                    }
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            //Success
            if (uiState.isPasswordChanged) {

                Text(
                    text = "Password changed successfully.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(
                    modifier = Modifier.height(12.dp)
                )
            }

            // Error Display
            uiState.error?.let { errorMessage ->

                Text(
                    text = errorMessage,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.error
                )

                Spacer(
                    modifier = Modifier.height(12.dp)
                )
            }

            // Submit Button
            Button(
                onClick = viewModel::changePassword,
                modifier = Modifier.fillMaxWidth(),
                enabled = !uiState.isLoading && !uiState.isPasswordChanged
            ) {
                if (uiState.isLoading) {

                    CircularProgressIndicator(
                        modifier = Modifier.height(20.dp),
                        strokeWidth = 2.dp
                    )

                } else {

                    Text("Change Password")
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}