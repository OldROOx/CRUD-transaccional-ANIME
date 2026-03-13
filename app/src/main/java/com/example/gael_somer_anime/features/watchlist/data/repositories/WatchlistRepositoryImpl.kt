package com.example.gael_somer_anime.features.watchlist.data.repositories

import com.example.gael_somer_anime.core.network.AnimeApiService
import com.example.gael_somer_anime.features.watchlist.data.local.dao.WatchlistDao
import com.example.gael_somer_anime.features.watchlist.data.local.entities.WatchlistEntity
import com.example.gael_somer_anime.features.watchlist.data.remote.models.WatchlistRequestDto
import com.example.gael_somer_anime.features.watchlist.domain.entities.Watchlist
import com.example.gael_somer_anime.features.watchlist.domain.entities.WatchlistAnime
import com.example.gael_somer_anime.features.watchlist.domain.entities.WatchlistStatus
import com.example.gael_somer_anime.features.watchlist.domain.repositories.WatchlistRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class WatchlistRepositoryImpl @Inject constructor(
    private val apiService: AnimeApiService,
    private val dao: WatchlistDao
) : WatchlistRepository {

    override suspend fun addToWatchlist(animeId: Int, status: WatchlistStatus): Result<Watchlist> {
        return try {
            val response = apiService.addToWatchlist(WatchlistRequestDto(animeId, status.value))
            if (response.isSuccessful && response.body() != null) {
                val dto = response.body()!!
                val watchlist = Watchlist(
                    id = dto.id,
                    userId = dto.userId,
                    animeId = dto.animeId,
                    estado = WatchlistStatus.fromString(dto.estado),
                    updatedAt = dto.updatedAt,
                    anime = dto.anime?.let {
                        WatchlistAnime(
                            id = it.id,
                            titulo = it.titulo,
                            genero = it.genero,
                            anio = it.anio,
                            descripcion = it.descripcion,
                            createdAt = it.createdAt
                        )
                    }
                )
                dao.insertWatchlist(listOf(WatchlistEntity(
                    id = dto.id,
                    userId = dto.userId,
                    animeId = dto.animeId,
                    estado = dto.estado,
                    updatedAt = dto.updatedAt,
                    animeTitulo = dto.anime?.titulo,
                    animeGenero = dto.anime?.genero,
                    animeAnio = dto.anime?.anio,
                    animeDescripcion = dto.anime?.descripcion,
                    animeCreatedAt = dto.anime?.createdAt
                )))
                Result.success(watchlist)
            } else {
                Result.failure(Exception("Error adding to watchlist: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getMyWatchlist(): Result<List<Watchlist>> {
        return try {
            val response = apiService.getMyWatchlist()
            if (response.isSuccessful && response.body() != null) {
                val dtos = response.body()!!
                val entities = dtos.map { dto ->
                    WatchlistEntity(
                        id = dto.id,
                        userId = dto.userId,
                        animeId = dto.animeId,
                        estado = dto.estado,
                        updatedAt = dto.updatedAt,
                        animeTitulo = dto.anime?.titulo,
                        animeGenero = dto.anime?.genero,
                        animeAnio = dto.anime?.anio,
                        animeDescripcion = dto.anime?.descripcion,
                        animeCreatedAt = dto.anime?.createdAt
                    )
                }
                dao.clearAll()
                dao.insertWatchlist(entities)
                
                val domainList = dtos.map { dto ->
                    Watchlist(
                        id = dto.id,
                        userId = dto.userId,
                        animeId = dto.animeId,
                        estado = WatchlistStatus.fromString(dto.estado),
                        updatedAt = dto.updatedAt,
                        anime = dto.anime?.let {
                            WatchlistAnime(
                                id = it.id,
                                titulo = it.titulo,
                                genero = it.genero,
                                anio = it.anio,
                                descripcion = it.descripcion,
                                createdAt = it.createdAt
                            )
                        }
                    )
                }
                Result.success(domainList)
            } else {
                fetchLocalWatchlist()
            }
        } catch (_: Exception) {
            fetchLocalWatchlist()
        }
    }

    private suspend fun fetchLocalWatchlist(): Result<List<Watchlist>> {
        val entities = dao.getAllWatchlist().first()
        return if (entities.isNotEmpty()) {
            Result.success(entities.map { entity ->
                Watchlist(
                    id = entity.id,
                    userId = entity.userId,
                    animeId = entity.animeId,
                    estado = WatchlistStatus.fromString(entity.estado),
                    updatedAt = entity.updatedAt,
                    anime = if (entity.animeTitulo != null) {
                        WatchlistAnime(
                            id = entity.animeId,
                            titulo = entity.animeTitulo,
                            genero = entity.animeGenero ?: "",
                            anio = entity.animeAnio ?: 0,
                            descripcion = entity.animeDescripcion ?: "",
                            createdAt = entity.animeCreatedAt ?: ""
                        )
                    } else null
                )
            })
        } else {
            Result.failure(Exception("No hay conexión a internet y la base de datos está vacía."))
        }
    }

    override suspend fun removeFromWatchlist(animeId: Int): Result<Unit> {
        return try {
            val response = apiService.removeFromWatchlist(animeId)
            if (response.isSuccessful) {
                dao.deleteByAnimeId(animeId)
                Result.success(Unit)
            } else {
                Result.failure(Exception("Error removing from watchlist: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
