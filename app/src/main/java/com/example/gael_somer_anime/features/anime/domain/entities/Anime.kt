package com.example.gael_somer_anime.features.anime.domain.entities

data class Anime(
    val id: Int,
    val titulo: String,
    val genero: String,
    val anio: Int,
    val descripcion: String,
    val imageUrl: String?,
    val userId: Int,
    val tags: String = "",
    val likes: Int = 0
)
