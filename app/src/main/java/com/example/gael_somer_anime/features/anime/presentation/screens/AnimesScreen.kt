package com.example.gael_somer_anime.features.anime.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Scaffold
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.Surface
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.gael_somer_anime.features.anime.presentation.components.AnimeDetailsDialog
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
    onNavToTags: () -> Unit,
    initialAnimeId: String? = null,
    onAnimeIdConsumed: () -> Unit = {},
    animesViewModel: AnimesViewModel = hiltViewModel(),
    favoritesViewModel: FavoritesViewModel = hiltViewModel(),
    watchlistViewModel: WatchlistViewModel = hiltViewModel()
) {
    val uiState by animesViewModel.uiState.collectAsStateWithLifecycle()
    val favState by favoritesViewModel.uiState.collectAsStateWithLifecycle()

    DisposableEffect(Unit) {
        animesViewModel.startShakeDetection()
        onDispose {
            animesViewModel.stopShakeDetection()
        }
    }

    LaunchedEffect(initialAnimeId, uiState.animes) {
        if (initialAnimeId != null && uiState.animes.isNotEmpty()) {
            initialAnimeId.toIntOrNull()?.let { id ->
                animesViewModel.onOpenAnimeDetails(id)
            }
            onAnimeIdConsumed()
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
                FloatingActionButton(
                    onClick = onNavToTags,
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                ) {
                    Icon(Icons.Filled.NotificationsActive, contentDescription = "Mis Suscripciones")
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
                    item {
                        Surface(
                            onClick = { animesViewModel.toggleMyAnimesExpansion() },
                            modifier = Modifier.fillMaxWidth(),
                            color = MaterialTheme.colorScheme.secondaryContainer,
                            contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                        ) {
                            Row(
                                modifier = Modifier.padding(16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Mis Animes (${uiState.myAnimes.size})",
                                    style = MaterialTheme.typography.titleMedium,
                                    modifier = Modifier.weight(1f)
                                )
                                Icon(
                                    imageVector = if (uiState.isMyAnimesExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                                    contentDescription = if (uiState.isMyAnimesExpanded) "Colapsar" else "Expandir"
                                )
                            }
                        }
                    }

                    if (uiState.isMyAnimesExpanded) {
                        items(uiState.myAnimes) { anime ->
                            val isFav = favState.favorites.any { it.id == anime.id }
                            AnimeItem(
                                anime = anime,
                                currentUserId = uiState.currentUserId,
                                isFavorite = isFav,
                                subscribedTags = uiState.subscribedTags,
                                imageModel = animesViewModel.getImageModel(anime.id, anime.imageUrl),
                                onEdit = { animesViewModel.onOpenDialog(anime) },
                                onDelete = { animesViewModel.deleteAnime(anime.id) },
                                onFavoriteToggle = {
                                    favoritesViewModel.toggleFavorite(
                                        Favorite(anime.id, anime.titulo, anime.genero, anime.anio, anime.descripcion)
                                    )
                                },
                                onWatchlistToggle = {
                                    watchlistViewModel.addToWatchlist(anime.id, WatchlistStatus.POR_VER)
                                },
                                onTagClick = { tag -> animesViewModel.onTagClick(tag) },
                                onViewDetails = { id -> animesViewModel.onOpenAnimeDetails(id) }
                            )
                        }
                    }

                    item {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Todos los Animes",
                            style = MaterialTheme.typography.titleSmall,
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                        )
                    }

                    items(uiState.otherAnimes) { anime ->
                        val isFav = favState.favorites.any { it.id == anime.id }
                        AnimeItem(
                            anime = anime,
                            currentUserId = uiState.currentUserId,
                            isFavorite = isFav,
                            subscribedTags = uiState.subscribedTags,
                            imageModel = animesViewModel.getImageModel(anime.id, anime.imageUrl),
                            onEdit = { animesViewModel.onOpenDialog(anime) },
                            onDelete = { animesViewModel.deleteAnime(anime.id) },
                            onFavoriteToggle = {
                                favoritesViewModel.toggleFavorite(
                                    Favorite(anime.id, anime.titulo, anime.genero, anime.anio, anime.descripcion)
                                )
                            },
                            onWatchlistToggle = {
                                watchlistViewModel.addToWatchlist(anime.id, WatchlistStatus.POR_VER)
                            },
                            onTagClick = { tag -> animesViewModel.onTagClick(tag) },
                            onViewDetails = { id -> animesViewModel.onOpenAnimeDetails(id) }
                        )
                    }
                }
            }
        }

        AnimeFormDialog(
            uiState = uiState,
            onFieldChange = { t, g, a, d, tags -> animesViewModel.onFieldChange(t, g, a, d, tags) },
            onImageSelected = { uri -> animesViewModel.onImageSelected(uri) },
            onSave = { animesViewModel.onSaveAnime() },
            onDismiss = { animesViewModel.onCloseDialog() }
        )

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

        if (uiState.selectedAnimeDetails != null) {
            AnimeDetailsDialog(
                anime = uiState.selectedAnimeDetails!!,
                imageModel = animesViewModel.getImageModel(uiState.selectedAnimeDetails!!.id, uiState.selectedAnimeDetails!!.imageUrl),
                showFullImage = uiState.showFullImage,
                onShowFullImageChange = { show -> animesViewModel.setShowFullImage(show) },
                onLike = { id -> animesViewModel.onLikeAnime(id) },
                onDismiss = { animesViewModel.onCloseAnimeDetails() }
            )
        }
    }
}
