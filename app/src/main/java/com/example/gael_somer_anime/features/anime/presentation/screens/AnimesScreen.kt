package com.example.gael_somer_anime.features.anime.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.gael_somer_anime.features.anime.presentation.components.AnimeFormDialog
import com.example.gael_somer_anime.features.anime.presentation.components.AnimeItem
import com.example.gael_somer_anime.features.anime.presentation.viewmodels.AnimesViewModel
import com.example.gael_somer_anime.features.favorites.domain.entities.Favorite
import com.example.gael_somer_anime.features.favorites.presentation.viewmodels.FavoritesViewModel
import com.example.gael_somer_anime.features.watchlist.domain.entities.WatchlistStatus
import com.example.gael_somer_anime.features.watchlist.presentation.viewmodels.WatchlistViewModel

@Composable
fun AnimesScreen(
    onNavToFavorites: () -> Unit,
    onNavToWatchlist: () -> Unit,
    animesViewModel: AnimesViewModel = hiltViewModel(),
    favoritesViewModel: FavoritesViewModel = hiltViewModel(),
    watchlistViewModel: WatchlistViewModel = hiltViewModel()
) {
    val uiState by animesViewModel.uiState.collectAsStateWithLifecycle()
    val favState by favoritesViewModel.uiState.collectAsStateWithLifecycle()

    // Manejo del ciclo de vida del sensor de movimiento (Shake)
    DisposableEffect(Unit) {
        animesViewModel.startShakeDetection()
        onDispose {
            animesViewModel.stopShakeDetection()
        }
    }

    Scaffold(
        floatingActionButton = {
            Column(horizontalAlignment = Alignment.End) {
                FloatingActionButton(
                    onClick = onNavToWatchlist,
                    containerColor = MaterialTheme.colorScheme.tertiaryContainer
                ) {
                    Icon(Icons.AutoMirrored.Filled.List, contentDescription = "Ver Watchlist")
                }
                Spacer(modifier = Modifier.height(12.dp))
                FloatingActionButton(
                    onClick = onNavToFavorites,
                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                ) {
                    Icon(Icons.Filled.Favorite, contentDescription = "Ver Favoritos")
                }
                Spacer(modifier = Modifier.height(12.dp))
                FloatingActionButton(onClick = { animesViewModel.onOpenDialog() }) {
                    Icon(Icons.Filled.Add, contentDescription = "Añadir Anime")
                }
            }
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentAlignment = Alignment.Center
        ) {
            if (uiState.isLoading) {
                CircularProgressIndicator()
            } else if (uiState.error != null) {
                Text(text = uiState.error!!)
            } else {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(uiState.animes) { anime ->
                        val isFav = favState.favorites.any { it.id == anime.id }
                        
                        AnimeItem(
                            anime = anime,
                            isFavorite = isFav,
                            onEdit = { animesViewModel.onOpenDialog(anime) },
                            onDelete = { animesViewModel.deleteAnime(anime.id) },
                            onFavoriteToggle = {
                                favoritesViewModel.toggleFavorite(
                                    Favorite(anime.id, anime.titulo, anime.genero, anime.anio, anime.descripcion)
                                )
                            },
                            onWatchlistToggle = {
                                watchlistViewModel.addToWatchlist(anime.id, WatchlistStatus.POR_VER)
                            }
                        )
                    }
                }
            }
        }

        // Diálogo para añadir/editar anime
        AnimeFormDialog(
            uiState = uiState,
            onFieldChange = { t, g, a, d -> animesViewModel.onFieldChange(t, g, a, d) },
            onImageSelected = { uri -> animesViewModel.onImageSelected(uri) },
            onSave = { file -> animesViewModel.onSaveAnime(file) },
            onDismiss = { animesViewModel.onCloseDialog() }
        )

        // Diálogo de recomendación aleatoria (Shake)
        if (uiState.showRandomDialog && uiState.randomAnime != null) {
            AlertDialog(
                onDismissRequest = { animesViewModel.onCloseRandomDialog() },
                title = { Text(text = "¡Recomendación Aleatoria!") },
                text = {
                    Column {
                        Text(text = "Título: ${uiState.randomAnime!!.titulo}", style = MaterialTheme.typography.titleMedium)
                        Text(text = "Género: ${uiState.randomAnime!!.genero}")
                        Text(text = "Año: ${uiState.randomAnime!!.anio}")
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = uiState.randomAnime!!.descripcion)
                    }
                },
                confirmButton = {
                    TextButton(onClick = { animesViewModel.onCloseRandomDialog() }) {
                        Text("Cerrar")
                    }
                }
            )
        }
    }
}
