package com.example.gael_somer_anime.features.watchlist.domain.usecases

import com.example.gael_somer_anime.features.watchlist.domain.entities.Watchlist
import com.example.gael_somer_anime.features.watchlist.domain.entities.WatchlistStatus
import com.example.gael_somer_anime.features.watchlist.domain.repositories.WatchlistRepository
import javax.inject.Inject

class AddToWatchlistUseCase @Inject constructor(
    private val repository: WatchlistRepository
) {
    suspend operator fun invoke(animeId: Int, status: WatchlistStatus): Result<Watchlist> {
        return repository.addToWatchlist(animeId, status)
    }
}
