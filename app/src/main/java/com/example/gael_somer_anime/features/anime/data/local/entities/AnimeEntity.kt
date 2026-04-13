package com.example.gael_somer_anime.features.anime.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "animes")
data class AnimeEntity(
    @PrimaryKey val id: Int,
    val titulo: String,
    val genero: String,
    val anio: Int,
    val descripcion: String,
    val imageUrl: String?,
    val userId: Int,
    val tags: String = "", // Tags separados por coma
    val likes: Int = 0
)
