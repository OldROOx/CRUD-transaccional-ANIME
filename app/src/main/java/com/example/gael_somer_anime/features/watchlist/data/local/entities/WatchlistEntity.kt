package com.example.gael_somer_anime.features.watchlist.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "watchlist")
data class WatchlistEntity(
    @PrimaryKey val id: Int,
    val userId: Int,
    val animeId: Int,
    val estado: String,
    val updatedAt: String,
    val animeTitulo: String?,
    val animeGenero: String?,
    val animeAnio: Int?,
    val animeDescripcion: String?,
    val animeCreatedAt: String?
)
