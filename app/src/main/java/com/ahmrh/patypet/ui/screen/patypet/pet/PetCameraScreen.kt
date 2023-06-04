package com.ahmrh.patypet.ui.screen.patypet.pet

import android.Manifest
import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import com.ahmrh.patypet.CameraActivity

@Composable
fun PetCameraScreen(
    launcherIntentCameraX: ActivityResultLauncher<Intent>
) {
//    val cameraPermissionResultLauncher = rememberLauncherForActivityResult(
//        contract = ActivityResultContracts.RequestPermission(),
//        onResult = { isGranted ->
//            mainViewModel.onPermissionResult(
//                permission = Manifest.permission.CAMERA,
//                isGranted = isGranted
//            )
//
//        }
//    )
//    val context = LocalContext.current
//    LaunchedEffect(key1 = true){
//        launcherIntentCameraX.launch(Intent(context, CameraActivity::class.java))
//    }

}