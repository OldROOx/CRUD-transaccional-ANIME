package com.example.gael_somer_anime

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.gael_somer_anime.core.di.AppContainer
import com.example.gael_somer_anime.core.network.SessionManager
import com.example.gael_somer_anime.features.auth.presentation.screens.LoginScreen
import com.example.gael_somer_anime.features.auth.presentation.screens.RegisterScreen
import com.example.gael_somer_anime.features.auth.presentation.viewmodels.AuthViewModel
import com.example.gael_somer_anime.ui.theme.Gael_somer_animeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicializamos el contenedor con el contexto para la persistencia
        val appContainer = AppContainer(this)
        val authViewModel = AuthViewModel(appContainer.authRepository)

        setContent {
            Gael_somer_animeTheme {
                Surface(modifier = Modifier.fillMaxSize()) {

                    // Revisar si ya hay un token guardado al abrir la app
                    val savedToken = remember { SessionManager.fetchToken(this@MainActivity) }
                    var currentScreen by remember {
                        mutableStateOf(if (savedToken != null) "home" else "login")
                    }

                    when (currentScreen) {
                        "login" -> LoginScreen(
                            viewModel = authViewModel,
                            onLoginSuccess = {
                                // Esta función coincide ahora con () -> Unit
                                currentScreen = "home"
                            },
                            onNavToRegister = { currentScreen = "register" }
                        )
                        "register" -> RegisterScreen(
                            viewModel = authViewModel,
                            onBackToLogin = { currentScreen = "login" }
                        )
                        "home" -> {
                            Text("¡Bienvenido! El token ha persistido correctamente.")
                        }
                    }
                }
            }
        }
    }
}