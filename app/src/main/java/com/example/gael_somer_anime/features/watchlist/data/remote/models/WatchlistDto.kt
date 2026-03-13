package com.example.gael_somer_anime.features.watchlist.data.remote.models

import com.google.gson.annotations.SerializedName

data class WatchlistRequestDto(
    @SerializedName("anime_id") val animeId: Int,
    val estado: String
)

data class WatchlistResponseDto(
    val id: Int,
    @SerializedName("user_id") val userId: Int,
    @SerializedName("anime_id") val animeId: Int,
    val estado: String,
    @SerializedName("updated_at") val updatedAt: String,
    val anime: WatchlistAnimeDto?
)

data class WatchlistAnimeDto(
    val id: Int,
    val titulo: String,
    val genero: String,
    @SerializedName("año") val anio: Int,
    val descripcion: String,
    @SerializedName("created_at") val createdAt: String
)

data class DeleteWatchlistResponse(
    val message: String
)
