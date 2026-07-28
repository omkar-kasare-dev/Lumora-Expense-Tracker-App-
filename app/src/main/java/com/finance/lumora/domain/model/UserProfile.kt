package com.finance.lumora.domain.model

/**
 * Represents a user's profile stored in Cloud Firestore.
 *
 * Firebase Authentication is responsible only for authentication.
 * Additional profile information required by Lumora is stored separately
 * in the "users" collection.
 */
data class UserProfile(

    val uid: String = "",

    val fullName: String = "",

    val email: String = "",

    val photoUrl: String? = null,

    val createdAt: Long = System.currentTimeMillis(),

    val lastLogin: Long = System.currentTimeMillis(),


    val currency: String = "INR",

    val theme: String = "SYSTEM",

    val language: String = "en",


    val notificationsEnabled: Boolean = true,

    val emailNotifications: Boolean = true,

    val onboardingCompleted: Boolean = false
)