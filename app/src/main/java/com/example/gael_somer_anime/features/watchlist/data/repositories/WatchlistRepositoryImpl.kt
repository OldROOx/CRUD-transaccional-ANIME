package com.example.gael_somer_anime.features.watchlist.data.repositories

import com.example.gael_somer_anime.core.network.AnimeApiService
import com.example.gael_somer_anime.features.watchlist.data.remote.models.WatchlistRequestDto
import com.example.gael_somer_anime.features.watchlist.domain.entities.Watchlist
import com.example.gael_somer_anime.features.watchlist.domain.entities.WatchlistStatus
import com.example.gael_somer_anime.features.watchlist.domain.repositories.WatchlistRepository
import javax.inject.Inject

class WatchlistRepositoryImpl @Inject constructor(
    private val apiService: AnimeApiService
) : WatchlistRepository {

    override suspend fun addToWatchlist(animeId: Int, status: WatchlistStatus): Result<Watchlist> {
        return try {
            val response = apiService.addToWatchlist(WatchlistRequestDto(animeId, status.value))
            if (response.isSuccessful && response.body() != null) {
                val dto = response.body()!!
                Result.success(
                    Watchlist(
                        id = dto.id,
                        userId = dto.userId,
                        animeId = dto.animeId,
                        estado = WatchlistStatus.fromString(dto.estado),
                        updatedAt = dto.updatedAt
                    )
                )
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
                val list = response.body()!!.map { dto ->
                    Watchlist(
                        id = dto.id,
                        userId = dto.userId,
                        animeId = dto.animeId,
                        estado = WatchlistStatus.fromString(dto.estado),
                        updatedAt = dto.updatedAt
                    )
                }
                Result.success(list)
            } else {
                Result.failure(Exception("Error fetching watchlist: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun removeFromWatchlist(animeId: Int): Result<Unit> {
        return try {
            val response = apiService.removeFromWatchlist(animeId)
            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                Result.failure(Exception("Error removing from watchlist: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
