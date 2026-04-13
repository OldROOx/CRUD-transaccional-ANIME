package com.example.gael_somer_anime.features.anime.data.remote.models

import com.google.gson.annotations.SerializedName

data class AnimeRequestDto(
    val titulo: String,
    val genero: String,
    @SerializedName("año") val anio: Int,
    val descripcion: String,
    val tags: String = ""  // Tags separados por coma: "accion,romance"
)

data class AnimeResponseDto(
    val id: Int,
    val titulo: String,
    val genero: String,
    @SerializedName("año") val anio: Int,
    val descripcion: String,
    @SerializedName("image_url") val imageUrl: String?,
    val tags: String? = "",
    val likes: Int = 0,
    @SerializedName("user_id") val userId: Int,
    @SerializedName("created_at") val createdAt: String
)
