package com.example.gael_somer_anime.features.anime.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BookmarkAdd
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.gael_somer_anime.features.anime.domain.entities.Anime
import com.example.gael_somer_anime.features.watchlist.domain.entities.WatchlistStatus


@Composable
fun AnimeItem(
    anime: Anime,
    onEdit: (Anime) -> Unit,
    onDelete: (Int) -> Unit,
    onAddToWatchlist: (Int, WatchlistStatus) -> Unit
) {
    var showMenu by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onEdit(anime) }
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1.0f)) {
                Text(text = "Título: ${anime.titulo}", style = MaterialTheme.typography.titleMedium)
                Text(text = "Género: ${anime.genero}", style = MaterialTheme.typography.bodyMedium)
                Text(text = "Año: ${anime.anio}", style = MaterialTheme.typography.bodySmall)
            }
            Spacer(modifier = Modifier.width(8.dp))
            Row {
                Box {
                    IconButton(onClick = { showMenu = true }) {
                        Icon(Icons.Default.BookmarkAdd, contentDescription = "Watchlist")
                    }
                    DropdownMenu(
                        expanded = showMenu,
                        onDismissRequest = { showMenu = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("Viendo") },
                            onClick = {
                                onAddToWatchlist(anime.id, WatchlistStatus.VIENDO)
                                showMenu = false
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Completado") },
                            onClick = {
                                onAddToWatchlist(anime.id, WatchlistStatus.COMPLETADO)
                                showMenu = false
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Por ver") },
                            onClick = {
                                onAddToWatchlist(anime.id, WatchlistStatus.POR_VER)
                                showMenu = false
                            }
                        )
                    }
                }
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
