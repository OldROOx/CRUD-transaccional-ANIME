package com.example.gael_somer_anime

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.gael_somer_anime.core.navigation.*
import com.example.gael_somer_anime.core.network.SessionManager
import com.example.gael_somer_anime.core.services.ImageCacheScheduler
import com.example.gael_somer_anime.core.services.ImageCacheService
import com.google.firebase.messaging.FirebaseMessaging
import com.example.gael_somer_anime.features.auth.presentation.components.Header
import com.example.gael_somer_anime.ui.theme.Gael_somer_animeTheme
import dagger.hilt.android.AndroidEntryPoint

import android.content.Intent

@AndroidEntryPoint
class MainActivity : FragmentActivity() {

    private var initialAnimeId by mutableStateOf<String?>(null)

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            Log.d("FCM", "Permiso de notificaciones concedido")
        } else {
            Log.d("FCM", "Permiso de notificaciones denegado")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestNotificationPermission()
        fetchAndStoreFcmToken()

        initialAnimeId = intent.getStringExtra("anime_id")

        // Si hay sesión activa, iniciar caché inmediato + programar periódico
        if (SessionManager.fetchToken(this) != null) {
            ImageCacheService.start(this)
            ImageCacheScheduler.schedule(this)
        }

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
                            Header(title = "Anime App") {
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
                            startDestination = startDest,
                            initialAnimeId = initialAnimeId,
                            onAnimeIdConsumed = { initialAnimeId = null }
                        )
                    }
                }
            }
        }
    }

    private fun requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) !=
                PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }

    private fun fetchAndStoreFcmToken() {
        Log.d("FCM", "Iniciando recuperación de token...")
        try {
            FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.e("FCM", "ERROR: No se pudo obtener el token", task.exception)
                    return@addOnCompleteListener
                }
                val token = task.result
                Log.i("FCM", "TOKEN RECUPERADO EXITOSAMENTE: $token")
                SessionManager.saveFcmToken(this, token)
            }
        } catch (e: Exception) {
            Log.e("FCM", "ERROR CRÍTICO: ${e.message}")
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        initialAnimeId = intent.getStringExtra("anime_id")
    }
}