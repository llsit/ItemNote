package com.example.core.common.image

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import java.io.File

object ImageUriUtils {
    private const val TEMP_IMAGE_PREFIX = "image_"
    private const val TEMP_IMAGE_SUFFIX = ".jpg"

    fun getTempUri(context: Context, authority: String, directory: File): Uri? {
        return try {
            if (!directory.exists()) {
                directory.mkdirs()
            }
            val tempFile = File.createTempFile(
                TEMP_IMAGE_PREFIX + System.currentTimeMillis(),
                TEMP_IMAGE_SUFFIX,
                directory
            )
            FileProvider.getUriForFile(context, authority, tempFile)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}