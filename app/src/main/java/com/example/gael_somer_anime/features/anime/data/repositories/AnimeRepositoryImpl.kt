package com.example.gael_somer_anime.features.anime.data.repositories

import com.example.gael_somer_anime.core.network.AnimeApiService
import com.example.gael_somer_anime.features.anime.data.local.dao.AnimeDao
import com.example.gael_somer_anime.features.anime.data.mappers.toDomain
import com.example.gael_somer_anime.features.anime.data.mappers.toEntity
import com.example.gael_somer_anime.features.anime.data.remote.models.AnimeRequestDto
import com.example.gael_somer_anime.features.anime.domain.entities.Anime
import com.example.gael_somer_anime.features.anime.domain.repositories.AnimeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class AnimeRepositoryImpl @Inject constructor(
    private val api: AnimeApiService,
    private val dao: AnimeDao
) : AnimeRepository {

    override fun getAnimes(): Flow<List<Anime>> = dao.getAllAnimes()
        .map { entities -> entities.map { it.toDomain() } }
        .onStart {
            try {
                val response = api.getAnimes()
                if (response.isSuccessful && response.body() != null) {
                    val dtos = response.body()!!
                    val entities = dtos.map { it.toEntity() }
                    dao.clearAll()
                    dao.insertAnimes(entities)
                }
            } catch (_: Exception) {

            }
        }

    override suspend fun getAnimeById(id: Int): Anime? {
        // Fallback robusto: si se solicita un ID específico (ej. notificación), buscarlo.
        return try {
            val response = api.getAnimeById(id)
            if (response.isSuccessful) {
                val domainAnime = response.body()?.toDomain()
                domainAnime?.let {
                    dao.insertAnime(it.toEntity())
                }
                domainAnime
            } else null
        } catch (_: Exception) {
            null
        }
    }

    override suspend fun syncAnimes(): Boolean {
        return try {
            val response = api.getAnimes()
            if (response.isSuccessful) {
                response.body()?.let { dtos ->
                    val entities = dtos.map { it.toEntity() }
                    dao.clearAll()
                    dao.insertAnimes(entities)
                    true
                } ?: false
            } else {
                false
            }
        } catch (_: Exception) {
            false
        }
    }

    override suspend fun createAnime(titulo: String, genero: String, anio: Int, descripcion: String, tags: String): Anime? {
        val request = AnimeRequestDto(titulo, genero, anio, descripcion, tags)
        return try {
            val response = api.createAnime(request)
            if (response.isSuccessful) {
                val domainAnime = response.body()?.toDomain()
                domainAnime?.let {
                    dao.insertAnime(it.toEntity())
                }
                domainAnime
            } else null
        } catch (_: Exception) {
            null
        }
    }

    override suspend fun updateAnime(id: Int, titulo: String, genero: String, anio: Int, descripcion: String, tags: String): Anime? {
        val request = AnimeRequestDto(titulo, genero, anio, descripcion, tags)
        return try {
            val response = api.updateAnime(id, request)
            if (response.isSuccessful) {
                val domainAnime = response.body()?.toDomain()
                domainAnime?.let {
                    dao.insertAnime(it.toEntity())
                }
                domainAnime
            } else null
        } catch (_: Exception) {
            null
        }
    }

    override suspend fun deleteAnime(id: Int): Boolean {
        return try {
            val response = api.deleteAnime(id)
            if (response.isSuccessful) {
                dao.deleteAnimeById(id)
                true
            } else false
        } catch (_: Exception) {
            false
        }
    }

    override suspend fun uploadImage(animeId: Int, file: File): Boolean {
        return try {
            val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
            val body = MultipartBody.Part.createFormData("file", file.name, requestFile)
            val response = api.uploadAnimeImage(animeId, body)
            if (response.isSuccessful) {
                response.body()?.let { dto ->
                    dao.insertAnime(dto.toEntity())
                }
                true
            } else {
                false
            }
        } catch (e: Exception) {
            false
        }
    }
}
