package com.example.imagecompressor

import java.io.File

object FileUtilsm {
    val compressQuality=50
    fun getFolderSizeLabel(file: File): String? {
        val size = getFolderSize(file).toDouble() / 1000.0 // Get size and convert bytes into KB.
        return if (size >= 1024) {


           Math.round((size / 1024)).toString() + " MB"
        } else {
            "$size KB"
        }
    }

    private fun getFolderSize(file: File): Long {
        var size: Long = 0
        if (file.isDirectory) {
            for (child in file.listFiles()) {
                size += getFolderSize(child)
            }
        } else {
            size = file.length()
        }
        return size
    }
}