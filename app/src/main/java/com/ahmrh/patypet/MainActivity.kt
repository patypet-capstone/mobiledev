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
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.ahmrh.patypet.ui.navigation.Screen
import com.ahmrh.patypet.ui.screen.auth.AuthViewModel
import com.ahmrh.patypet.ui.screen.auth.LandingScreen
import com.ahmrh.patypet.ui.screen.auth.login.SignInScreen
import com.ahmrh.patypet.ui.screen.auth.register.SignUpScreen
import com.ahmrh.patypet.ui.theme.PatypetTheme
import com.ahmrh.patypet.domain.utils.rotateFile
import com.ahmrh.patypet.ui.screen.auth.register.SignUpViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.io.File

@AndroidEntryPoint
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
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Screen.Auth.route,
                    ){
                        composable(Screen.Launch.route){

                        }
                        navigation(
                            startDestination = Screen.Landing.route,
                            route = Screen.Auth.route
                        ){
                            composable(Screen.Landing.route){
                                LandingScreen(
                                    navigateToSignIn = {
                                    },
                                    navigateToSignUp = {
                                        navController.navigate(Screen.SignUp.route)
                                    }
                                )
                            }
                            composable(Screen.SignIn.route){
                                val viewModel = it.sharedViewModel<AuthViewModel>(
                                    navController = navController
                                )
                                SignInScreen(
                                    viewModel.uiState,
                                    viewModel::login,
                                    viewModel::forceLogin
                                )

                            }
                            composable(Screen.SignUp.route){
                                val viewModel = it.sharedViewModel<SignUpViewModel>(
                                    navController = navController
                                )
                                SignUpScreen(
                                    viewModel.state,
                                    viewModel::signUp,
                                )
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

//                    val authViewModel: AuthViewModel = viewModel(
//                        factory = ViewModelFactory(LocalContext.current)
//                    )
//                    authViewModel.getAuthState()
//                    val authState = authViewModel.authState
//
//                    val authState: MutableStateFlow<AuthState> = MutableStateFlow(AuthState.Authenticated(""))
//
//                    authState.collectAsState(initial = AuthState.Unknown).value.let { state ->
//                        when (state) {
//                            is AuthState.Unknown -> {
//                                Authenticator()
//                            }
//
//                            is AuthState.Authenticated -> {
//                                PatypetApp()
//                            }
//
//                        }
//                    }


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

