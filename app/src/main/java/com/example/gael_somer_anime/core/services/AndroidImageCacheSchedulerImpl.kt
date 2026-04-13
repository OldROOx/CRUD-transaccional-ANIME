package com.example.gael_somer_anime.core.services

import android.content.Context
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AndroidImageCacheSchedulerImpl @Inject constructor(
    @ApplicationContext applicationContext: Context
) : ImageCacheScheduler {

    private val context = applicationContext
    override fun schedule() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresBatteryNotLow(true)
            .build()

        val workRequest = PeriodicWorkRequestBuilder<ImageCacheWorker>(
            6, TimeUnit.HOURS
        )
            .setConstraints(constraints)
            .setInitialDelay(1, TimeUnit.MINUTES)
            .build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            ImageCacheWorker.WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            workRequest
        )
    }

    override fun runNow() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val workRequest = OneTimeWorkRequestBuilder<ImageCacheWorker>()
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(context).enqueueUniqueWork(
            "${ImageCacheWorker.WORK_NAME}_one_time",
            ExistingWorkPolicy.REPLACE,
            workRequest
        )
    }

    override fun cancelAll() {
        WorkManager.getInstance(context).cancelUniqueWork(ImageCacheWorker.WORK_NAME)
    }
}
