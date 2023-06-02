package com.ahmrh.patypet

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.ahmrh.patypet.ui.navigation.Screen
import com.ahmrh.patypet.ui.screen.auth.AuthViewModel
import com.ahmrh.patypet.ui.screen.auth.LandingScreen
import com.ahmrh.patypet.ui.theme.PatypetTheme
import com.ahmrh.patypet.utils.AuthState
import com.ahmrh.patypet.utils.ViewModelFactory
import com.ahmrh.patypet.utils.rotateFile
import java.io.File

class MainActivity : ComponentActivity() {
    private var getFile: File? = null
    companion object {
        const val CAMERA_X_RESULT = 200

        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        private const val REQUEST_CODE_PERMISSIONS = 10
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            PatypetTheme {
                val navController = rememberNavController()
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = "auth",
                    ){
                        composable(Screen.Launch.route){

                        }
                        navigation(
                            startDestination = Screen.Landing.route,
                            route = "auth"
                        ){
                            composable(Screen.Landing.route){
                                LandingScreen()
                            }
                            composable(Screen.SignIn.route){

                            }
                            composable(Screen.SignUp.route){

                            }
                        }

                        navigation(
                            startDestination = Screen.Home.route,
                            route = "patypet"
                        ){
                            composable(Screen.Home.route){

                            }

                            composable(Screen.Home.route){

                            }

                            composable(Screen.SignUp.route){

                            }
                        }
                    }

                    val authViewModel: AuthViewModel = viewModel(
                        factory = ViewModelFactory(LocalContext.current)
                    )
                    authViewModel.getAuthState()
                    val authState = authViewModel.authState

//                    val authState: MutableStateFlow<AuthState> = MutableStateFlow(AuthState.Authenticated(""))

                    authState.collectAsState(initial = AuthState.Unknown).value.let { state ->
                        when (state) {
                            is AuthState.Unknown -> {
                                Authenticator()
                            }

                            is AuthState.Authenticated -> {
                                PatypetApp()
                            }

                        }
                    }


                }
            }
        }
    }

    private val launcherIntentCameraX = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == CAMERA_X_RESULT) {
            val myFile = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                it.data?.getSerializableExtra("picture", File::class.java)
            } else {
                @Suppress("DEPRECATION")
                it.data?.getSerializableExtra("picture")
            } as? File

            val isBackCamera = it.data?.getBooleanExtra("isBackCamera", true) as Boolean

            myFile?.let { file ->
                rotateFile(file, isBackCamera)
                getFile = file
            }
        }
    }

    private fun startCameraX() {
        val intent = Intent(this, CameraActivity::class.java)
        launcherIntentCameraX.launch(intent)
    }

}

@Composable
fun NavBackStackEntry.sharedViewModel(navController: NavController) : ViewModel {
    val navGraphRoute = destination.parent?.route ?: return viewModel()
    val parentEntry = remember(this) {
        navController.getBackStackEntry(navGraphRoute)
    }
    return viewModel(parentEntry)
}

fun Activity.openAppSettings(){
    Intent(
        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
        Uri.fromParts("package", packageName, null)
    ).also(::startActivity)
}

