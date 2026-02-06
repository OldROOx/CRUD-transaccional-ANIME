package com.example.gael_somer_anime.features.auth.domain.entities

import com.google.gson.annotations.SerializedName

data class User(
    val id: Int,
    val username: String,
    val email: String,
    @SerializedName("created_at") val createdAt: String
)
