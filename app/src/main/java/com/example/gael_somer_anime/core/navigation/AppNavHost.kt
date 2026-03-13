package com.example.gael_somer_anime.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.gael_somer_anime.features.auth.presentation.screens.LoginScreen
import com.example.gael_somer_anime.features.auth.presentation.screens.RegisterScreen
import com.example.gael_somer_anime.features.anime.presentation.screens.AnimesScreen
import com.example.gael_somer_anime.features.anime.presentation.screens.FavoritesScreen

@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screens.Login.route
    ) {
        composable(Screens.Login.route) {
            LoginScreen(navController)
        }

        composable(Screens.Register.route) {
            RegisterScreen(navController)
        }

        composable(Screens.Animes.route) {
            // Pasamos el navController para que el Header pueda navegar a Favoritos
            AnimesScreen(navController = navController)
        }

        composable(Screens.Favorites.route) {
            // Nueva pantalla de favoritos
            FavoritesScreen()
        }
    }
}