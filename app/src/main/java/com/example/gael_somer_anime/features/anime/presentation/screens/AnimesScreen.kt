package com.example.gael_somer_anime.features.anime.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
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

@Composable
fun AnimesScreen(
    onNavToFavorites: () -> Unit,
    animesViewModel: AnimesViewModel = hiltViewModel(),
    favoritesViewModel: FavoritesViewModel = hiltViewModel()
) {
    val uiState by animesViewModel.uiState.collectAsStateWithLifecycle()
    val favState by favoritesViewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        floatingActionButton = {
            Column(horizontalAlignment = Alignment.End) {
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
                        val isFav = favoritesViewModel.isFavorite(anime.id)
                        AnimeItem(
                            anime = anime,
                            isFavorite = isFav,
                            onEdit = { animesViewModel.onOpenDialog(anime) },
                            onDelete = { animesViewModel.deleteAnime(anime.id) },
                            onFavoriteToggle = {
                                favoritesViewModel.toggleFavorite(
                                    Favorite(anime.id, anime.titulo, anime.genero, anime.anio, anime.descripcion)
                                )
                            }
                        )
                    }
                }
            }
        }

        AnimeFormDialog(
            uiState = uiState,
            onFieldChange = { t, g, a, d -> animesViewModel.onFieldChange(t, g, a, d) },
            onSave = { animesViewModel.onSaveAnime() },
            onDismiss = { animesViewModel.onCloseDialog() }
        )
    }
}