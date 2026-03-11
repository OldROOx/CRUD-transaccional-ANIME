package com.example.gael_somer_anime.features.anime.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LifecycleResumeEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.gael_somer_anime.features.anime.presentation.components.AnimeFormDialog
import com.example.gael_somer_anime.features.anime.presentation.components.AnimeItem
import com.example.gael_somer_anime.features.anime.presentation.viewmodels.AnimesViewModel

@Composable
fun AnimesScreen(viewModel: AnimesViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LifecycleResumeEffect(viewModel) {
        viewModel.startShakeDetection()
        onPauseOrDispose {
            viewModel.stopShakeDetection()
        }
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { viewModel.onOpenDialog() }) {
                Icon(Icons.Filled.Add, contentDescription = "Añadir Anime")
            }
        }
    ) { padding ->
        Box(
            modifier = Modifier.fillMaxSize().padding(padding),
            contentAlignment = Alignment.Center
        ) {
            if (uiState.isLoading) {
                CircularProgressIndicator()
            } else if (uiState.error != null) {
                Text(text = uiState.error!!)
            } else {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(uiState.animes) { anime ->
                        AnimeItem(
                            anime = anime,
                            onEdit = { viewModel.onOpenDialog(anime) },
                            onDelete = { viewModel.deleteAnime(anime.id) }
                        )
                    }
                }
            }
        }

        AnimeFormDialog(
            uiState = uiState,
            onFieldChange = { t, g, a, d -> viewModel.onFieldChange(t, g, a, d) },
            onSave = { viewModel.onSaveAnime() },
            onDismiss = { viewModel.onCloseDialog() }
        )

        if (uiState.showRandomDialog && uiState.randomAnime != null) {
            Dialog(onDismissRequest = { viewModel.onCloseRandomDialog() }) {
                Card(
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            "Recomendación Aleatoria",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            uiState.randomAnime!!.titulo,
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            uiState.randomAnime!!.genero,
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            uiState.randomAnime!!.descripcion,
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Spacer(modifier = Modifier.height(24.dp))
                        Button(onClick = { viewModel.onCloseRandomDialog() }) {
                            Text("Ok")
                        }
                    }
                }
            }
        }
    }
}
