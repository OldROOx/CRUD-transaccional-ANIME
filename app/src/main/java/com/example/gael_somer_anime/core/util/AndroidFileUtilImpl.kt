package com.example.gael_somer_anime.core.util

import android.content.Context
import android.net.Uri
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AndroidFileUtilImpl @Inject constructor(
    @ApplicationContext applicationContext: Context
) : FileUtil {
    private val context = applicationContext
    override fun uriToFile(uri: Uri): File? {
        return try {
            val tempFile = File(context.cacheDir, "temp_upload_${System.currentTimeMillis()}.jpg")
            context.contentResolver.openInputStream(uri)?.use { input ->
                FileOutputStream(tempFile).use { output ->
                    input.copyTo(output)
                }
            }
            tempFile
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
