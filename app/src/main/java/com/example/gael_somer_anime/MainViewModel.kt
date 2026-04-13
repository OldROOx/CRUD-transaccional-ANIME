package com.example.gael_somer_anime

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.gael_somer_anime.core.navigation.Screens
import com.example.gael_somer_anime.core.network.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val application: Application
) : ViewModel() {

    private val _initialAnimeId = MutableStateFlow<String?>(null)
    val initialAnimeId = _initialAnimeId.asStateFlow()

    fun setInitialAnimeId(id: String?) {
        _initialAnimeId.value = id
    }

    fun consumeInitialAnimeId() {
        _initialAnimeId.value = null
    }

    fun getStartDestination(): String {
        val prefs = application.getSharedPreferences("anime_prefs", Context.MODE_PRIVATE)
        return if (SessionManager.fetchToken(prefs) != null) {
            Screens.Home.route
        } else {
            Screens.Login.route
        }
    }

    fun isUserLoggedIn(): Boolean {
        val prefs = application.getSharedPreferences("anime_prefs", Context.MODE_PRIVATE)
        return SessionManager.fetchToken(prefs) != null
    }
}
