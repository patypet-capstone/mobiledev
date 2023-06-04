package com.ahmrh.patypet.ui.screen.patypet.pet

import android.Manifest
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.Image
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import coil.compose.rememberImagePainter
import com.ahmrh.patypet.CameraActivity
import com.ahmrh.patypet.di.Injection.findActivity
import com.ahmrh.patypet.openAppSettings
import com.ahmrh.patypet.ui.components.dialog.CameraPermissionTextProvider
import com.ahmrh.patypet.ui.components.dialog.PermissionDialog
import java.io.File

@Composable
fun PetScreen(
    launcherIntentCameraX: ActivityResultLauncher<Intent>,
    viewModel: PetViewModel,
    navigateUp: () -> Unit,
    file: File?
) {
    CameraLayer(
        launcherIntentCameraX = launcherIntentCameraX,
        viewModel = viewModel,
        navigateUp = navigateUp
    )
    if(file != null){
        Box(
            modifier = Modifier.fillMaxSize()
        ){
            Log.d("Pet Image", "${file.absolutePath} asdasd")

            var imgBitmap: Bitmap? = null
            imgBitmap = BitmapFactory.decodeFile(file.absolutePath)
            Image(
                painter = rememberImagePainter(data = imgBitmap),
                contentDescription = "Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(10.dp)
            )
        }
    }

}

@Composable
fun CameraLayer(
    launcherIntentCameraX: ActivityResultLauncher<Intent>,
    viewModel: PetViewModel,
    navigateUp: () -> Unit
) {

    val context = LocalContext.current

    val multiplePermissionResultLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestMultiplePermissions(),
            onResult = { perms ->
                perms.keys.forEach { permission ->
                    viewModel.onPermissionResult(
                        permission = permission,
                        isGranted = perms[permission] == true
                    )

                }

            }
        )

    val dialogQueue = viewModel.visiblePermissionDialogQueue

    dialogQueue
        .reversed()
        .forEach { permission ->
            PermissionDialog(
                permissionTextProvider = when (permission) {
                    Manifest.permission.CAMERA -> {
                        CameraPermissionTextProvider()
                    }

                    else -> return@forEach
                },
                isPermanentlyDeclined = !ActivityCompat.shouldShowRequestPermissionRationale(
                    LocalContext.current.findActivity(),
                    permission
                ),
                onDeny = {
                    navigateUp()
                },
                onDismiss = viewModel::dismissDialog,
                onOkClick = {
                    viewModel.dismissDialog()
                    multiplePermissionResultLauncher.launch(
                        arrayOf(permission)
                    )
                },
                onGoToAppSettingsClick = {
                    context.findActivity().openAppSettings()
                }
            )
        }

    LaunchedEffect(key1 = true) {
        multiplePermissionResultLauncher.launch(
            arrayOf(
                Manifest.permission.CAMERA
            )
        )

        if (Manifest.permission.CAMERA in viewModel.permission) {
            launcherIntentCameraX.launch(
                Intent(
                    context,
                    CameraActivity::class.java
                )
            )
        }
    }
}
