package com.finance.lumora.domain.usecase.auth

import com.finance.lumora.domain.model.UserProfile
import com.finance.lumora.domain.repository.UserRepository
import javax.inject.Inject

class GetUserProfileUseCase @Inject constructor(

    private val repository: UserRepository

) {

    suspend operator fun invoke(

        uid: String

    ): Result<UserProfile> {

        return repository.getUserProfile(uid)

    }

}