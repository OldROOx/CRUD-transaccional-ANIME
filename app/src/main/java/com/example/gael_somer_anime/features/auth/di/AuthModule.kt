package com.example.gael_somer_anime.features.auth.di

import com.example.gael_somer_anime.core.di.AppContainer
import com.example.gael_somer_anime.features.auth.domain.repositories.AuthRepository
import com.example.gael_somer_anime.features.auth.domain.usecases.LoginUseCase
import com.example.gael_somer_anime.features.auth.domain.usecases.LogoutUseCase
import com.example.gael_somer_anime.features.auth.domain.usecases.RegisterUseCase
import com.example.gael_somer_anime.features.auth.presentation.viewmodels.HeaderViewModelFactory
import com.example.gael_somer_anime.features.auth.presentation.viewmodels.LoginViewModelFactory
import com.example.gael_somer_anime.features.auth.presentation.viewmodels.RegisterViewModelFactory

class AuthModule(private val appContainer: AppContainer) {

    private val authRepository: AuthRepository = appContainer.authRepository

    private fun provideLoginUseCase(): LoginUseCase {
        return LoginUseCase(authRepository)
    }

    private fun provideRegisterUseCase(): RegisterUseCase {
        return RegisterUseCase(authRepository)
    }

    private fun provideLogoutUseCase(): LogoutUseCase {
        return LogoutUseCase(authRepository)
    }

    fun provideLoginViewModelFactory(): LoginViewModelFactory {
        return LoginViewModelFactory(provideLoginUseCase())
    }

    fun provideRegisterViewModelFactory(): RegisterViewModelFactory {
        return RegisterViewModelFactory(provideRegisterUseCase())
    }

    fun provideHeaderViewModelFactory(): HeaderViewModelFactory {
        return HeaderViewModelFactory(provideLogoutUseCase())
    }
}