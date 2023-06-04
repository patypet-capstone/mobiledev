package com.ahmrh.patypet.ui.screen.patypet.pet

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.ahmrh.patypet.CameraActivity
import com.ahmrh.patypet.di.Injection.findActivity
import com.ahmrh.patypet.openAppSettings
import com.ahmrh.patypet.ui.components.dialog.CameraPermissionTextProvider
import com.ahmrh.patypet.ui.components.dialog.PermissionDialog

@Composable
fun PetScreen(
    launcherIntentCameraX: ActivityResultLauncher<Intent>,
    viewModel: PetViewModel,
    navigateUp: () -> Unit
) {
    val context = LocalContext.current
    val cameraPermissionResultLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestPermission(),
            onResult = { isGranted ->
                viewModel.onPermissionResult(
                    permission = Manifest.permission.CAMERA,
                    isGranted = isGranted
                )

            }
        )

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

        if(Manifest.permission.CAMERA in viewModel.permission){
            launcherIntentCameraX.launch(
                Intent(
                    context,
                    CameraActivity::class.java
                )
            )
        }

    }
}
