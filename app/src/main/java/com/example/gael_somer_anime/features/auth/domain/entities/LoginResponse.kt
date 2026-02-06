package com.example.gael_somer_anime.features.auth.domain.entities

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("access_token") val accessToken: String,
    @SerializedName("token_type") val tokenType: String,
    val user: User
)
