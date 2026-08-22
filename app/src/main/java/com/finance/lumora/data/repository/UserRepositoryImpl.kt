package com.finance.lumora.data.repository


import com.finance.lumora.data.remote.auth.FirestoreUserDataSource
import com.finance.lumora.domain.model.UserProfile
import com.finance.lumora.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(

    private val dataSource: FirestoreUserDataSource

) : UserRepository {

    override suspend fun saveUserProfile(

        userProfile: UserProfile

    ): Result<Unit> {

        return try {

            dataSource.saveUserProfile(userProfile)

            Result.success(Unit)

        } catch (e: Exception) {

            Result.failure(e)

        }

    }

    //----------------------
    override suspend fun updateLastLogin(

        uid: String

    ): Result<Unit> {

        return try {

            dataSource.updateLastLogin(uid)

            Result.success(Unit)

        } catch (e: Exception) {

            Result.failure(e)

        }

    }
    //--------------

    /**
     * Returns a user's profile from Firestore.
     */
    override suspend fun getUserProfile(
        uid: String
    ): Result<UserProfile> {

        return try {

            Result.success(

                dataSource.getUserProfile(uid)

            )

        } catch (e: Exception) {

            Result.failure(e)

        }

    }

    override suspend fun updateUserProfile(
        userProfile: UserProfile
    ): Result<Unit> {

        return try {

            dataSource.updateUserProfile(userProfile)

            Result.success(Unit)

        } catch (e: Exception) {

            Result.failure(e)

        }
    }


}