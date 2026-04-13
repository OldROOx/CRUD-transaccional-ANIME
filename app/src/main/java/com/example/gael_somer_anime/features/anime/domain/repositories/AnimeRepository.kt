package com.example.gael_somer_anime.features.anime.domain.repositories

import com.example.gael_somer_anime.features.anime.domain.entities.Anime
import kotlinx.coroutines.flow.Flow
import java.io.File

interface AnimeRepository {
    fun getAnimes(): Flow<List<Anime>>
    suspend fun getAnimeById(id: Int): Anime?
    suspend fun syncAnimes(): Boolean
    suspend fun createAnime(titulo: String, genero: String, anio: Int, descripcion: String, tags: String = ""): Anime?
    suspend fun updateAnime(id: Int, titulo: String, genero: String, anio: Int, descripcion: String, tags: String = ""): Anime?
    suspend fun deleteAnime(id: Int): Boolean
    suspend fun likeAnime(id: Int): Anime?
    suspend fun uploadImage(animeId: Int, file: File): Boolean
}
