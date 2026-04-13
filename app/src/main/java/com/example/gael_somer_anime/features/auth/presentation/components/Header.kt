package com.example.gael_somer_anime.features.auth.presentation.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.IconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.gael_somer_anime.features.auth.presentation.viewmodels.HeaderViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Header(
    title: String,
    viewModel: HeaderViewModel = hiltViewModel(),
    onLogoutSuccess: () -> Unit
) {
    TopAppBar(
        title = { Text(title) },
        actions = {
            IconButton(onClick = { viewModel.logout(onLogoutSuccess) }) {
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
