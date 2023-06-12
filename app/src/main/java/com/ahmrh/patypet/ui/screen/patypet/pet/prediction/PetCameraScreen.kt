package com.ahmrh.patypet.ui.screen.patypet.pet.prediction

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.ahmrh.patypet.R
import java.io.File
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.concurrent.Executor
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@Composable
fun PetCameraScreen(
    outputDirectory: File,
    executor: Executor,
    onImageCaptured: (Uri) -> Unit,
    onError: (ImageCaptureException) -> Unit,
    onPredict: (img: Any) -> Unit = {},
) {
    // 1
    val lensFacing = CameraSelector.LENS_FACING_BACK
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val preview = Preview.Builder().build()
    val previewView = remember { PreviewView(context) }
    val imageCapture: ImageCapture =
        remember { ImageCapture.Builder().build() }
    val cameraSelector = CameraSelector.Builder()
        .requireLensFacing(lensFacing)
        .build()

    // 2
    LaunchedEffect(lensFacing) {
        val cameraProvider = context.getCameraProvider()
        cameraProvider.unbindAll()
        cameraProvider.bindToLifecycle(
            lifecycleOwner,
            cameraSelector,
            preview,
            imageCapture
        )

        preview.setSurfaceProvider(previewView.surfaceProvider)
    }

    // 3
    Box(
        contentAlignment = Alignment.BottomCenter,
        modifier = Modifier.fillMaxSize()
    ) {
        AndroidView(
            { previewView },
            modifier = Modifier.fillMaxSize()
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
        ) {
            IconButton(
                onClick = {
                    Log.i("kilo", "ON GALLERY")
                },
                content = {

                },
                modifier = Modifier
                    .size(72.dp)
                    .align(
                        Alignment.BottomStart
                    )
            )

            IconButton(
                onClick = {
                    Log.i("kilo", "ON CAPTURE")
                    takePhoto(
                        filenameFormat = "yyyy-MM-dd-HH-mm-ss-SSS",
                        imageCapture = imageCapture,
                        outputDirectory = outputDirectory,
                        executor = executor,
                        onImageCaptured = onImageCaptured,
                        onError = onError,
                        onPredict = onPredict,
                    )
                },
                content = {
                    Icon(
                        painter = painterResource(id = R.drawable.outline_circle_24),
                        contentDescription = "Take picture",
                        tint = Color.White,
                        modifier = Modifier.fillMaxSize()
                    )
                },
                modifier = Modifier
                    .size(72.dp)
                    .align(
                        Alignment.Center
                    )
            )
        }

    }
}


private fun takePhoto(
    filenameFormat: String,
    imageCapture: ImageCapture,
    outputDirectory: File,
    executor: Executor,
    onImageCaptured: (Uri) -> Unit,
    onError: (ImageCaptureException) -> Unit,
    onPredict: (img: Any) -> Unit = {},
) {
    val photoFile = File(
        outputDirectory,
        SimpleDateFormat(filenameFormat, Locale.US).format(
            System.currentTimeMillis()
        ) + ".jpg"
    )

    val outputOptions =
        ImageCapture.OutputFileOptions.Builder(photoFile)
            .build()

    imageCapture.takePicture(
        outputOptions,
        executor,
        object : ImageCapture.OnImageSavedCallback {
            override fun onError(
                exception: ImageCaptureException
            ) {
                Log.e(
                    "kilo",
                    "Take photo error:",
                    exception
                )
                onError(exception)
            }

            override fun onImageSaved(
                outputFileResults: ImageCapture.OutputFileResults
            ) {
                val savedUri = Uri.fromFile(
                    photoFile
                )

                onPredict(savedUri)
                onImageCaptured(
                    savedUri
                )
            }
        })
}

private fun rotateImage() {

}

private suspend fun Context.getCameraProvider(): ProcessCameraProvider =
    suspendCoroutine { continuation ->
        ProcessCameraProvider.getInstance(this)
            .also { cameraProvider ->
                cameraProvider.addListener(
                    {
                        continuation.resume(
                            cameraProvider.get()
                        )
                    },
                    ContextCompat.getMainExecutor(
                        this
                    )
                )
            }
    }

