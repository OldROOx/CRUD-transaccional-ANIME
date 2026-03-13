package com.example.gael_somer_anime.features.favorites.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.gael_somer_anime.features.favorites.domain.entities.Favorite

@Composable
fun FavoriteItem(
    favorite: Favorite,
    onRemove: (Int) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = "Título: ${favorite.titulo}", style = MaterialTheme.typography.titleMedium)
                Text(text = "Género: ${favorite.genero}")
                Text(text = "Año: ${favorite.anio}")
                Text(text = "Descripción: ${favorite.descripcion}")
            }
            IconButton(onClick = { onRemove(favorite.id) }) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Quitar de favoritos",
                    tint = Color.Red
                )
            }
        }
    }
}