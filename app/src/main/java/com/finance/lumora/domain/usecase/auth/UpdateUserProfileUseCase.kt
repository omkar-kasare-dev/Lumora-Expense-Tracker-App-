package com.finance.lumora.domain.usecase.auth


import com.finance.lumora.domain.model.UserProfile
import com.finance.lumora.domain.repository.UserRepository
import javax.inject.Inject

class UpdateUserProfileUseCase @Inject constructor(
    private val repository: UserRepository
) {

    suspend operator fun invoke(
        userProfile: UserProfile
    ): Result<Unit> {

        return repository.updateUserProfile(
            userProfile = userProfile
        )
    }
}

