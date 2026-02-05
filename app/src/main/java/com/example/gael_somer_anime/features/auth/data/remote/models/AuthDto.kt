package com.example.gael_somer_anime.features.auth.data.remote.models

data class LoginRequestDto(
    val username: String,
    val password: String
)

data class RegisterRequestDto(
    val username: String,
    val email: String,
    val password: String
)