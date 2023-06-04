package com.ahmrh.patypet

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.ahmrh.patypet.ui.components.BottomBar
import com.ahmrh.patypet.ui.navigation.Screen
import com.ahmrh.patypet.ui.screen.auth.LandingScreen
import com.ahmrh.patypet.ui.screen.auth.login.SignInScreen
import com.ahmrh.patypet.ui.screen.auth.register.SignInViewModel
import com.ahmrh.patypet.ui.screen.auth.register.SignUpScreen
import com.ahmrh.patypet.ui.screen.auth.register.SignUpViewModel
import com.ahmrh.patypet.ui.screen.patypet.home.HomeScreen
import com.ahmrh.patypet.ui.screen.patypet.pet.PetPredictionScreen
import com.ahmrh.patypet.ui.screen.patypet.pet.PetCameraScreen
import com.ahmrh.patypet.ui.screen.patypet.pet.PetViewModel
import com.ahmrh.patypet.ui.theme.PatypetTheme
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private var getFile: File? = null

    private var shouldShowCamera: MutableState<Boolean> = mutableStateOf(false)
    private lateinit var photoUri: Uri
    private var shouldShowPhoto: MutableState<Boolean> = mutableStateOf(false)
    private lateinit var cameraExecutor: ExecutorService
    private lateinit var outputDirectory: File

    companion object {
        const val CAMERA_X_RESULT = 200

        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        private const val REQUEST_CODE_PERMISSIONS = 10
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()

        val outputDirectory = getOutputDirectory()
        val cameraExecutor = Executors.newSingleThreadExecutor()

        requestCameraPermission()
        setContent {
            PatypetTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    val currentRoute = navBackStackEntry?.destination?.route
                    Scaffold(
                        bottomBar = {
                            if (isVisibleBarRoute(currentRoute)){
                                BottomBar(
                                    navController = navController
                                )
                            }
                        }
                    ) {
                        Box(modifier = Modifier.padding(it)){
                            NavHost(
                                navController = navController,
                                startDestination = Screen.Auth.route,
                            ){
                                composable(Screen.Launch.route){

                                }
                                navigation(
                                    startDestination = Screen.Auth.Landing.route,
                                    route = Screen.Auth.route
                                ){
                                    composable(Screen.Auth.Landing.route){
                                        LandingScreen(
                                            navigateToSignIn = {
                                                navController.navigate(Screen.Auth.SignIn.route)
                                            },
                                            navigateToSignUp = {
                                                navController.navigate(Screen.Auth.SignUp.route)
                                            },
                                        )
                                    }
                                    composable(Screen.Auth.SignIn.route){
                                        val viewModel = it.sharedViewModel<SignInViewModel>(
                                            navController = navController
                                        )
                                        SignInScreen(
                                            viewModel.uiState,
                                            viewModel::signIn,
                                            authenticate = {
                                                navController.navigate(Screen.Patypet.route){
                                                    popUpTo(Screen.Auth.route){
                                                        inclusive = true
                                                    }
                                                }
                                            }
                                        )

                                    }
                                    composable(Screen.Auth.SignUp.route){
                                        val viewModel = it.sharedViewModel<SignUpViewModel>(
                                            navController = navController
                                        )
                                        SignUpScreen(
                                            viewModel.uiState,
                                            viewModel::signUp,
                                        )
                                    }
                                }

                                navigation(
                                    startDestination = Screen.Patypet.Home.route,
                                    route = Screen.Patypet.route
                                ){
                                    composable(Screen.Patypet.Home.route){
                                        HomeScreen(
                                            deauthenticate = {
                                                navController.navigate(Screen.Auth.route){
                                                    popUpTo(Screen.Patypet.route){
                                                        inclusive=true
                                                    }
                                                }
                                            }
                                        )
                                    }

                                    composable(Screen.Patypet.Pet.route){
                                        val viewModel = it.sharedViewModel<PetViewModel>(
                                            navController = navController
                                        )
                                        LaunchedEffect(key1= true){
                                            shouldShowCamera.value = true
                                        }

                                        if (shouldShowCamera.value) {
                                            PetCameraScreen(
                                                outputDirectory = outputDirectory,
                                                executor = cameraExecutor,
                                                onImageCaptured = ::handleImageCapture,
                                                onError = { Log.e("kilo", "View error:", it) }
                                            )
                                        }
                                        if(shouldShowPhoto.value){
                                            PetPredictionScreen(
                                                photoUri = photoUri
                                            )
                                        }

                                    }

                                }
                            }
                        }
                    }

                }
            }
        }
    }

    private fun isVisibleBarRoute(route: String?): Boolean{
        val authRoute = listOf(

            Screen.Patypet.Home.route,
            Screen.Patypet.Profile.route
        )
        return route in authRoute
    }

    private fun handleImageCapture(uri: Uri) {
        Log.i("kilo", "Image captured: $uri")
        shouldShowCamera.value = false
        photoUri = uri
        shouldShowPhoto.value = true
    }

    private fun getOutputDirectory(): File {
        val mediaDir = externalMediaDirs.firstOrNull()?.let {
            File(it, resources.getString(R.string.app_name)).apply { mkdirs() }
        }

        return if (mediaDir != null && mediaDir.exists()) mediaDir else filesDir
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
    }
    private fun requestCameraPermission() {
        when {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED -> {
                Log.i("kilo", "Permission previously granted")
                shouldShowCamera.value = true // 👈🏽
            }

            ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                Manifest.permission.CAMERA
            ) -> Log.i("kilo", "Show camera permissions dialog")

            else -> requestPermissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            Log.i("kilo", "Permission granted")
            shouldShowCamera.value = true // 👈🏽
        } else {
            Log.i("kilo", "Permission denied")
        }
    }

}

@Composable
inline fun <reified T: ViewModel>NavBackStackEntry.sharedViewModel(navController: NavController) : T {
    val navGraphRoute = destination.parent?.route ?: return hiltViewModel()
    val parentEntry = remember(this) {
        navController.getBackStackEntry(navGraphRoute)
    }
    return hiltViewModel(parentEntry)
}

fun Activity.openAppSettings(){
    Intent(
        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
        Uri.fromParts("package", packageName, null)
    ).also(::startActivity)
}

