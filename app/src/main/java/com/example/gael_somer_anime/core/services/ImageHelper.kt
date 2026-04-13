package com.example.gael_somer_anime.core.services

import java.io.File

interface ImageHelper {
    fun getLocalPath(animeId: Int): File
    fun getImageModel(animeId: Int, remoteUrl: String?): Any
}
