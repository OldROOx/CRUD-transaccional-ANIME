package com.example.gael_somer_anime.features.anime.data.local.dao

import androidx.room.*
import com.example.gael_somer_anime.features.anime.data.local.entities.AnimeEntity
import com.example.gael_somer_anime.features.anime.data.local.entities.FavoriteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AnimeDao {
    @Query("SELECT * FROM animes")
    fun getAllAnimes(): Flow<List<AnimeEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAnimes(animes: List<AnimeEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAnime(anime: AnimeEntity)

    @Query("DELETE FROM animes WHERE id = :id")
    suspend fun deleteAnimeById(id: Int)

    @Query("DELETE FROM animes")
    suspend fun clearAll()

    // --- NUEVOS MÉTODOS PARA FAVORITOS ---
    @Query("SELECT * FROM favorites WHERE userId = :userId")
    fun getFavoritesByUser(userId: String): Flow<List<FavoriteEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(favorite: FavoriteEntity)

    @Query("DELETE FROM favorites WHERE userId = :userId AND animeId = :animeId")
    suspend fun deleteFavorite(userId: String, animeId: Int)
}