package com.finance.lumora.domain.repository


import com.finance.lumora.domain.model.UserProfile
import kotlinx.coroutines.flow.Flow

/**
 * Repository contract for managing user profiles stored in Cloud Firestore.
 *
 * This interface belongs to the Domain layer and must not contain any
 * Firebase-specific implementation details.
 */
interface UserRepository {

    /**
     * Creates a new user profile in Firestore.
     */
    suspend fun saveUserProfile(
        userProfile: UserProfile
    ): Result<Unit>

    /**
     * Returns the profile of the specified user.
     */
    suspend fun getUserProfile(
        uid: String
    ): Result<UserProfile>

    /**
     * Updates an existing user profile.
     */
    suspend fun updateUserProfile(
        userProfile: UserProfile
    ): Result<Unit>

    suspend fun updateLastLogin(
        uid: String
    ): Result<Unit>



/*
    /**
     * Observes profile changes in real time.
     */
    fun observeUserProfile(
        uid: String
    ): Flow<UserProfile?>

    /**
     * Deletes the user's profile document.
     *
     * Note:
     * This only removes the Firestore document.
     * Firebase Authentication account deletion
     * will be handled separately.
     */
    suspend fun deleteUserProfile(
        uid: String
    ): Result<Unit>

 */
}