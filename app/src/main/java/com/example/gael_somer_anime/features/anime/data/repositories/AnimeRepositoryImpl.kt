package com.example.gael_somer_anime.features.anime.data.repositories

import com.example.gael_somer_anime.core.network.AnimeApiService
import com.example.gael_somer_anime.features.anime.data.mappers.toDomain
import com.example.gael_somer_anime.features.anime.data.remote.models.AnimeRequestDto
import com.example.gael_somer_anime.features.anime.domain.entities.Anime
import com.example.gael_somer_anime.features.anime.domain.repositories.AnimeRepository

class AnimeRepositoryImpl(
    private val api: AnimeApiService
) : AnimeRepository {
    override suspend fun getAnimes(): List<Anime>? {
        val response = api.getAnimes()
        return if (response.isSuccessful) {
            response.body()?.map { it.toDomain() }
        } else {
            null
        }
    }

    override suspend fun createAnime(titulo: String, genero: String, anio: Int, descripcion: String): Anime? {
        val request = AnimeRequestDto(titulo, genero, anio, descripcion)
        val response = api.createAnime(request)
        return if (response.isSuccessful) {
            response.body()?.toDomain()
        } else {
            null
        }
    }

    override suspend fun updateAnime(id: Int, titulo: String, genero: String, anio: Int, descripcion: String): Anime? {
        val request = AnimeRequestDto(titulo, genero, anio, descripcion)
        val response = api.updateAnime(id, request)
        return if (response.isSuccessful) {
            response.body()?.toDomain()
        } else {
            null
        }
    }

    override suspend fun deleteAnime(id: Int): Boolean {
        val response = api.deleteAnime(id)
        return response.isSuccessful // Cambiado
    }
}