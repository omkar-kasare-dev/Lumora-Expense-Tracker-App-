package com.finance.lumora.presentation.profile.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.finance.lumora.domain.repository.AuthRepository
import com.finance.lumora.domain.usecase.auth.GetUserProfileUseCase
import com.finance.lumora.domain.usecase.auth.UpdateUserProfileUseCase
import com.finance.lumora.presentation.profile.intent.EditProfileEvent
import com.finance.lumora.presentation.profile.state.EditProfileState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val getUserProfileUseCase: GetUserProfileUseCase,
    private val updateUserProfileUseCase: UpdateUserProfileUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(EditProfileState())

    val uiState: StateFlow<EditProfileState> = _uiState.asStateFlow()

    /**
     * Loads the currently logged-in user's profile.
     */
    fun loadProfile() {

        // Prevent unnecessary reloads.
        if (_uiState.value.profile != null) {
            return
        }

        val currentUser = authRepository.getCurrentUser()

        if (currentUser == null) {
            _uiState.update {
                it.copy(
                    isLoading = false,
                    errorMessage = "User is not logged in."
                )
            }
            return
        }

        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoading = true,
                    errorMessage = null
                )
            }

            getUserProfileUseCase(currentUser.uid)
                .onSuccess { profile ->
                    _uiState.update {
                        it.copy(
                            profile = profile,
                            fullName = profile.fullName,
                            email = profile.email,
                            currency = profile.currency,
                            language = profile.language,
                            isLoading = false,
                            errorMessage = null
                        )
                    }
                }
                .onFailure { exception ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = exception.message
                                ?: "Unable to load profile."
                        )
                    }
                }
        }
    }

    /**
     * Handles all Edit Profile UI events.
     */
    fun onEvent(event: EditProfileEvent) {

        when (event) {

            is EditProfileEvent.FullNameChanged -> {
                _uiState.update {
                    it.copy(
                        fullName = event.fullName,
                        errorMessage = null,
                        isSaved = false
                    )
                }
            }

            is EditProfileEvent.EmailChanged -> {
                _uiState.update {
                    it.copy(
                        email = event.email,
                        errorMessage = null,
                        isSaved = false
                    )
                }
            }

            is EditProfileEvent.CurrencyChanged -> {
                _uiState.update {
                    it.copy(
                        currency = event.currency,
                        errorMessage = null,
                        isSaved = false
                    )
                }
            }

            is EditProfileEvent.LanguageChanged -> {
                _uiState.update {
                    it.copy(
                        language = event.language,
                        errorMessage = null,
                        isSaved = false
                    )
                }
            }

            EditProfileEvent.SaveProfile -> {
                saveProfile()
            }

            EditProfileEvent.Retry -> {
                loadProfile()
            }

            EditProfileEvent.ClearError -> {
                _uiState.update {
                    it.copy(
                        errorMessage = null
                    )
                }
            }
        }
    }

    /**
     * Saves the edited profile.
     */
    private fun saveProfile() {

        val currentState = _uiState.value
        val currentProfile = currentState.profile

        if (currentProfile == null) {
            _uiState.update {
                it.copy(
                    errorMessage = "Profile is not loaded."
                )
            }
            return
        }

        // -----------------------------
        // Validation
        // -----------------------------
        val trimmedName = currentState.fullName.trim()
        val trimmedEmail = currentState.email.trim()

        if (trimmedName.isBlank()) {
            _uiState.update {
                it.copy(
                    errorMessage = "Full name cannot be empty."
                )
            }
            return
        }

        if (trimmedName.length < 2) {
            _uiState.update {
                it.copy(
                    errorMessage = "Full name must contain at least 2 characters."
                )
            }
            return
        }

        if (trimmedEmail.isBlank()) {
            _uiState.update {
                it.copy(
                    errorMessage = "Email cannot be empty."
                )
            }
            return
        }

        if (!android.util.Patterns.EMAIL_ADDRESS
                .matcher(trimmedEmail)
                .matches()
        ) {
            _uiState.update {
                it.copy(
                    errorMessage = "Please enter a valid email address."
                )
            }
            return
        }

        // -----------------------------
        // Create updated profile
        // -----------------------------
        val updatedProfile = currentProfile.copy(
            fullName = trimmedName,
            email = trimmedEmail,
            currency = currentState.currency,
            language = currentState.language
        )

        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isSaving = true,
                    errorMessage = null,
                    isSaved = false
                )
            }

            updateUserProfileUseCase(updatedProfile)
                .onSuccess {
                    _uiState.update {
                        it.copy(
                            profile = updatedProfile,
                            fullName = updatedProfile.fullName,
                            email = updatedProfile.email,
                            currency = updatedProfile.currency,
                            language = updatedProfile.language,
                            isSaving = false,
                            isSaved = true,
                            errorMessage = null
                        )
                    }
                }
                .onFailure { exception ->
                    _uiState.update {
                        it.copy(
                            isSaving = false,
                            isSaved = false,
                            errorMessage = exception.message
                                ?: "Unable to update profile."
                        )
                    }
                }
        }
    }
}