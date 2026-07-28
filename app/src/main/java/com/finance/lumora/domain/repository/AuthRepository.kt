package com.finance.lumora.domain.repository



import com.finance.lumora.domain.model.User
import com.finance.lumora.domain.model.AuthUser
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    suspend fun register(

        fullName: String,

        email: String,

        password: String

    ): Result<AuthUser>

    suspend fun login(

        email: String,

        password: String

    ): Result<User>

    suspend fun logout()

    fun getCurrentUser(): User?

    fun observeAuthState(): Flow<User?>

    /**
     * Sends password reset email.
     */
    suspend fun sendPasswordReset(
        email: String
    ): Result<Unit>

}