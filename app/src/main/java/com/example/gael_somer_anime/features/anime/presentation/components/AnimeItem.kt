package com.example.gael_somer_anime.features.anime.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.PlaylistAdd
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import com.example.gael_somer_anime.features.anime.domain.entities.Anime

@Composable
fun AnimeItem(
    anime: Anime,
    currentUserId: Int,
    isFavorite: Boolean,
    onEdit: (Anime) -> Unit,
    onDelete: (Int) -> Unit,
    onFavoriteToggle: () -> Unit,
    onWatchlistToggle: () -> Unit
) {
    var showFullImage by remember { mutableStateOf(false) }

    if (showFullImage) {
        Dialog(onDismissRequest = { showFullImage = false }) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                shape = MaterialTheme.shapes.extraLarge,
                color = MaterialTheme.colorScheme.surface
            ) {
                Column {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        IconButton(
                            onClick = { showFullImage = false },
                            modifier = Modifier.align(Alignment.TopEnd)
                        ) {
                            Icon(Icons.Default.Close, contentDescription = "Cerrar")
                        }
                    }
                    AsyncImage(
                        model = anime.imageUrl ?: "https://via.placeholder.com/150",
                        contentDescription = anime.titulo,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        contentScale = ContentScale.Fit
                    )
                }
            }
        }
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .then(
                if (anime.userId == currentUserId) {
                    Modifier.clickable { onEdit(anime) }
                } else Modifier
            )
    ) {
        Column {
            AsyncImage(
                model = anime.imageUrl ?: "https://via.placeholder.com/150",
                contentDescription = anime.titulo,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .clickable { showFullImage = true },
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Título: ${anime.titulo}",
                    style = MaterialTheme.typography.titleMedium
                )
                Text(text = "Género: ${anime.genero}")
                Text(text = "Año: ${anime.anio}")
                Text(text = "Descripción: ${anime.descripcion}")

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    IconButton(onClick = onWatchlistToggle) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.PlaylistAdd,
                            contentDescription = "Añadir a Watchlist",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                    IconButton(onClick = onFavoriteToggle) {
                        Icon(
                            imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = "Favorito",
                            tint = if (isFavorite) Color.Red else Color.Gray
                        )
                    }
                    if (anime.userId == currentUserId) {
                        IconButton(onClick = { onEdit(anime) }) {
                            Icon(Icons.Default.Edit, contentDescription = "Editar")
                        }
                        IconButton(onClick = { onDelete(anime.id) }) {
                            Icon(Icons.Default.Delete, contentDescription = "Borrar")
                        }
                    }
                }
            }
        }
    }
}
