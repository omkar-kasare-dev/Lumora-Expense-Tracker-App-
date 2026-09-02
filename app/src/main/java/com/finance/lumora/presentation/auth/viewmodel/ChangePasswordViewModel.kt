package com.finance.lumora.presentation.auth.viewmodel


/*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.finance.lumora.domain.usecase.auth.AuthUseCases
import com.finance.lumora.presentation.auth.state.ChangePasswordUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChangePasswordViewModel @Inject constructor(
    private val authUseCases: AuthUseCases
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        ChangePasswordUiState()
    )

    val uiState: StateFlow<ChangePasswordUiState> =
        _uiState.asStateFlow()

    fun onCurrentPasswordChanged(
        password: String
    ) {
        _uiState.value = _uiState.value.copy(
            currentPassword = password,
            error = null
        )
    }

    fun onNewPasswordChanged(
        password: String
    ) {
        _uiState.value = _uiState.value.copy(
            newPassword = password,
            error = null
        )
    }

    fun onConfirmPasswordChanged(
        password: String
    ) {
        _uiState.value = _uiState.value.copy(
            confirmPassword = password,
            error = null
        )
    }

    fun changePassword() {

        val state = _uiState.value

        when {
            state.currentPassword.isBlank() -> {
                _uiState.value = state.copy(
                    error = "Please enter your current password."
                )
                return
            }

            state.newPassword.isBlank() -> {
                _uiState.value = state.copy(
                    error = "Please enter a new password."
                )
                return
            }

            state.confirmPassword.isBlank() -> {
                _uiState.value = state.copy(
                    error = "Please confirm your new password."
                )
                return
            }

            state.newPassword != state.confirmPassword -> {
                _uiState.value = state.copy(
                    error = "New password and confirm password do not match."
                )
                return
            }

            state.newPassword.length < 6 -> {
                _uiState.value = state.copy(
                    error = "Password must be at least 6 characters."
                )
                return
            }
        }

        _uiState.value = state.copy(
            isLoading = true,
            error = null,
            isPasswordChanged = false
        )

        viewModelScope.launch {

            val result = authUseCases.changePassword(
                currentPassword = state.currentPassword,
                newPassword = state.newPassword
            )

            result
                .onSuccess {
                    _uiState.value = ChangePasswordUiState(
                        isPasswordChanged = true
                    )
                }
                .onFailure { exception ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = exception.message
                            ?: "Unable to change password. Please try again."
                    )
                }
        }
    }

    fun clearResult() {
        _uiState.value = _uiState.value.copy(
            isPasswordChanged = false
        )
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(
            error = null
        )
    }
}



 */




import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.finance.lumora.domain.usecase.auth.AuthUseCases
import com.finance.lumora.presentation.auth.state.ChangePasswordUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChangePasswordViewModel @Inject constructor(
    private val authUseCases: AuthUseCases
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        ChangePasswordUiState()
    )

    val uiState: StateFlow<ChangePasswordUiState> =
        _uiState.asStateFlow()

    fun onCurrentPasswordChanged(
        password: String
    ) {
        _uiState.value = _uiState.value.copy(
            currentPassword = password,
            error = null
        )
    }

    fun onNewPasswordChanged(
        password: String
    ) {
        _uiState.value = _uiState.value.copy(
            newPassword = password,
            error = null
        )
    }

    fun onConfirmPasswordChanged(
        password: String
    ) {
        _uiState.value = _uiState.value.copy(
            confirmPassword = password,
            error = null
        )
    }

    fun changePassword() {

        val state = _uiState.value

        when {

            state.currentPassword.isBlank() -> {
                showError(
                    "Please enter your current password."
                )
                return
            }

            state.newPassword.isBlank() -> {
                showError(
                    "Please enter a new password."
                )
                return
            }

            state.confirmPassword.isBlank() -> {
                showError(
                    "Please confirm your new password."
                )
                return
            }

            state.newPassword != state.confirmPassword -> {
                showError(
                    "New password and confirm password do not match."
                )
                return
            }

            state.newPassword.length < 6 -> {
                showError(
                    "Password must be at least 6 characters."
                )
                return
            }

            state.currentPassword == state.newPassword -> {
                showError(
                    "New password must be different from your current password."
                )
                return
            }
        }

        _uiState.value = state.copy(
            isLoading = true,
            error = null,
            isPasswordChanged = false
        )

        viewModelScope.launch {

            val result = authUseCases.changePassword(
                currentPassword = state.currentPassword,
                newPassword = state.newPassword
            )

            result
                .onSuccess {

                    _uiState.value = ChangePasswordUiState(
                        isPasswordChanged = true
                    )
                }
                .onFailure { exception ->

                    _uiState.value = ChangePasswordUiState(
                        error = exception.message
                            ?: "Unable to change password. Please try again."
                    )
                }
        }
    }

    private fun showError(
        message: String
    ) {
        _uiState.value = _uiState.value.copy(
            isLoading = false,
            error = message,
            isPasswordChanged = false
        )
    }

    fun clearResult() {
        _uiState.value = _uiState.value.copy(
            isPasswordChanged = false
        )
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(
            error = null
        )
    }

    fun clearPasswordFields() {
        _uiState.value = _uiState.value.copy(
            currentPassword = "",
            newPassword = "",
            confirmPassword = ""
        )
    }
}

