package com.example.gael_somer_anime.features.auth.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.example.gael_somer_anime.features.auth.domain.usecases.LogoutUseCase

class HeaderViewModel(private val logoutUseCase: LogoutUseCase) : ViewModel() {
    fun logout(onLogoutSuccess: () -> Unit) {
        logoutUseCase()
        onLogoutSuccess()
    }
}