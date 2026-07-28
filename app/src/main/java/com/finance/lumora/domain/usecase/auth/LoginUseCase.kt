package com.finance.lumora.domain.usecase.auth



import com.finance.lumora.domain.repository.AuthRepository

class LoginUseCase(

    private val repository: AuthRepository

) {

    suspend operator fun invoke(

        email: String,

        password: String

    ) = repository.login(

        email,

        password

    )

}