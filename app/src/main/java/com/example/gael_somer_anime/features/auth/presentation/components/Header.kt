package com.example.gael_somer_anime.features.auth.presentation.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gael_somer_anime.features.auth.presentation.viewmodels.HeaderViewModel
import com.example.gael_somer_anime.features.auth.presentation.viewmodels.HeaderViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Header(
    title: String,
    factory: HeaderViewModelFactory,
    onLogout: () -> Unit
) {
    val viewModel: HeaderViewModel = viewModel(factory = factory)
    TopAppBar(
        title = { Text(title) },
        actions = {
            IconButton(onClick = { viewModel.logout(onLogout) }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                    contentDescription = "Cerrar sesión"
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    )
}