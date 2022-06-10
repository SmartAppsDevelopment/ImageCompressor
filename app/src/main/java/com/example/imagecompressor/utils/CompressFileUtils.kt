package com.example.imagecompressor.utils

import java.io.File
import kotlin.math.roundToInt

object CompressFileUtils {
    const val COMPRESS_QUALITY = 50

    fun getFolderSizeLabel(file: File): String {
        val size = getFileSize(file).toDouble() / 1000.0 // Get size and convert bytes into KB.
        return if (size >= 1024) {
            (size / 1024).roundToInt().toString() + " MB"
        } else {
            "$size KB"
        }
    }

    private fun getFileSize(file: File): Long = file.length()
}