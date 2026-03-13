package com.example.gael_somer_anime.features.anime.data.local.entities

import androidx.room.Entity

@Entity(
    tableName = "favorites",
    primaryKeys = ["userId", "animeId"]
)
data class FavoriteEntity(
    val userId: String,
    val animeId: Int,
    val titulo: String,
    val genero: String,
    val anio: Int,
    val descripcion: String
)