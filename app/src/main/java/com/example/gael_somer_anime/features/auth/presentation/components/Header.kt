package com.example.gael_somer_anime.features.auth.presentation.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.gael_somer_anime.core.navigation.Screens
import com.example.gael_somer_anime.features.auth.presentation.viewmodels.HeaderViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Header(
    title: String,
    onLogoutSuccess: () -> Unit,
    navController: NavController,
    viewModel: HeaderViewModel = hiltViewModel()
) {
    TopAppBar(
        title = { Text(title) },
        actions = {
            // Botón para ir a Favoritos
            IconButton(onClick = { navController.navigate(Screens.Favorites.route) }) {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = "Ver favoritos"
                )
            }
            // Botón de Cerrar Sesión
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