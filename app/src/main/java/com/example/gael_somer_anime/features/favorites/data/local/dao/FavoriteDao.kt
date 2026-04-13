package com.example.gael_somer_anime.features.favorites.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.gael_somer_anime.features.favorites.data.local.entities.FavoriteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {
    @Query("SELECT * FROM favorites")
    fun getAllFavorites(): Flow<List<FavoriteEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(favorite: FavoriteEntity)

    @Query("DELETE FROM favorites WHERE id = :id")
    suspend fun deleteFavoriteById(id: Int)

    @Query("SELECT EXISTS(SELECT * FROM favorites WHERE id = :id)")
    suspend fun isFavorite(id: Int): Boolean
}
