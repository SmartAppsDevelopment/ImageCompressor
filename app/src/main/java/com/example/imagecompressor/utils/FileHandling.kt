package com.example.imagecompressor.utils


object FileHandling {
    fun getFileNameAfterCompression(name: String = "COMPRESS"): String =
        "IMG_${(0..900).random()}_${name}.JPG"
}