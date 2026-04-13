package com.example.gael_somer_anime.core.di

import com.example.gael_somer_anime.core.hardware.*
import com.example.gael_somer_anime.core.services.*
import com.example.gael_somer_anime.core.util.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class HardwareModule {

    @Binds
    @Singleton
    abstract fun bindBiometricManager(
        biometricManagerImpl: AndroidBiometricManagerImpl
    ): BiometricManager

    @Binds
    @Singleton
    abstract fun bindVibrationManager(
        vibrationManagerImpl: AndroidVibrationManagerImpl
    ): VibrationManager

    @Binds
    @Singleton
    abstract fun bindShakeDetector(
        shakeDetectorImpl: AndroidShakeDetectorImpl
    ): ShakeDetector

    @Binds
    @Singleton
    abstract fun bindFileUtil(
        fileUtilImpl: AndroidFileUtilImpl
    ): FileUtil

    @Binds
    @Singleton
    abstract fun bindImageCacheScheduler(
        imageCacheSchedulerImpl: AndroidImageCacheSchedulerImpl
    ): ImageCacheScheduler

    @Binds
    @Singleton
    abstract fun bindImageHelper(
        imageHelperImpl: AndroidImageHelperImpl
    ): ImageHelper
}

