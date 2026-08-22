package com.finance.lumora.presentation.auth.viewmodel


import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.finance.lumora.domain.model.UserProfile
import com.finance.lumora.domain.repository.AuthRepository
import com.finance.lumora.domain.repository.UserRepository
import com.finance.lumora.presentation.auth.intent.RegisterEvent
import com.finance.lumora.presentation.auth.state.RegisterState
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(RegisterState())

    val uiState: StateFlow<RegisterState> = _uiState.asStateFlow()

    fun onEvent(event: RegisterEvent) {
        when (event) {
            is RegisterEvent.ProfileImageSelected -> {
                _uiState.update {
                    it.copy(
                        profileImageUri = event.uri
                    )
                }
            }

            is RegisterEvent.NameChanged -> {
                _uiState.update {
                    it.copy(
                        name = event.name,
                        nameError = null
                    )
                }
            }

            is RegisterEvent.EmailChanged -> {
                _uiState.update {
                    it.copy(
                        email = event.email,
                        emailError = null
                    )
                }
            }

            is RegisterEvent.PasswordChanged -> {
                _uiState.update {
                    it.copy(
                        password = event.password,
                        passwordError = null
                    )
                }
            }

            is RegisterEvent.ConfirmPasswordChanged -> {
                _uiState.update {
                    it.copy(
                        confirmPassword = event.confirmPassword,
                        confirmPasswordError = null
                    )
                }
            }

            RegisterEvent.TogglePasswordVisibility -> {
                _uiState.update {
                    it.copy(
                        isPasswordVisible = !it.isPasswordVisible
                    )
                }
            }

            RegisterEvent.ToggleConfirmPasswordVisibility -> {
                _uiState.update {
                    it.copy(
                        isConfirmPasswordVisible = !it.isConfirmPasswordVisible
                    )
                }
            }

            RegisterEvent.ClearError -> {
                _uiState.update {
                    it.copy(
                        errorMessage = null
                    )
                }
            }

            RegisterEvent.RegisterClicked -> {
                register()
            }
        }
    }

    private fun register() {
        val state = _uiState.value

        var hasError = false

        var nameError: String? = null
        var emailError: String? = null
        var passwordError: String? = null
        var confirmPasswordError: String? = null

        if (state.name.isBlank()) {
            nameError = "Name is required"
            hasError = true
        }

        if (state.email.isBlank()) {
            emailError = "Email is required"
            hasError = true
        }

        if (state.password.length < 6) {
            passwordError = "Password must be at least 6 characters"
            hasError = true
        }

        if (state.password != state.confirmPassword) {
            confirmPasswordError = "Passwords do not match"
            hasError = true
        }

        if (hasError) {
            _uiState.update {
                it.copy(
                    nameError = nameError,
                    emailError = emailError,
                    passwordError = passwordError,
                    confirmPasswordError = confirmPasswordError
                )
            }
            return
        }

        viewModelScope.launch {
            _uiState.update {
                it.copy(isLoading = true)
            }

            authRepository.register(
                fullName = state.name,
                email = state.email,
                password = state.password
            ).onSuccess { authUser ->

                // Upload selected image to Firebase Storage if available
                val uploadedPhotoUrl = uploadProfileImageIfSelected(
                    userId = authUser.uid,
                    imageUri = state.profileImageUri
                )

                val userProfile = UserProfile(
                    uid = authUser.uid,
                    fullName = authUser.displayName ?: state.name,
                    email = authUser.email,
                    photoUrl = uploadedPhotoUrl,
                    createdAt = System.currentTimeMillis(),
                    lastLogin = System.currentTimeMillis(),
                    currency = "INR",
                    theme = "SYSTEM",
                    language = "en",
                    notificationsEnabled = true,
                    emailNotifications = true,
                    onboardingCompleted = false
                )

                userRepository
                    .saveUserProfile(userProfile)
                    .onSuccess {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                registrationSuccess = true
                            )
                        }
                    }
                    .onFailure { exception ->
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                errorMessage = exception.message ?: "Failed to save profile."
                            )
                        }
                    }

            }.onFailure { exception ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = exception.message ?: "Registration failed"
                    )
                }
            }
        }
    }

    /**
     * Uploads local image URI to Firebase Storage and returns the public HTTP Download URL string.
     */
    private suspend fun uploadProfileImageIfSelected(
        userId: String,
        imageUri: Uri?
    ): String? {
        if (imageUri == null) return null

        return runCatching {
            val storageRef = FirebaseStorage.getInstance().reference
                .child("profile_images/$userId.jpg")

            // Upload file to Firebase Storage
            storageRef.putFile(imageUri).await()

            // Get permanent HTTP download URL
            storageRef.downloadUrl.await().toString()
        }.getOrNull()
    }
}