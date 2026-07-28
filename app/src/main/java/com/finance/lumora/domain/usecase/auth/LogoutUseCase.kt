package com.finance.lumora.domain.usecase.auth



import com.finance.lumora.domain.repository.AuthRepository

class LogoutUseCase(

    private val repository: AuthRepository

) {

    suspend operator fun invoke() {

        repository.logout()

    }

}