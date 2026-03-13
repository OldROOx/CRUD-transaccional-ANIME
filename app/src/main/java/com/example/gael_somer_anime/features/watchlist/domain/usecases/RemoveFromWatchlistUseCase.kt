package com.example.gael_somer_anime.features.watchlist.domain.usecases

import com.example.gael_somer_anime.features.watchlist.domain.repositories.WatchlistRepository
import javax.inject.Inject

class RemoveFromWatchlistUseCase @Inject constructor(
    private val repository: WatchlistRepository
) {
    suspend operator fun invoke(animeId: Int): Result<Unit> {
        return repository.removeFromWatchlist(animeId)
    }
}
