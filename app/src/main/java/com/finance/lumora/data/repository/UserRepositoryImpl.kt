package com.finance.lumora.data.repository


/*
import com.finance.lumora.domain.model.UserProfile
import com.finance.lumora.domain.repository.UserRepository
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration

import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor(

    private val firestore: FirebaseFirestore

) : UserRepository {

    companion object {

        private const val USERS_COLLECTION = "users"

    }

    override suspend fun saveUserProfile(
        userProfile: UserProfile
    ): Result<Unit> {

        return try {

            firestore
                .collection(USERS_COLLECTION)
                .document(userProfile.uid)
                .set(userProfile)
                .await()

            Result.success(Unit)

        } catch (exception: Exception) {

            Result.failure(exception)

        }

    }

    override suspend fun getUserProfile(
        uid: String
    ): Result<UserProfile> {

        return try {

            val snapshot = firestore
                .collection(USERS_COLLECTION)
                .document(uid)
                .get()
                .await()

            val profile = snapshot.toObject(UserProfile::class.java)

            if (profile != null) {

                Result.success(profile)

            } else {

                Result.failure(
                    Exception("User profile not found.")
                )

            }

        } catch (exception: Exception) {

            Result.failure(exception)

        }

    }

    override suspend fun updateUserProfile(
        userProfile: UserProfile
    ): Result<Unit> {

        return try {

            firestore
                .collection(USERS_COLLECTION)
                .document(userProfile.uid)
                .set(userProfile)
                .await()

            Result.success(Unit)

        } catch (exception: Exception) {

            Result.failure(exception)

        }

    }

    override fun observeUserProfile(
        uid: String
    ): Flow<UserProfile?> = callbackFlow {

        val listener: ListenerRegistration = firestore
            .collection(USERS_COLLECTION)
            .document(uid)
            .addSnapshotListener { snapshot, error ->

                if (error != null) {

                    close(error)

                    return@addSnapshotListener

                }

                val profile = snapshot?.toObject(UserProfile::class.java)

                trySend(profile)

            }

        awaitClose {

            listener.remove()

        }

    }

    override suspend fun deleteUserProfile(
        uid: String
    ): Result<Unit> {

        return try {

            firestore
                .collection(USERS_COLLECTION)
                .document(uid)
                .delete()
                .await()

            Result.success(Unit)

        } catch (exception: Exception) {

            Result.failure(exception)

        }

    }

}

 */



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

        TODO("Phase 10")

    }


}