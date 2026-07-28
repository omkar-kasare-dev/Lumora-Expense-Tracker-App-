package com.finance.lumora.presentation.auth.state



import com.finance.lumora.domain.model.User

data class AuthUiState(

    val isLoading: Boolean = false,

    val currentUser: User? = null,

    val isLoggedIn: Boolean = false,

    val errorMessage: String? = null

)