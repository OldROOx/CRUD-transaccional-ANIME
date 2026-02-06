package com.example.gael_somer_anime.features.anime.di

import com.example.gael_somer_anime.core.di.AppContainer
import com.example.gael_somer_anime.features.anime.domain.repositories.AnimeRepository
import com.example.gael_somer_anime.features.anime.domain.usecases.CreateAnimeUseCase
import com.example.gael_somer_anime.features.anime.domain.usecases.DeleteAnimeUseCase
import com.example.gael_somer_anime.features.anime.domain.usecases.GetAnimesUseCase
import com.example.gael_somer_anime.features.anime.domain.usecases.UpdateAnimeUseCase
import com.example.gael_somer_anime.features.anime.presentation.viewmodels.AnimesViewModelFactory

class AnimeModule(private val appContainer: AppContainer) {

    private val animeRepository: AnimeRepository = appContainer.animeRepository

    private fun provideGetAnimesUseCase(): GetAnimesUseCase {
        return GetAnimesUseCase(animeRepository)
    }

    private fun provideCreateAnimeUseCase(): CreateAnimeUseCase {
        return CreateAnimeUseCase(animeRepository)
    }

    private fun provideUpdateAnimeUseCase(): UpdateAnimeUseCase {
        return UpdateAnimeUseCase(animeRepository)
    }

    private fun provideDeleteAnimeUseCase(): DeleteAnimeUseCase {
        return DeleteAnimeUseCase(animeRepository)
    }

    fun provideAnimesViewModelFactory(): AnimesViewModelFactory {
        return AnimesViewModelFactory(
            getAnimesUseCase = provideGetAnimesUseCase(),
            createAnimeUseCase = provideCreateAnimeUseCase(),
            updateAnimeUseCase = provideUpdateAnimeUseCase(),
            deleteAnimeUseCase = provideDeleteAnimeUseCase()
        )
    }
}