package com.example.imagecompressor

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.databinding.DataBindingUtil
import com.example.imagecompressor.databinding.ActivityMainBinding
import permissions.dispatcher.NeedsPermission
import permissions.dispatcher.RuntimePermissions
import java.io.File
import java.io.FileOutputStream



@RuntimePermissions
class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    var stringlogger = StringBuilder()
    var currentFileUri: Uri? = null
    var currentFile: File? = null
    var tcCounter=1

    val getContent by lazy {
        registerForActivityResult(ActivityResultContracts.TakePicture()) {
            if (it) {
                binding.ivPickImage.setImageURI(currentFileUri)
            } else {
                showToast("Fail To get Image ")
            }
        }
    }

    fun compressImg(){
        val destbaseFile = File(cacheDir, "DestImg")
        destbaseFile.mkdir()
        val destFile = File(destbaseFile, "sldkfjsdlfkdsjf.JPG")
        destFile.createNewFile()
        stringlogger.appendLog("--------------TC${tcCounter++}")
        stringlogger.appendLog("CompressQuality " + FileUtilsm.compressQuality)
        stringlogger.appendLog(
            "Before Compress Size = " + FileUtilsm.getFolderSizeLabel(
                currentFile!!
            )
        )
        val currentBitmap = BitmapFactory.decodeFile(currentFile!!.absolutePath)
        val startTime = System.currentTimeMillis()
        currentBitmap.compress(
            Bitmap.CompressFormat.JPEG,
            FileUtilsm.compressQuality,
            FileOutputStream(destFile)
        )
        val endTime = System.currentTimeMillis()
        val totalTimeInSEc = (endTime - startTime)
        binding.ivAfterimg.setImageURI(null)
        binding.ivAfterimg.setImageURI(Uri.fromFile(destFile))
        stringlogger.appendLog("Compress Time:=" + totalTimeInSEc.toString()+" Ms")
        stringlogger.appendLog(
            "AFter Compress Size = " + FileUtilsm.getFolderSizeLabel(
                destbaseFile!!
            )
        )

        showToast("Success Full " + currentFileUri.toString())
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        stringlogger = StringBuilder()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.tvHint.setMovementMethod(ScrollingMovementMethod());

        binding.ivPickImage.setOnClickListener {
            requestCameraForImageCaptureWithPermissionCheck()
        }
        ///Fake call to register at on Create
        getContent
        binding.button.setOnClickListener {
            compressImg()
        }
    }

    @NeedsPermission(android.Manifest.permission.CAMERA)
    public fun requestCameraForImageCapture() {
        currentFile = File(cacheDir, "IMG_${(0..900).random()}.jpg")
        currentFileUri =
            FileProvider.getUriForFile(
                this,
                applicationContext.packageName + ".provider",
                currentFile!!
            )
        getContent.launch(currentFileUri)
    }


    fun StringBuilder.appendLog(msg: String) {
        stringlogger.append("\n $msg")
        binding.tvHint.setText(stringlogger)
    }


}

fun Context.showToast(msg: String) {
    Log.e("Context.showToast", "showToast: " + msg)
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}