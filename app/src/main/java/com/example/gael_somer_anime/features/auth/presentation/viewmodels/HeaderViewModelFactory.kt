package com.example.gael_somer_anime.features.auth.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.gael_somer_anime.features.auth.domain.usecases.LogoutUseCase

class HeaderViewModelFactory(private val logoutUseCase: LogoutUseCase) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HeaderViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HeaderViewModel(logoutUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}