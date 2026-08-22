package com.finance.lumora.presentation.profile.state

import com.finance.lumora.domain.model.UserProfile


data class EditProfileState(
    val profile: UserProfile? = null,
    val fullName: String = "",

    val email: String = "",

    val currency: String = "INR",

    val language: String = "en",

    val isLoading: Boolean = false,

    val isSaving: Boolean = false,

    val saveSuccess: Boolean = false,


    /**
     * Error shown to the user.
     */
    val errorMessage: String? = null,

    /**
     * Becomes true after a successful update.
     */
    val isSaved: Boolean = false

)
