package com.example.gael_somer_anime.features.anime.domain.repositories

import com.example.gael_somer_anime.features.anime.domain.entities.Anime

interface AnimeRepository {
    suspend fun getAnimes(): List<Anime>?
    suspend fun createAnime(titulo: String, genero: String, anio: Int, descripcion: String): Anime?
    suspend fun updateAnime(id: Int, titulo: String, genero: String, anio: Int, descripcion: String): Anime?
    suspend fun deleteAnime(id: Int): Boolean
}
