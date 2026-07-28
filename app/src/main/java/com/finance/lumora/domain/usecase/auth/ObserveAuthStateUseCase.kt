package com.finance.lumora.domain.usecase.auth




import com.finance.lumora.domain.model.User
import com.finance.lumora.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveAuthStateUseCase @Inject constructor(

    private val repository: AuthRepository

) {

    operator fun invoke(): Flow<User?> {

        return repository.observeAuthState()

    }

}