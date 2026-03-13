package com.example.gael_somer_anime.features.watchlist.domain.entities

data class Watchlist(
    val id: Int,
    val userId: Int,
    val animeId: Int,
    val estado: WatchlistStatus,
    val updatedAt: String,
    val anime: WatchlistAnime? = null
)

data class WatchlistAnime(
    val id: Int,
    val titulo: String,
    val genero: String,
    val anio: Int,
    val descripcion: String,
    val createdAt: String
)

enum class WatchlistStatus(val value: String) {
    VIENDO("viendo"),
    COMPLETADO("completado"),
    POR_VER("por_ver");

    companion object {
        fun fromString(value: String): WatchlistStatus {
            return WatchlistStatus.entries.find { it.value == value } ?: POR_VER
        }
    }
}
