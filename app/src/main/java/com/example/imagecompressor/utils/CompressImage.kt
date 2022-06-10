package com.example.imagecompressor.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.File
import java.io.FileOutputStream

object CompressImage {

    suspend fun compressImage(sourceFile: File, destinationFile:File):Long{
        return measureTimeMillis {
            val currentBitmap = BitmapFactory.decodeFile(sourceFile.absolutePath)
            currentBitmap.compress(
                Bitmap.CompressFormat.JPEG,
                CompressFileUtils.COMPRESS_QUALITY,
                FileOutputStream(destinationFile)
            )
        }
    }
}