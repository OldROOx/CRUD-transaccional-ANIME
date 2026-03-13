package com.example.gael_somer_anime.features.anime.presentation.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gael_somer_anime.core.network.SessionManager
import com.example.gael_somer_anime.features.anime.data.local.dao.AnimeDao
import com.example.gael_somer_anime.features.anime.data.local.entities.FavoriteEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val animeDao: AnimeDao,
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val currentUser = SessionManager.getSavedUser(context) ?: ""

    val favoriteAnimes: StateFlow<List<FavoriteEntity>> = animeDao.getFavoritesByUser(currentUser)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun removeFromFavorites(animeId: Int) {
        viewModelScope.launch {
            animeDao.deleteFavorite(currentUser, animeId)
        }
    }
}