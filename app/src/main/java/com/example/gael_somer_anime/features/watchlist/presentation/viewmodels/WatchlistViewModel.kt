package com.example.gael_somer_anime.features.watchlist.presentation.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gael_somer_anime.features.anime.domain.entities.Anime
import com.example.gael_somer_anime.features.anime.domain.usecases.GetAnimesUseCase
import com.example.gael_somer_anime.features.watchlist.domain.entities.Watchlist
import com.example.gael_somer_anime.features.watchlist.domain.entities.WatchlistStatus
import com.example.gael_somer_anime.features.watchlist.domain.usecases.AddToWatchlistUseCase
import com.example.gael_somer_anime.features.watchlist.domain.usecases.GetWatchlistUseCase
import com.example.gael_somer_anime.features.watchlist.domain.usecases.RemoveFromWatchlistUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WatchlistViewModel @Inject constructor(
    private val getWatchlistUseCase: GetWatchlistUseCase,
    private val addToWatchlistUseCase: AddToWatchlistUseCase,
    private val removeFromWatchlistUseCase: RemoveFromWatchlistUseCase,
    private val getAnimesUseCase: GetAnimesUseCase
) : ViewModel() {

    private val _state = mutableStateOf(WatchlistState())
    val state: State<WatchlistState> = _state

    init {
        loadData()
    }

    fun loadData() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)
            
            // Observar animes (Lógica: Red -> Room ya implementada en el repo)
            launch {
                getAnimesUseCase().collect { animeList ->
                    _state.value = _state.value.copy(animes = animeList)
                }
            }

            // Cargar Watchlist
            getWatchlistUseCase().onSuccess { watchlist ->
                _state.value = _state.value.copy(
                    items = watchlist,
                    isLoading = false
                )
            }.onFailure { e ->
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = e.message ?: "Error al cargar watchlist"
                )
            }
        }
    }

    fun addToWatchlist(animeId: Int, status: WatchlistStatus) {
        viewModelScope.launch {
            addToWatchlistUseCase(animeId, status).onSuccess {
                loadData()
            }.onFailure { e ->
                _state.value = _state.value.copy(error = e.message)
            }
        }
    }

    fun removeFromWatchlist(animeId: Int) {
        viewModelScope.launch {
            removeFromWatchlistUseCase(animeId).onSuccess {
                loadData()
            }.onFailure { e ->
                _state.value = _state.value.copy(error = e.message)
            }
        }
    }
}

data class WatchlistState(
    val items: List<Watchlist> = emptyList(),
    val animes: List<Anime> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
