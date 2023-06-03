package com.ahmrh.patypet

import android.Manifest
import android.content.Context
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ahmrh.patypet.di.Injection.findActivity
import com.ahmrh.patypet.ui.components.dialog.CameraPermissionTextProvider
import com.ahmrh.patypet.ui.components.dialog.PermissionDialog
import com.ahmrh.patypet.ui.navigation.NavigationItem
import com.ahmrh.patypet.ui.navigation.Screen
import com.ahmrh.patypet.ui.screen.auth.AuthViewModel
import com.ahmrh.patypet.ui.screen.patypet.MainViewModel
import com.ahmrh.patypet.ui.screen.patypet.home.HomeScreen
import com.ahmrh.patypet.ui.screen.patypet.pet.PetCameraScreen
import com.ahmrh.patypet.ui.screen.patypet.pet.PetViewModel
import com.ahmrh.patypet.ui.theme.PatypetTheme
import com.ahmrh.patypet.domain.utils.ViewModelFactory

@Composable
fun PatypetApp(
    navController: NavHostController = rememberNavController(),
    authViewModel: AuthViewModel = viewModel(
        factory = ViewModelFactory(LocalContext.current)
    ),
    mainViewModel: MainViewModel = viewModel(
        factory = ViewModelFactory(LocalContext.current)
    ),
    petViewModel: PetViewModel = viewModel(
        factory = ViewModelFactory(LocalContext.current)
    ),
    context : Context = LocalContext.current

) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    val cameraPermissionResultLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            mainViewModel.onPermissionResult(
                permission = Manifest.permission.CAMERA,
                isGranted = isGranted
            )

        }
    )

    val multiplePermissionResultLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { perms ->
            perms.keys.forEach{ permission ->
                mainViewModel.onPermissionResult(
                    permission = permission,
                    isGranted =  perms[permission] == true
                )

            }

        }
    )

    val dialogQueue = mainViewModel.visiblePermissionDialogQueue

    dialogQueue
        .reversed()
        .forEach{ permission ->
            PermissionDialog(
                permissionTextProvider = when(permission){
                    Manifest.permission.CAMERA -> {
                        CameraPermissionTextProvider()
                    }
                    else -> return@forEach
                },
                isPermanentlyDeclined = !shouldShowRequestPermissionRationale(
                    LocalContext.current.findActivity()
                    , permission
                ),
                onDeny = {
                    navController.navigateUp()
                },
                onDismiss = mainViewModel::dismissDialog,
                onOkClick = {
                    mainViewModel.dismissDialog()
                    multiplePermissionResultLauncher.launch(
                        arrayOf(permission)
                    )
                },
                onGoToAppSettingsClick = {
                    context.findActivity().openAppSettings()
                }
            )
        }


//    val bottomBarRoute = listOf(
//        Screen.Home.route,
//        Screen.Profile.route,
//    )
//
//    Scaffold(
//        bottomBar = {
//            if (currentRoute in bottomBarRoute) {
//                BottomBar(navController)
//            }
//        }
//    ) {
//        NavHost(
//            navController = navController,
//            startDestination = Screen.Home.route,
//            modifier = Modifier.padding(it)
//        ){
//            composable(Screen.Home.route){
//                HomeScreen(
//                    onLogout = authViewModel::logout
//                )
//            }
//            composable(Screen.Profile.route){
//
//            }
//            composable(Screen.Pet.route){
//                LaunchedEffect(snackbarHostState){
//                    multiplePermissionResultLauncher.launch(
//                        arrayOf(
//                            Manifest.permission.CAMERA
//                        )
//                    )
//                }
//                PetCameraScreen(
//                )
//            }
//
//        }
//    }
}




@Preview(showBackground = true)
@Composable
fun PatypetAppPreview() {
    PatypetTheme {
    }
}