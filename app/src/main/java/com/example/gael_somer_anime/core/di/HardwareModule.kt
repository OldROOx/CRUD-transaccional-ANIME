package com.example.gael_somer_anime.core.di

import com.example.gael_somer_anime.core.hardware.BiometricManager
import com.example.gael_somer_anime.core.hardware.AndroidBiometricManagerImpl
import com.example.gael_somer_anime.core.hardware.VibrationManager
import com.example.gael_somer_anime.core.hardware.AndroidVibrationManagerImpl
import com.example.gael_somer_anime.core.hardware.ShakeDetector
import com.example.gael_somer_anime.core.hardware.AndroidShakeDetectorImpl
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
}
