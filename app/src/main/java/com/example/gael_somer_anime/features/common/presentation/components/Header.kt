package com.example.gael_somer_anime.features.common.presentation.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import com.example.gael_somer_anime.features.common.presentation.viewmodels.HeaderViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Header(title: String, viewModel: HeaderViewModel, onLogout: () -> Unit) {
    TopAppBar(
        title = { Text(title) },
        actions = {
            IconButton(onClick = { viewModel.logout(onLogout) }) {
                Icon(Icons.AutoMirrored.Filled.ExitToApp, contentDescription = "Logout")
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    )
}