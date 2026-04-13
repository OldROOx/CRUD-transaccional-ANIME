package com.example.gael_somer_anime

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.gael_somer_anime.core.navigation.AppNavHost
import com.example.gael_somer_anime.core.navigation.Screens
import com.example.gael_somer_anime.core.network.SessionManager
import com.example.gael_somer_anime.core.services.ImageCacheScheduler
import com.example.gael_somer_anime.features.auth.presentation.components.Header
import com.example.gael_somer_anime.ui.theme.Gael_somer_animeTheme
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : FragmentActivity() {

    @Inject
    lateinit var imageCacheScheduler: ImageCacheScheduler

    private val viewModel: MainViewModel by viewModels()

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

        viewModel.setInitialAnimeId(intent.getStringExtra("anime_id"))

        if (viewModel.isUserLoggedIn()) {
            imageCacheScheduler.runNow()
            imageCacheScheduler.schedule()
        }

        setContent {
            Gael_somer_animeTheme {
                val navController = rememberNavController()
                val navBackStack by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStack?.destination?.route
                val initialAnimeId by viewModel.initialAnimeId.collectAsState()

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
                            startDestination = viewModel.getStartDestination(),
                            initialAnimeId = initialAnimeId,
                            onAnimeIdConsumed = { viewModel.consumeInitialAnimeId() }
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
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val token = task.result
                val prefs = getSharedPreferences("anime_prefs", MODE_PRIVATE)
                SessionManager.saveFcmToken(prefs, token)
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        viewModel.setInitialAnimeId(intent.getStringExtra("anime_id"))
    }
}
