package com.example.gael_somer_anime.core.services

interface ImageCacheScheduler {
    fun schedule()
    fun runNow()
    fun cancelAll()
}
