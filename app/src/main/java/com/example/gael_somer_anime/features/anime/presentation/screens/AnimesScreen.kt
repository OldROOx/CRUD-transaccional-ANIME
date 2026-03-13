package com.example.gael_somer_anime.features.anime.presentation.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.gael_somer_anime.core.navigation.Screens
import com.example.gael_somer_anime.features.anime.presentation.components.AnimeItem
import com.example.gael_somer_anime.features.anime.presentation.viewmodels.AnimesViewModel
import com.example.gael_somer_anime.features.auth.presentation.components.Header

@Composable
fun AnimesScreen(
    navController: NavController,
    viewModel: AnimesViewModel = hiltViewModel()
) {
    val animes by viewModel.animes.collectAsState()

    Scaffold(
        topBar = {
            Header(
                title = "Lista de Animes",
                navController = navController,
                onLogoutSuccess = {
                    navController.navigate(Screens.Login.route) {
                        popUpTo(Screens.Animes.route) { inclusive = true }
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(modifier = Modifier.padding(padding)) {
            items(animes) { anime ->
                AnimeItem(
                    anime = anime,
                    onDelete = { viewModel.deleteAnime(anime.id) },
                    onUpdate = { /* Lógica de update */ },
                    onFavorite = { viewModel.addToFavorites(anime) }
                )
            }
        }
    }
}