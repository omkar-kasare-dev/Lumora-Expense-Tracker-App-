package com.finance.lumora.domain.usecase.auth



import com.finance.lumora.domain.repository.AuthRepository

class GetCurrentUserUseCase(

    private val repository: AuthRepository

) {

    operator fun invoke() =

        repository.getCurrentUser()

}