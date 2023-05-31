package com.ahmrh.patypet.utils

import android.content.Context
import androidx.camera.lifecycle.ProcessCameraProvider
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

suspend fun Context.getCameraProvider(): ProcessCameraProvider =
    suspendCoroutine { continuation ->
        ProcessCameraProvider.getInstance(this)
            .also { future ->
                future.addListener(
                    {
                        continuation.resume(future.get())
                    },
                    mainExecutor
                )
            }
    }