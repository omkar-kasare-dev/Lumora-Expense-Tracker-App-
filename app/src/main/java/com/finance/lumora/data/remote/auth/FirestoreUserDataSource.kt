package com.finance.lumora.data.remote.auth


import com.finance.lumora.domain.model.UserProfile
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


class FirestoreUserDataSource @Inject constructor(

    private val firestore: FirebaseFirestore

) {

    private val usersCollection =

        firestore.collection("users")

    suspend fun saveUserProfile(

        userProfile: UserProfile

    ) {

        usersCollection

            .document(userProfile.uid)

            .set(userProfile)

            .await()

    }

    //--------------
    suspend fun updateLastLogin(
        uid: String
    ) {

        firestore
            .collection("users")
            .document(uid)
            .update(

                "lastLogin",
                System.currentTimeMillis()

            )
            .await()

    }

    /**
     * Returns a user's profile from Firestore.
     */
    suspend fun getUserProfile(
        uid: String
    ): UserProfile {

        return firestore
            .collection("users")
            .document(uid)
            .get()
            .await()
            .toObject(UserProfile::class.java)
            ?: throw Exception("User profile not found.")

    }

    // Update Profile-Section:

    suspend fun updateUserProfile(
        userProfile: UserProfile
    ) {
        usersCollection
            .document(userProfile.uid)
            .set(userProfile)
            .await()
    }


}