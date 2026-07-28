package com.finance.lumora.domain.usecase.auth



data class AuthUseCases(

    val login: LoginUseCase,

    val register: RegisterUseCase,

    val logout: LogoutUseCase,

    val getCurrentUser: GetCurrentUserUseCase,

    val observeAuthState: ObserveAuthStateUseCase

)