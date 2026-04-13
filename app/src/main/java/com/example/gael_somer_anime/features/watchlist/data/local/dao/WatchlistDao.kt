package com.example.gael_somer_anime.features.watchlist.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.gael_somer_anime.features.watchlist.data.local.entities.WatchlistEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WatchlistDao {
    @Query("SELECT * FROM watchlist")
    fun getAllWatchlist(): Flow<List<WatchlistEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWatchlist(items: List<WatchlistEntity>)

    @Query("DELETE FROM watchlist WHERE animeId = :animeId")
    suspend fun deleteByAnimeId(animeId: Int)

    @Query("DELETE FROM watchlist")
    suspend fun clearAll()
}
