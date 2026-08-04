package com.finance.lumora.data.repository


import com.finance.lumora.data.mapper.toDomain
import com.finance.lumora.data.remote.auth.FirebaseAuthDataSource
import com.finance.lumora.domain.model.User
import com.finance.lumora.domain.model.AuthUser
import com.finance.lumora.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

import com.google.firebase.FirebaseNetworkException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException

class AuthRepositoryImpl @Inject constructor(

    private val dataSource: FirebaseAuthDataSource

) : AuthRepository {

    /**
     * Register a new user.
     */
    override suspend fun register(

        fullName: String,

        email: String,

        password: String

    ): Result<AuthUser> {

        return try {

            Result.success(

                dataSource.register(

                    fullName = fullName,

                    email = email,

                    password = password

                )

            )

        } catch (e: Exception) {

            Result.failure(e)

        }

    }
    /**
     * Login existing user.
     */
    override suspend fun login(
        email: String,
        password: String
    ): Result<User> {

        return try {

            Result.success(

                dataSource
                    .login(
                        email = email,
                        password = password
                    )
                    .toDomain()

            )

        } catch (e: Exception) {

            Result.failure(e)

        }

    }

    /**
     * Logout current user.
     */
    override suspend fun logout() {

        dataSource.logout()

    }

    /**
     * Currently logged-in user.
     */
    override fun getCurrentUser(): User? {

        return dataSource
            .getCurrentUser()
            ?.toDomain()

    }

    /**
     * Observe authentication state.
     */
    override fun observeAuthState(): Flow<User?> {

        return dataSource
            .observeAuthState()
            .map { dto ->

                dto?.toDomain()

            }

    }
/*
    override suspend fun sendPasswordReset(
        email: String
    ): Result<Unit> {

        return try {

            firebaseAuth
                .sendPasswordResetEmail(email)
                .await()

            Result.success(Unit)

        } catch (e: Exception) {

            Result.failure(
                Exception(
                    mapFirebaseAuthError(e)
                )
            )

        }

    }

 */

    override suspend fun sendPasswordReset(
        email: String
    ): Result<Unit> {
        return try {
            dataSource.sendPasswordReset(email)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(
                Exception(mapFirebaseAuthError(e))
            )
        }
    }

    private fun mapFirebaseAuthError(
        exception: Exception
    ): String {

        return when (exception) {

            is FirebaseAuthInvalidCredentialsException ->

                "Please enter a valid email address."

            is FirebaseAuthInvalidUserException ->

                "No account found with this email address."

            is FirebaseNetworkException ->

                "No internet connection. Please check your network and try again."

            is FirebaseTooManyRequestsException ->

                "Too many attempts. Please wait a few minutes and try again."

            is FirebaseAuthException ->

                when (exception.errorCode) {

                    "ERROR_USER_NOT_FOUND" ->
                        "No account found with this email address."

                    "ERROR_INVALID_EMAIL" ->
                        "Please enter a valid email address."

                    else ->
                        "Authentication failed. Please try again."

                }

            else ->

                "Something went wrong. Please try again."

        }

    }

}