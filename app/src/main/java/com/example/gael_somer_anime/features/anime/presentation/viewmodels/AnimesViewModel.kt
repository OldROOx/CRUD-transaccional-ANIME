package com.example.gael_somer_anime.features.anime.presentation.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gael_somer_anime.core.network.SessionManager
import com.example.gael_somer_anime.features.anime.data.local.dao.AnimeDao
import com.example.gael_somer_anime.features.anime.data.local.entities.FavoriteEntity
import com.example.gael_somer_anime.features.anime.domain.entities.Anime
import com.example.gael_somer_anime.features.anime.domain.usecases.*
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnimesViewModel @Inject constructor(
    private val getAnimesUseCase: GetAnimesUseCase,
    private val deleteAnimeUseCase: DeleteAnimeUseCase,
    private val animeDao: AnimeDao,
    @ApplicationContext private val context: Context
) : ViewModel() {

    val animes: StateFlow<List<Anime>> = getAnimesUseCase()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun deleteAnime(id: Int) {
        viewModelScope.launch { deleteAnimeUseCase(id) }
    }

    fun addToFavorites(anime: Anime) {
        val currentUser = SessionManager.getSavedUser(context) ?: "unknown"
        viewModelScope.launch {
            animeDao.insertFavorite(
                FavoriteEntity(
                    userId = currentUser,
                    animeId = anime.id,
                    titulo = anime.titulo,
                    genero = anime.genero,
                    anio = anime.anio,
                    descripcion = anime.descripcion
                )
            )
        }
    }
}