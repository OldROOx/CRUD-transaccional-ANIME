package com.example.gael_somer_anime.features.anime.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil.compose.AsyncImage
import com.example.gael_somer_anime.features.anime.domain.entities.Anime

@Composable
fun AnimeDetailsDialog(
    anime: Anime,
    imageModel: Any,
    showFullImage: Boolean,
    onShowFullImageChange: (Boolean) -> Unit,
    onLike: (Int) -> Unit = {},
    onDismiss: () -> Unit
) {
    if (showFullImage) {
        Dialog(
            onDismissRequest = { onShowFullImageChange(false) },
            properties = DialogProperties(usePlatformDefaultWidth = false)
        ) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth(0.95f)
                    .wrapContentHeight(),
                shape = MaterialTheme.shapes.extraLarge,
                color = MaterialTheme.colorScheme.surface
            ) {
                Column {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        IconButton(
                            onClick = { onShowFullImageChange(false) },
                            modifier = Modifier.align(Alignment.TopEnd)
                        ) {
                            Icon(Icons.Default.Close, contentDescription = "Cerrar")
                        }
                    }
                    AsyncImage(
                        model = imageModel,
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

    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            shape = MaterialTheme.shapes.large,
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant,
            )
        ) {
            Column {
                AsyncImage(
                    model = imageModel,
                    contentDescription = anime.titulo,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clickable { onShowFullImageChange(true) },
                    contentScale = ContentScale.Crop
                )

                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = anime.titulo,
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(4.dp))

                    Text(text = "Género: ${anime.genero}", style = MaterialTheme.typography.bodyMedium)
                    Text(text = "Año: ${anime.anio}", style = MaterialTheme.typography.bodyMedium)

                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = anime.descripcion,
                        style = MaterialTheme.typography.bodyMedium
                    )

                    val tagList = anime.tags.split(",").map { it.trim() }.filter { it.isNotEmpty() }
                    if (tagList.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(12.dp))
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .horizontalScroll(rememberScrollState()),
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            tagList.forEach { tag ->
                                SuggestionChip(
                                    onClick = { },
                                    label = { Text(text = "#$tag") },
                                    colors = SuggestionChipDefaults.suggestionChipColors(
                                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                                        labelColor = MaterialTheme.colorScheme.onPrimaryContainer
                                    )
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            IconButton(onClick = { onLike(anime.id) }) {
                                Icon(
                                    imageVector = Icons.Default.Favorite,
                                    contentDescription = "Me gusta",
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }
                            Text(
                                text = "${anime.likes}",
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        TextButton(onClick = onDismiss) {
                            Text("Cerrar")
                        }
                    }
                }
            }
        }
    }
}
