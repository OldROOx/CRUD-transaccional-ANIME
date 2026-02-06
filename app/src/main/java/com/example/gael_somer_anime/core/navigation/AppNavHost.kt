package com.example.gael_somer_anime.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.gael_somer_anime.features.auth.presentation.screens.*
import com.example.gael_somer_anime.features.auth.presentation.viewmodels.LoginViewModelFactory
import com.example.gael_somer_anime.features.auth.presentation.viewmodels.RegisterViewModelFactory
import androidx.compose.material3.Text

@Composable
fun AppNavHost(
    navController: NavHostController,
    loginFactory: LoginViewModelFactory,
    registerFactory: RegisterViewModelFactory,
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
            Text("¡Bienvenido a la Home de Anime!")
        }
    }
}