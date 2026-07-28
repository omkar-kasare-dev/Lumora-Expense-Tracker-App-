package com.finance.lumora.data.mapper



import com.finance.lumora.data.remote.dto.UserDto
import com.finance.lumora.domain.model.User

/**
 * Maps Firestore UserDto to Domain User.
 */
fun UserDto.toDomain(): User {

    return User(

        uid = uid,

        name = name,

        email = email

    )

}

/**
 * Maps Domain User to Firestore UserDto.
 */
fun User.toDto(): UserDto {

    return UserDto(

        uid = uid,

        name = name,

        email = email

    )

}