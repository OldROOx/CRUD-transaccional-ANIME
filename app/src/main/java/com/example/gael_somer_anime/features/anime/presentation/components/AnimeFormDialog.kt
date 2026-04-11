package com.example.gael_somer_anime.features.anime.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import android.net.Uri
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.gael_somer_anime.features.anime.presentation.viewmodels.AnimesUiState
import java.io.File
import java.io.FileOutputStream

@Composable
fun AnimeFormDialog(
    uiState: AnimesUiState,
    onFieldChange: (String?, String?, String?, String?) -> Unit,
    onImageSelected: (Uri?) -> Unit,
    onSave: (File?) -> Unit,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        onImageSelected(uri)
    }

    if (uiState.showDialog) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = {
                Text(text = if (uiState.selectedAnime == null) "Nuevo Anime" else "Editar Anime")
            },
            text = {
                Column {
                    OutlinedTextField(
                        value = uiState.titulo,
                        onValueChange = { onFieldChange(it, null, null, null) },
                        label = { Text("Título") },
                        modifier = Modifier.fillMaxWidth(),
                        isError = uiState.tituloError != null,
                        supportingText = { uiState.tituloError?.let { Text(it) } }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = uiState.genero,
                        onValueChange = { onFieldChange(null, it, null, null) },
                        label = { Text("Género") },
                        modifier = Modifier.fillMaxWidth(),
                        isError = uiState.generoError != null,
                        supportingText = { uiState.generoError?.let { Text(it) } }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = uiState.anio,
                        onValueChange = { onFieldChange(null, null, it, null) },
                        label = { Text("Año") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.fillMaxWidth(),
                        isError = uiState.anioError != null,
                        supportingText = { uiState.anioError?.let { Text(it) } }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = uiState.descripcion,
                        onValueChange = { onFieldChange(null, null, null, it) },
                        label = { Text("Descripción") },
                        modifier = Modifier.fillMaxWidth(),
                        maxLines = 4,
                        isError = uiState.descripcionError != null,
                        supportingText = { uiState.descripcionError?.let { Text(it) } }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(
                        onClick = { launcher.launch("image/*") },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(if (uiState.imageUri == null) "Elegir Imagen" else "Cambiar Imagen")
                    }

                    uiState.imageUri?.let { uri ->
                        Spacer(modifier = Modifier.height(8.dp))
                        AsyncImage(
                            model = uri,
                            contentDescription = null,
                            modifier = Modifier.fillMaxWidth().height(100.dp)
                        )
                    }
                }
            },
            confirmButton = {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = onDismiss) {
                        Text("Cancelar")
                    }
                    Button(
                        onClick = {
                            val file = uiState.imageUri?.let { uri ->
                                val tempFile = File(context.cacheDir, "temp_upload_${System.currentTimeMillis()}.jpg")
                                context.contentResolver.openInputStream(uri)?.use { input ->
                                    FileOutputStream(tempFile).use { output ->
                                        input.copyTo(output)
                                    }
                                }
                                tempFile
                            }
                            onSave(file)
                        }
                    ) {
                        Text("Guardar")
                    }
                }
            }
        )
    }
}