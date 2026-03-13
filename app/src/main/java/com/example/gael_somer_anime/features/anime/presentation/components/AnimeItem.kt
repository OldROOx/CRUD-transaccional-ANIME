package com.example.gael_somer_anime.features.anime.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.gael_somer_anime.features.anime.domain.entities.Anime

@Composable
fun AnimeItem(
    anime: Anime,
    onDelete: () -> Unit,
    onUpdate: () -> Unit,
    onFavorite: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = anime.titulo, style = MaterialTheme.typography.titleLarge)
            Text(text = "Género: ${anime.genero}")
            Text(text = "Año: ${anime.anio}")
            Text(text = anime.descripcion, style = MaterialTheme.typography.bodyMedium)

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(onClick = onFavorite) {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = "Favorito",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
                IconButton(onClick = onUpdate) {
                    Icon(Icons.Default.Edit, contentDescription = "Editar")
                }
                IconButton(onClick = onDelete) {
                    Icon(Icons.Default.Delete, contentDescription = "Eliminar")
                }
            }
        }
    }
}