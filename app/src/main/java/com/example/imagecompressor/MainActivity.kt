package com.example.imagecompressor

import android.net.Uri
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.imagecompressor.databinding.ActivityMainBinding
import com.example.imagecompressor.utils.*
import kotlinx.coroutines.*
import permissions.dispatcher.NeedsPermission
import permissions.dispatcher.RuntimePermissions
import java.io.File


@RuntimePermissions
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var sourceImageFile: File? = null
    private var tcCounter = 1

    private val getContent = registerForActivityResult(ActivityResultContracts.TakePicture()) {
        if (it) {
            binding.ivPickImage.setImageURI(getUriFromFile(sourceImageFile!!))
        } else {
            showToast("Fail To get Image ")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.tvHint.movementMethod = ScrollingMovementMethod()

        binding.ivPickImage.setOnClickListener {
            requestCameraForImageCaptureWithPermissionCheck()
        }
        binding.button.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                compressImage()
            }
        }
    }

    private suspend fun compressImage() = withContext(Dispatchers.IO) {
         val destinationFile = getDestinationFileForImage()
        try {
            appendLog("--------------TC${tcCounter++}")
            appendLog("CompressQuality ${CompressFileUtils.COMPRESS_QUALITY}" )
            appendLog("Before Compress Size = ${CompressFileUtils.getFolderSizeLabel(sourceImageFile!!)}")

            val timeToExecute = CompressImage.compressImage(sourceImageFile!!, destinationFile)
            updateImage(destinationFile)
            appendLog("Compress Time:= $timeToExecute Ms")
            appendLog("After Compress Size = ${CompressFileUtils.getFolderSizeLabel(destinationFile)}")
            showToast("File Compressed" /*+ currentFileUri.toString()*/)
        } catch (e: Exception) {
            showToast("Select Image First ")
        }

    }

    private fun updateImage(fileDest:File)=runOnUiThread {
        binding.ivAfterimg.setImageURI(null)
        binding.ivAfterimg.setImageURI(Uri.fromFile(fileDest))
    }

    @NeedsPermission(android.Manifest.permission.CAMERA)
    fun requestCameraForImageCapture() {
        sourceImageFile = getFileForSaveCameraImages()
        getContent.launch(getUriFromFile(sourceImageFile!!))
    }

    private fun appendLog(msg: String) {
        binding.tvHint.append("\n$msg")
    }

}

