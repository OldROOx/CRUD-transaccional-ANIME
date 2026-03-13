package com.example.gael_somer_anime.features.favorites.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites")
data class FavoriteEntity(
    @PrimaryKey val id: Int,
    val titulo: String,
    val genero: String,
    val anio: Int,
    val descripcion: String
)
