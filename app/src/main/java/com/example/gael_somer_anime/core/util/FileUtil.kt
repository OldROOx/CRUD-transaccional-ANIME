package com.example.gael_somer_anime.core.util

import android.net.Uri
import java.io.File

interface FileUtil {
    fun uriToFile(uri: Uri): File?
}
