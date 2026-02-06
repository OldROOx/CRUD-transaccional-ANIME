package com.example.gael_somer_anime.features.anime.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.gael_somer_anime.features.anime.domain.usecases.CreateAnimeUseCase
import com.example.gael_somer_anime.features.anime.domain.usecases.DeleteAnimeUseCase
import com.example.gael_somer_anime.features.anime.domain.usecases.GetAnimesUseCase
import com.example.gael_somer_anime.features.anime.domain.usecases.UpdateAnimeUseCase

class AnimesViewModelFactory(
    private val getAnimesUseCase: GetAnimesUseCase,
    private val createAnimeUseCase: CreateAnimeUseCase,
    private val updateAnimeUseCase: UpdateAnimeUseCase,
    private val deleteAnimeUseCase: DeleteAnimeUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AnimesViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AnimesViewModel(
                getAnimesUseCase,
                createAnimeUseCase,
                updateAnimeUseCase,
                deleteAnimeUseCase
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}