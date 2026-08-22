package com.finance.lumora.presentation.profile.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.finance.lumora.presentation.profile.intent.EditProfileEvent
import com.finance.lumora.presentation.profile.screen.EditProfileScreen
import com.finance.lumora.presentation.profile.viewmodel.EditProfileViewModel

@Composable
fun EditProfileRoute(
    onBackClick: () -> Unit,
    viewModel: EditProfileViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    /*
     * Load the current user's profile
     * when this route enters composition.
     */
    LaunchedEffect(Unit) {
        viewModel.loadProfile()
    }

    /*
     * Navigate back after successful save.
     * The ViewModel owns the save operation.
     * The Route owns navigation.
     */
    LaunchedEffect(uiState.isSaved) {
        if (uiState.isSaved) {
            onBackClick()
        }
    }

    when {
        // ----------------------------------------
        // Loading profile
        // ----------------------------------------
        uiState.isLoading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        // ----------------------------------------
        // Profile loading error
        // ----------------------------------------
        uiState.errorMessage != null && uiState.fullName.isBlank() -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = uiState.errorMessage ?: "Unable to load profile."
                )
            }
        }

        // ----------------------------------------
        // Profile loaded
        // ----------------------------------------
        else -> {
            EditProfileScreen(
                state = uiState,
                onBackClick = onBackClick,
                onFullNameChange = { fullName ->
                    viewModel.onEvent(EditProfileEvent.FullNameChanged(fullName))
                },
                onCurrencyChange = { currency ->
                    viewModel.onEvent(EditProfileEvent.CurrencyChanged(currency))
                },
                onLanguageChange = { language ->
                    viewModel.onEvent(EditProfileEvent.LanguageChanged(language))
                },
                onSaveClick = {
                    viewModel.onEvent(EditProfileEvent.SaveProfile)
                }
            )
        }
    }
}