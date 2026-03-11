package com.example.gael_somer_anime.features.anime.domain.repositories

import com.example.gael_somer_anime.features.anime.domain.entities.Anime
import kotlinx.coroutines.flow.Flow

interface AnimeRepository {
    fun getAnimes(): Flow<List<Anime>>
    suspend fun syncAnimes(): Boolean
    suspend fun createAnime(titulo: String, genero: String, anio: Int, descripcion: String): Anime?
    suspend fun updateAnime(id: Int, titulo: String, genero: String, anio: Int, descripcion: String): Anime?
    suspend fun deleteAnime(id: Int): Boolean
}
