package com.finance.lumora.presentation.auth.state



data class RegisterState(

    val name: String = "",

    val email: String = "",

    val password: String = "",

    val confirmPassword: String = "",

    val isPasswordVisible: Boolean = false,

    val isConfirmPasswordVisible: Boolean = false,

    val isLoading: Boolean = false,

    val registrationSuccess: Boolean = false,

    val errorMessage: String? = null,

    val nameError: String? = null,

    val emailError: String? = null,

    val passwordError: String? = null,

    val confirmPasswordError: String? = null

)