package com.example.gael_somer_anime

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.gael_somer_anime.core.di.AppContainer
import com.example.gael_somer_anime.core.navigation.*
import com.example.gael_somer_anime.core.network.SessionManager
import com.example.gael_somer_anime.features.auth.presentation.components.Header
import com.example.gael_somer_anime.features.auth.presentation.viewmodels.HeaderViewModel
import com.example.gael_somer_anime.features.auth.presentation.viewmodels.LoginViewModelFactory
import com.example.gael_somer_anime.features.auth.presentation.viewmodels.RegisterViewModelFactory
import com.example.gael_somer_anime.ui.theme.Gael_somer_animeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val container = AppContainer(this)
        val loginVMFactory = LoginViewModelFactory(container.loginUseCase)
        val registerVMFactory = RegisterViewModelFactory(container.registerUseCase)
        val headerVM = HeaderViewModel(container.logoutUseCase)

        setContent {
            Gael_somer_animeTheme {
                val navController = rememberNavController()
                val navBackStack by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStack?.destination?.route

                val startDest = remember {
                    if (SessionManager.fetchToken(this@MainActivity) != null) Screens.Home.route
                    else Screens.Login.route
                }

                Scaffold(
                    topBar = {
                        if (currentRoute == Screens.Home.route) {
                            Header(title = "Anime App", viewModel = headerVM) {
                                navController.navigate(Screens.Login.route) {
                                    popUpTo(0)
                                }
                            }
                        }
                    }
                ) { padding ->
                    Box(modifier = Modifier.padding(padding)) {
                        AppNavHost(
                            navController = navController,
                            loginFactory = loginVMFactory,
                            registerFactory = registerVMFactory,
                            startDestination = startDest
                        )
                    }
                }
            }
        }
    }
}