package com.finance.lumora.presentation.auth.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.finance.lumora.domain.usecase.auth.AuthUseCases
import com.finance.lumora.presentation.auth.state.AuthUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class AuthViewModel @Inject constructor(

    private val authUseCases: AuthUseCases

) : ViewModel() {

    private val _uiState =
        MutableStateFlow(AuthUiState())

    val uiState: StateFlow<AuthUiState> =
        _uiState.asStateFlow()

    init {

        observeAuthState()

    }

    /**
     * Observe Firebase authentication state.
     */
    private fun observeAuthState() {

        viewModelScope.launch {

            authUseCases
                .observeAuthState()
                .collectLatest { user ->

                    _uiState.update {

                        it.copy(

                            currentUser = user,

                            isLoggedIn = user != null,

                            isLoading = false

                        )

                    }

                }

        }

    }

    /**
     * Register a new user.
     */
    fun register(

        name: String,

        email: String,

        password: String

    ) {

        viewModelScope.launch {

            _uiState.update {

                it.copy(

                    isLoading = true,

                    errorMessage = null

                )

            }

            authUseCases
                .register(
                    name,
                    email,
                    password
                )
                .onSuccess {

                    _uiState.update {

                        it.copy(

                            isLoading = false

                        )

                    }

                }
                .onFailure { error ->

                    _uiState.update {

                        it.copy(

                            isLoading = false,

                            errorMessage = error.message

                        )

                    }

                }

        }

    }

    /**
     * Login existing user.
     */
    fun login(

        email: String,

        password: String

    ) {

        viewModelScope.launch {

            _uiState.update {

                it.copy(

                    isLoading = true,

                    errorMessage = null

                )

            }

            authUseCases
                .login(
                    email,
                    password
                )
                .onSuccess {

                    _uiState.update {

                        it.copy(

                            isLoading = false

                        )

                    }

                }
                .onFailure { error ->

                    _uiState.update {

                        it.copy(

                            isLoading = false,

                            errorMessage = error.message

                        )

                    }

                }

        }

    }

    /**
     * Logout current user.
     */
    fun logout() {

        viewModelScope.launch {

            authUseCases.logout()

        }

    }

    /**
     * Clear error after displaying.
     */
    fun clearError() {

        _uiState.update {

            it.copy(

                errorMessage = null

            )

        }

    }

}