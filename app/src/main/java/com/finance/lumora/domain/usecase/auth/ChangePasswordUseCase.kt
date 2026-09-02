package com.finance.lumora.domain.usecase.auth


import com.finance.lumora.domain.repository.AuthRepository

class ChangePasswordUseCase(
    private val repository: AuthRepository
) {

    suspend operator fun invoke(
        currentPassword: String,
        newPassword: String
    ): Result<Unit> {
        return repository.changePassword(
            currentPassword = currentPassword,
            newPassword = newPassword
        )
    }
}