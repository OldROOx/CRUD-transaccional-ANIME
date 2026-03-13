package com.example.gael_somer_anime.features.anime.presentation.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.gael_somer_anime.features.anime.domain.entities.Anime
import com.example.gael_somer_anime.features.anime.presentation.components.AnimeItem
import com.example.gael_somer_anime.features.anime.presentation.viewmodels.FavoritesViewModel

@Composable
fun FavoritesScreen(
    viewModel: FavoritesViewModel = hiltViewModel()
) {
    val favorites by viewModel.favoriteAnimes.collectAsState()

    Scaffold { padding ->
        LazyColumn(modifier = Modifier.padding(padding)) {
            items(favorites) { fav ->
                AnimeItem(
                    anime = Anime(fav.animeId, fav.titulo, fav.genero, fav.anio, fav.descripcion),
                    onDelete = { viewModel.removeFromFavorites(fav.animeId) },
                    onUpdate = { },
                    onFavorite = {
                        // Opcional: podrías usar esto para quitar de favoritos también
                        viewModel.removeFromFavorites(fav.animeId)
                    }
                )
            }
        }
    }
}