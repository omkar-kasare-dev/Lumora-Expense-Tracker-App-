package com.finance.lumora.data.remote.dto


/**
 * Firestore representation of a user profile.
 *
 * This model is stored inside:
 *
 * users/{uid}
 */
data class UserDto(

    val uid: String = "",

    val name: String = "",

    val email: String = "",

    val photoUrl: String? = null,

    val createdAt: Long = System.currentTimeMillis()

)