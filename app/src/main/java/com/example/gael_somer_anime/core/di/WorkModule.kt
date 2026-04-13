package com.example.gael_somer_anime.core.di

import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class WorkModule {

    companion object {
        @Provides
        @Singleton
        fun provideWorkManagerConfiguration(
            workerFactory: HiltWorkerFactory
        ): Configuration {
            return Configuration.Builder()
                .setWorkerFactory(workerFactory)
                .build()
        }
    }
}
