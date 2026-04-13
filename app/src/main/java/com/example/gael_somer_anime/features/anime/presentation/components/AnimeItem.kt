package com.example.gael_somer_anime.features.anime.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.PlaylistAdd
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Tag
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.gael_somer_anime.core.services.ImageCacheHelper
import com.example.gael_somer_anime.features.anime.domain.entities.Anime

@Composable
fun AnimeItem(
    anime: Anime,
    currentUserId: Int,
    isFavorite: Boolean,
    subscribedTags: List<String> = emptyList(),
    onEdit: (Anime) -> Unit,
    onDelete: (Int) -> Unit,
    onFavoriteToggle: () -> Unit,
    onWatchlistToggle: () -> Unit,
    onTagClick: (String) -> Unit,
    onViewDetails: (Int) -> Unit
) {
    val context = LocalContext.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onViewDetails(anime.id) }
    ) {
        Column {
            AsyncImage(
                model = ImageCacheHelper.getImageModel(context, anime.id, anime.imageUrl),
                contentDescription = anime.titulo,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
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

                val tagList = anime.tags.split(",").map { it.trim() }.filter { it.isNotEmpty() }
                if (tagList.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(6.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        tagList.forEach { tag ->
                            val isSubscribed = subscribedTags.contains(tag)
                            SuggestionChip(
                                onClick = { onTagClick(tag) },
                                label = { Text(text = "#$tag", style = MaterialTheme.typography.labelSmall) },
                                icon = {
                                    Icon(
                                        imageVector = Icons.Default.Tag,
                                        contentDescription = null,
                                        modifier = Modifier.size(14.dp)
                                    )
                                },
                                colors = SuggestionChipDefaults.suggestionChipColors(
                                    containerColor = if (isSubscribed) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.secondaryContainer,
                                    labelColor = if (isSubscribed) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSecondaryContainer
                                )
                            )
                        }
                    }
                }

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