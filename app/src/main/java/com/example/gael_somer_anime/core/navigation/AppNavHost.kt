package com.example.gael_somer_anime.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.gael_somer_anime.features.anime.presentation.screens.AnimesScreen
import com.example.gael_somer_anime.features.auth.presentation.screens.LoginScreen
import com.example.gael_somer_anime.features.auth.presentation.screens.RegisterScreen
import com.example.gael_somer_anime.features.favorites.presentation.screens.FavoritesScreen
import com.example.gael_somer_anime.features.tags.presentation.screens.TagSubscriptionsScreen
import com.example.gael_somer_anime.features.watchlist.presentation.screens.WatchlistScreen

@Composable
fun AppNavHost(
    navController: NavHostController,
    startDestination: String
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Screens.Login.route) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Screens.Home.route) {
                        popUpTo(Screens.Login.route) { inclusive = true }
                    }
                },
                onNavToRegister = {
                    navController.navigate(Screens.Register.route)
                }
            )
        }
        composable(Screens.Register.route) {
            RegisterScreen(
                onBackToLogin = { navController.popBackStack() }
            )
        }
        composable(Screens.Home.route) {
            AnimesScreen(
                onNavToFavorites = {
                    navController.navigate(Screens.Favorites.route)
                },
                onNavToWatchlist = {
                    navController.navigate(Screens.Watchlist.route)
                },
                onNavToTags = {
                    navController.navigate(Screens.TagSubscriptions.route)
                }
            )
        }
        composable(Screens.TagSubscriptions.route) {
            TagSubscriptionsScreen(
                onBack = { navController.popBackStack() }
            )
        }
        composable(Screens.Favorites.route) {
            FavoritesScreen(
                onBack = { navController.popBackStack() }
            )
        }
        composable(Screens.Watchlist.route) {
            WatchlistScreen(
                onBack = { navController.popBackStack() }
            )
        }
    }
}
