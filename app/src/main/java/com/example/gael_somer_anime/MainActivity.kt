=package com.example.gael_somer_anime

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.gael_somer_anime.core.di.AppContainer
import com.example.gael_somer_anime.features.auth.presentation.screens.LoginScreen
import com.example.gael_somer_anime.features.auth.presentation.screens.RegisterScreen
import com.example.gael_somer_anime.features.auth.presentation.viewmodels.AuthViewModel
import com.example.gael_somer_anime.ui.theme.Gael_somer_animeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicializamos el contenedor de dependencias (estilo kt-demo)
        val appContainer = AppContainer()

        // Creamos el ViewModel pasando el repositorio del contenedor
        val authViewModel = AuthViewModel(appContainer.authRepository)

        setContent {
            Gael_somer_animeTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    // Estado para controlar la navegación simple entre pantallas
                    var currentScreen by remember { mutableStateOf("login") }

                    when (currentScreen) {
                        "login" -> LoginScreen(
                            viewModel = authViewModel,
                            onLoginSuccess = { token ->
                                // Aquí el token ya se guardó en SessionManager automáticamente
                                println("Login exitoso. Token: $token")
                                // El siguiente paso de tu equipo sería navegar a la lista de animes
                            },
                            onNavToRegister = { currentScreen = "register" }
                        )
                        "register" -> RegisterScreen(
                            viewModel = authViewModel,
                            onBackToLogin = { currentScreen = "login" }
                        )
                    }
                }
            }
        }
    }
}