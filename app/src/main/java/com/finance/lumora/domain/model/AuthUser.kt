package com.finance.lumora.domain.model



/**
 * Represents an authenticated Firebase user.
 *
 * This model keeps the domain layer independent
 * from Firebase SDK classes.
 */
data class AuthUser(

    val uid: String,

    val email: String,

    val displayName: String? = null

)