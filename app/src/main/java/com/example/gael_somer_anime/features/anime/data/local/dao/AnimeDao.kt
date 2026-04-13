package com.example.gael_somer_anime.features.anime.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.gael_somer_anime.features.anime.data.local.entities.AnimeEntity
import kotlinx.coroutines.flow.Flow

import androidx.room.Transaction

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

    @Transaction
    suspend fun refreshAnimes(animes: List<AnimeEntity>) {
        clearAll()
        insertAnimes(animes)
    }
}
