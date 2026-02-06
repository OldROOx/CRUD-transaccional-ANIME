package com.example.gael_somer_anime.features.auth.data.remote.mappers

import com.example.gael_somer_anime.features.auth.data.remote.models.LoginResponseDto
import com.example.gael_somer_anime.features.auth.data.remote.models.UserDto
import com.example.gael_somer_anime.features.auth.domain.entities.LoginResponse
import com.example.gael_somer_anime.features.auth.domain.entities.User

fun UserDto.toDomain(): User {
    return User(
        id = id,
        username = username,
        email = email,
        createdAt = createdAt
    )
}

fun LoginResponseDto.toDomain(): LoginResponse {
    return LoginResponse(
        accessToken = accessToken,
        tokenType = tokenType,
        user = user.toDomain()
    )
}