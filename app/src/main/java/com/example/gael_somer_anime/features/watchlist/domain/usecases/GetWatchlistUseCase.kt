package com.example.gael_somer_anime.features.watchlist.domain.usecases

import com.example.gael_somer_anime.features.watchlist.domain.entities.Watchlist
import com.example.gael_somer_anime.features.watchlist.domain.repositories.WatchlistRepository
import javax.inject.Inject

class GetWatchlistUseCase @Inject constructor(
    private val repository: WatchlistRepository
) {
    suspend operator fun invoke(): Result<List<Watchlist>> {
        return repository.getMyWatchlist()
    }
}
