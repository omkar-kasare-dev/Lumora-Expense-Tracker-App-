package com.finance.lumora.presentation.profile.state


import com.finance.lumora.domain.model.UserProfile

data class ProfileState(

    val profile: UserProfile? = null,

    val isLoading: Boolean = false,

    val isUpdating: Boolean = false,

    val errorMessage: String? = null,

    val updateSuccess: Boolean = false
)