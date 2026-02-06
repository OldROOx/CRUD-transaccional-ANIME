package com.example.gael_somer_anime.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.gael_somer_anime.features.anime.presentation.screens.AnimesScreen
import com.example.gael_somer_anime.features.anime.presentation.viewmodels.AnimesViewModelFactory
import com.example.gael_somer_anime.features.auth.presentation.screens.LoginScreen
import com.example.gael_somer_anime.features.auth.presentation.screens.RegisterScreen
import com.example.gael_somer_anime.features.auth.presentation.viewmodels.LoginViewModelFactory
import com.example.gael_somer_anime.features.auth.presentation.viewmodels.RegisterViewModelFactory

@Composable
fun AppNavHost(
    navController: NavHostController,
    loginFactory: LoginViewModelFactory,
    registerFactory: RegisterViewModelFactory,
    animesFactory: AnimesViewModelFactory, // Añadido
    startDestination: String
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Screens.Login.route) {
            LoginScreen(
                factory = loginFactory,
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
                factory = registerFactory,
                onBackToLogin = {
                    navController.popBackStack()
                }
            )
        }
        composable(Screens.Home.route) {
            AnimesScreen(factory = animesFactory)
        }
    }
}