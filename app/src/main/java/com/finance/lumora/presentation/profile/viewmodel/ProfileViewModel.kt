package com.finance.lumora.presentation.profile.viewmodel



import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.finance.lumora.domain.repository.AuthRepository
import com.finance.lumora.domain.usecase.auth.GetUserProfileUseCase
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
    private val authRepository: AuthRepository,


) : ViewModel() {


    private val _uiState = MutableStateFlow(
        ProfileState()
    )

    val uiState: StateFlow<ProfileState> =
        _uiState.asStateFlow()


    //-----------------------------------
/*
    fun loadProfile(
        uid: String
    ) {

        viewModelScope.launch {

            _uiState.update {

                it.copy(

                    isLoading = true,

                    errorMessage = null

                )

            }

            getUserProfileUseCase(uid)

                .onSuccess { profile ->

                    _uiState.update {

                        it.copy(

                            profile = profile,

                            isLoading = false

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


 */

    fun loadProfile() {

        val currentUser = authRepository.getCurrentUser()

        Log.d("PROFILE", "Current User = $currentUser")

        if (currentUser == null) {
            Log.d("PROFILE", "User is null")

            _uiState.update {

                it.copy(

                    isLoading = false,

                    errorMessage = "User is not logged in."

                )

            }

            return

        }

        viewModelScope.launch {
            Log.d("PROFILE", "Loading uid = ${currentUser.uid}")

            _uiState.update {

                it.copy(

                    isLoading = true,

                    errorMessage = null

                )

            }

            getUserProfileUseCase(currentUser.uid)

                .onSuccess { profile ->
                    Log.d("PROFILE", "Firestore profile = $profile")


                    _uiState.update {

                        it.copy(

                            profile = profile,

                            isLoading = false

                        )

                    }

                }

                .onFailure { exception ->
                    Log.e("PROFILE", exception.stackTraceToString())

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


    //---------------------------
    fun logout() {

        viewModelScope.launch {

            authRepository.logout()

        }

    }



}