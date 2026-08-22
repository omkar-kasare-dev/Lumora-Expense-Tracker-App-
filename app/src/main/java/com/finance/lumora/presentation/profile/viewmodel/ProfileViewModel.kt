package com.finance.lumora.presentation.profile.viewmodel


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.finance.lumora.domain.model.UserProfile
import com.finance.lumora.domain.repository.AuthRepository
import com.finance.lumora.domain.usecase.auth.GetUserProfileUseCase
import com.finance.lumora.domain.usecase.auth.UpdateUserProfileUseCase
import com.finance.lumora.presentation.profile.state.ProfileState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getUserProfileUseCase: GetUserProfileUseCase,
    private val updateUserProfileUseCase: UpdateUserProfileUseCase,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileState())

    val uiState: StateFlow<ProfileState> =
        _uiState.asStateFlow()

    /**
     * Loads the currently authenticated user's profile
     * from Firestore.
     */
    fun loadProfile() {

        val currentUser = authRepository.getCurrentUser()

        Log.d("PROFILE", "Current User = $currentUser")

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

            Log.d(
                "PROFILE",
                "Loading profile for uid = ${currentUser.uid}"
            )

            getUserProfileUseCase(currentUser.uid)

                .onSuccess { profile ->

                    Log.d(
                        "PROFILE",
                        "Firestore profile = $profile"
                    )

                    _uiState.update {
                        it.copy(
                            profile = profile,
                            isLoading = false,
                            errorMessage = null
                        )
                    }
                }

                .onFailure { exception ->

                    Log.e(
                        "PROFILE",
                        "Failed to load profile",
                        exception
                    )

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

    // Update userProfile helper Fucntion:
    fun updateProfile(
        userProfile: UserProfile
    ) {

        viewModelScope.launch {

            _uiState.update {

                it.copy(
                    isUpdating = true,
                    errorMessage = null,
                    updateSuccess = false
                )
            }

            updateUserProfileUseCase(userProfile)

                .onSuccess {

                    _uiState.update {

                        it.copy(
                            profile = userProfile,
                            isUpdating = false,
                            updateSuccess = true
                        )
                    }
                }

                .onFailure { exception ->

                    _uiState.update {

                        it.copy(
                            isUpdating = false,
                            errorMessage =
                                exception.message
                                    ?: "Unable to update profile."
                        )
                    }
                }
        }
    }

    /**
     * Logs out the currently authenticated user.
     */
    fun logout() {

        viewModelScope.launch {
            authRepository.logout()
        }
    }
}