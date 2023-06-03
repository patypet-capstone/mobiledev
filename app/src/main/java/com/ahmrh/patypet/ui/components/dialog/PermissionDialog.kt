package com.ahmrh.patypet.ui.components.dialog

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun PermissionDialog(
    permissionTextProvider: PermissionTextProvider,
    isPermanentlyDeclined: Boolean,
    onDismiss: () -> Unit,
    onOkClick: () -> Unit,
    onDeny: () -> Unit,
    onGoToAppSettingsClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = "Permission Required")
        },
        text = {
            Text(
                text = permissionTextProvider.getDescription(isPermanentlyDeclined)
            )
        },
        confirmButton = {
            TextButton(
                onClick = {
                    if(isPermanentlyDeclined){
                        onGoToAppSettingsClick()
                    } else  {
                        onOkClick()
                    }
                }
            ) {
                Text("Allow")
            }
        },

        dismissButton = {
            if(!isPermanentlyDeclined){
                TextButton(
                    onClick = {
                        onDismiss()
                        onDeny()
                    }
                ) {
                    Text("Don't Allow")
                }
            }
        }
    )
}

interface PermissionTextProvider {
    fun getDescription(isPermanentlyDeclined: Boolean): String
}

class CameraPermissionTextProvider :
    PermissionTextProvider {
    override fun getDescription(isPermanentlyDeclined: Boolean): String {
        return if (isPermanentlyDeclined) {
            "It seems you permanently declined camera permission. You can go to the app settings to grant it."
        } else {
            "This app needs access to your camera to use the pet prediction feature"
        }
    }
}
class FilePermissionTextProvider :
    PermissionTextProvider {
    override fun getDescription(isPermanentlyDeclined: Boolean): String {
        return if (isPermanentlyDeclined) {
            "It seems you permanently declined file permission. You can go to the app settings to grant it."
        } else {
            "This app needs access to your file so you can save file in your phone"
        }
    }
}

@Preview(showBackground = true)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PermissionDialogContent() {
    PermissionDialog(
        permissionTextProvider = CameraPermissionTextProvider(),
        isPermanentlyDeclined = false,
        onDismiss = { /*TODO*/ },
        onOkClick = { /*TODO*/ },
        onGoToAppSettingsClick = { /*TODO*/ },
        onDeny = {}
    )

}