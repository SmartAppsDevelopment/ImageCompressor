package com.example.imagecompressor.utils

import android.content.Context
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.core.content.FileProvider
import java.io.File

fun Context.showToast(msg: String) {
    Handler(Looper.getMainLooper()).post {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}


fun Context.getBaseFilePath(): File = cacheDir

fun Context.getDestinationFileForImage(): File {
    val imageFile = File(getBaseFilePath(), Constants.BASE_DESTINATION_FOLDER_NAME)
    if (imageFile.exists().not())
        imageFile.mkdir()
    val destFile = File(imageFile, FileHandling.getFileNameAfterCompression())
    if (destFile.exists().not())
        destFile.createNewFile()
    return destFile
}

fun Context.getFileForSaveCameraImages(): File {
    val imageFile = File(getBaseFilePath(), Constants.BASE_DESTINATION_FOLDER_NAME_FOR_CAMERA)
    if (imageFile.exists().not())
        imageFile.mkdir()
    val destFile = File(imageFile, FileHandling.getFileNameAfterCompression())
    if (destFile.exists().not())
        destFile.createNewFile()
    return destFile
}

fun Context.getUriFromFile(file:File): Uri {
   return FileProvider.getUriForFile(
        this,
        applicationContext.packageName + ".provider",
        file
    )
}

 inline fun measureTimeMillis(block: () -> Unit): Long {
    val start = System.currentTimeMillis()
    block()
    return System.currentTimeMillis() - start
}
