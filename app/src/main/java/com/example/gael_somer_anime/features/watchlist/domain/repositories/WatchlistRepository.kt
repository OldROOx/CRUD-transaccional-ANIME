package com.example.gael_somer_anime.features.watchlist.domain.repositories

import com.example.gael_somer_anime.features.watchlist.domain.entities.Watchlist
import com.example.gael_somer_anime.features.watchlist.domain.entities.WatchlistStatus

interface WatchlistRepository {
    suspend fun addToWatchlist(animeId: Int, status: WatchlistStatus): Result<Watchlist>
    suspend fun getMyWatchlist(): Result<List<Watchlist>>
    suspend fun removeFromWatchlist(animeId: Int): Result<Unit>
}
