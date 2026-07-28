package com.finance.lumora.data.remote.auth


import com.finance.lumora.data.remote.dto.UserDto
import com.finance.lumora.domain.model.AuthUser
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuth.AuthStateListener
import com.google.firebase.auth.userProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


class FirebaseAuthDataSource @Inject constructor(

    private val firebaseAuth: FirebaseAuth,

    private val firestore: FirebaseFirestore

) {
/*
    /**
     * Register new user
     */
    suspend fun register(

        name: String,

        email: String,

        password: String

    ): UserDto {

        val authResult = firebaseAuth
            .createUserWithEmailAndPassword(
                email,
                password
            )
            .await()

        val firebaseUser = authResult.user
            ?: throw Exception("Registration failed.")

        val userDto = UserDto(

            uid = firebaseUser.uid,

            name = name,

            email = email

        )

        firestore
            .collection("users")
            .document(firebaseUser.uid)
            .set(userDto)
            .await()

        return userDto
    }

 */
    /**
     * Register new user
     */
    suspend fun register(

        fullName: String,

        email: String,

        password: String

    ): AuthUser {

        val authResult = firebaseAuth
            .createUserWithEmailAndPassword(
                email,
                password
            )
            .await()

        val firebaseUser = authResult.user
            ?: throw Exception("Registration failed.")

        val profileUpdates = userProfileChangeRequest {

            displayName = fullName

        }

        firebaseUser
            .updateProfile(profileUpdates)
            .await()

        return AuthUser(

            uid = firebaseUser.uid,

            email = firebaseUser.email.orEmpty(),

            displayName = fullName

        )
    }


    /**
     * Login existing user
     */
    suspend fun login(

        email: String,

        password: String

    ): UserDto {

        val authResult = firebaseAuth
            .signInWithEmailAndPassword(
                email,
                password
            )
            .await()

        val firebaseUser = authResult.user
            ?: throw Exception("Login failed.")

        return firestore
            .collection("users")
            .document(firebaseUser.uid)
            .get()
            .await()
            .toObject(UserDto::class.java)
            ?: throw Exception("User profile not found.")
    }

    /**
     * Logout
     */
    fun logout() {

        firebaseAuth.signOut()

    }

    /**
     * Current logged in user
     */
    fun getCurrentUser(): UserDto? {

        val firebaseUser = firebaseAuth.currentUser
            ?: return null

        return UserDto(

            uid = firebaseUser.uid,

            name = firebaseUser.displayName ?: "",

            email = firebaseUser.email ?: "",

            photoUrl = firebaseUser.photoUrl?.toString()

        )
    }

    /**
     * Observe authentication state
     */
    fun observeAuthState(): Flow<UserDto?> = callbackFlow {

        val listener = AuthStateListener { auth ->

            val user = auth.currentUser

            trySend(

                user?.let {

                    UserDto(

                        uid = it.uid,

                        name = it.displayName ?: "",

                        email = it.email ?: "",

                        photoUrl = it.photoUrl?.toString()

                    )

                }

            )

        }

        firebaseAuth.addAuthStateListener(listener)

        awaitClose {

            firebaseAuth.removeAuthStateListener(listener)

        }

    }
    // In FirebaseAuthDataSource.kt
    suspend fun sendPasswordReset(
        email: String
    ) {

        firebaseAuth
            .sendPasswordResetEmail(email)
            .await()

    }


}