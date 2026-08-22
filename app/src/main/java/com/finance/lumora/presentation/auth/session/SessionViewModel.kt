package com.finance.lumora.presentation.auth.session


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.finance.lumora.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * ViewModel responsible for monitoring the application's
 * authentication session.
 * It listens to Firebase Authentication state changes and
 * exposes a simple SessionState for the UI.
 */
@HiltViewModel
class SessionViewModel @Inject constructor(

    private val authRepository: AuthRepository

) : ViewModel() {

    /**
     * Internal mutable session state.
     * Default state is Loading until Firebase
     * emits the current authentication status.
     */
    private val _sessionState = MutableStateFlow<SessionState>(
        SessionState.Loading
    )

    val sessionState: StateFlow<SessionState> =
        _sessionState.asStateFlow()

    init {

        observeAuthenticationState()
    }

    /**
     * Observe Firebase authentication state.
     * Whenever Firebase signs a user in or out,
     * this Flow emits a new value.
     */
    private fun observeAuthenticationState() {

        viewModelScope.launch {

            authRepository
                .observeAuthState()
                .collectLatest { user ->

                    _sessionState.value =
                        if (user != null) {
                            SessionState.Authenticated
                        } else {
                            SessionState.Unauthenticated
                        }

                }

        }

    }



}