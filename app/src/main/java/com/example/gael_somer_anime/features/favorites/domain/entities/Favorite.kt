package com.example.gael_somer_anime.features.favorites.domain.entities

data class Favorite(
    val id: Int,
    val titulo: String,
    val genero: String,
    val anio: Int,
    val descripcion: String
)