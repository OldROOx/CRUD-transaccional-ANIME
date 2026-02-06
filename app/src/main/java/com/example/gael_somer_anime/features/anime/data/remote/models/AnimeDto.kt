package com.example.gael_somer_anime.features.anime.data.remote.models

import com.google.gson.annotations.SerializedName

data class AnimeRequestDto(
    val titulo: String,
    val genero: String,
    @SerializedName("año") val anio: Int,
    val descripcion: String
)

data class AnimeResponseDto(
    val id: Int,
    val titulo: String,
    val genero: String,
    @SerializedName("año") val anio: Int,
    val descripcion: String,
    @SerializedName("created_at") val createdAt: String
)
