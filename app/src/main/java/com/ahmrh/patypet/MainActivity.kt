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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.ahmrh.patypet.common.AuthState
import com.ahmrh.patypet.ui.components.bar.BottomBar
import com.ahmrh.patypet.ui.navigation.Screen
import com.ahmrh.patypet.ui.screen.auth.AuthViewModel
import com.ahmrh.patypet.ui.screen.auth.LandingScreen
import com.ahmrh.patypet.ui.screen.auth.login.SignInScreen
import com.ahmrh.patypet.ui.screen.auth.register.SignInViewModel
import com.ahmrh.patypet.ui.screen.auth.register.SignUpScreen
import com.ahmrh.patypet.ui.screen.auth.register.SignUpViewModel
import com.ahmrh.patypet.ui.screen.patypet.adopt.AdoptScreen
import com.ahmrh.patypet.ui.screen.patypet.adopt.AdoptViewModel
import com.ahmrh.patypet.ui.screen.patypet.caretake.CaretakeScreen
import com.ahmrh.patypet.ui.screen.patypet.caretake.CaretakeViewModel
import com.ahmrh.patypet.ui.screen.patypet.home.HomeScreen
import com.ahmrh.patypet.ui.screen.patypet.home.HomeViewModel
import com.ahmrh.patypet.ui.screen.patypet.pet.PetScreen
import com.ahmrh.patypet.ui.screen.patypet.pet.PetViewModel
import com.ahmrh.patypet.ui.screen.patypet.pet.prediction.PetCameraScreen
import com.ahmrh.patypet.ui.screen.patypet.pet.prediction.PetPredictionDetailScreen
import com.ahmrh.patypet.ui.screen.patypet.pet.prediction.PetPredictionScreen
import com.ahmrh.patypet.ui.screen.patypet.profile.ProfileScreen
import com.ahmrh.patypet.ui.screen.patypet.shop.ShopScreen
import com.ahmrh.patypet.ui.screen.patypet.shop.ShopViewModel
import com.ahmrh.patypet.ui.screen.patypet.vet.VetScreen
import com.ahmrh.patypet.ui.screen.patypet.vet.VetViewModel
import com.ahmrh.patypet.ui.theme.PatypetTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.io.File
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private var shouldShowCamera: MutableState<Boolean> =
        mutableStateOf(false)
    private var shouldShowPhoto: MutableState<Boolean> =
        mutableStateOf(false)

    private var photoUri: Uri = Uri.EMPTY
    private lateinit var cameraExecutor: ExecutorService

    companion object {
        const val CAMERA_X_RESULT = 200

        private val REQUIRED_PERMISSIONS =
            arrayOf(Manifest.permission.CAMERA)
        private const val REQUEST_CODE_PERMISSIONS = 10
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val outputDirectory = getOutputDirectory()
        val cameraExecutor =
            Executors.newSingleThreadExecutor()

        requestCameraPermission()
        setContent {
            PatypetTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController =
                        rememberNavController()
                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    val currentRoute =
                        navBackStackEntry?.destination?.route
                    Scaffold(
                        bottomBar = {
                            if (isVisibleBarRoute(
                                    currentRoute
                                )
                            ) {
                                BottomBar(
                                    navController = navController,
                                    onImageRetake = {
                                        handleImageRetake()
                                    }
                                )
                            }
                        },
                        topBar = { },
                    ) {
                        Box(modifier = Modifier.padding(it)) {
                            NavHost(
                                navController = navController,
                                startDestination = Screen.Auth.route,
                            ) {
                                composable(Screen.Launch.route) {

                                }
                                navigation(
                                    startDestination = Screen.Auth.Landing.route,
                                    route = Screen.Auth.route
                                ) {
                                    composable(Screen.Auth.Landing.route) {
                                        val viewModel =
                                            it.sharedViewModel<AuthViewModel>(
                                                navController = navController
                                            )

                                        val authState =
                                            viewModel.authState

                                        when (authState.value) {
                                            is AuthState.Unknown -> {

                                                Log.d(
                                                    "MainActivity",
                                                    "Auth Check: UNKNOWN"
                                                )
                                            }

                                            is AuthState.Authenticated -> {
                                                LaunchedEffect(
                                                    key1 = true
                                                ) {
                                                    navController.navigate(
                                                        Screen.Patypet.route
                                                    ) {
                                                        popUpTo(
                                                            Screen.Auth.route
                                                        ) {
                                                            inclusive =
                                                                true
                                                        }
                                                    }
                                                    Log.d(
                                                        "MainActivity",
                                                        "Auth Check: AUTHENTICATED"
                                                    )
                                                }
                                            }
                                        }

                                        LandingScreen(
                                            navigateToSignIn = {
                                                navController.navigate(
                                                    Screen.Auth.SignIn.route
                                                )
                                            },
                                            navigateToSignUp = {
                                                navController.navigate(
                                                    Screen.Auth.SignUp.route
                                                )
                                            },
                                        )
                                    }
                                    composable(Screen.Auth.SignIn.route) {
                                        val viewModel =
                                            it.sharedViewModel<SignInViewModel>(
                                                navController = navController
                                            )
                                        SignInScreen(
                                            viewModel.uiState,
                                            viewModel::signIn,
                                            authenticate = {
                                                Log.d(
                                                    "MainActivity",
                                                    "Authenticate in Sign In"
                                                )

                                                navController.navigate(
                                                    Screen.Patypet.route
                                                ) {
                                                    popUpTo(
                                                        Screen.Auth.route
                                                    ) {
                                                        inclusive =
                                                            true
                                                    }
                                                }
                                            },
                                            navigateToSignIn = {

                                                navController.navigate(
                                                    Screen.Auth.SignIn.route
                                                ) {
                                                    popUpTo(
                                                        Screen.Auth.route
                                                    )
                                                }
                                            },
                                            viewModel::resetState

                                        )
                                    }

                                    composable(Screen.Auth.SignUp.route) {
                                        val viewModel =
                                            it.sharedViewModel<SignUpViewModel>(
                                                navController = navController
                                            )
                                        SignUpScreen(
                                            viewModel.uiState,
                                            viewModel::signUp,
                                            navigateToSignIn = {
                                                navController.navigate(
                                                    Screen.Auth.SignIn.route
                                                )
                                            },
                                            viewModel::resetState
                                        )
                                    }
                                }

                                navigation(
                                    startDestination = Screen.Patypet.Home.route,
                                    route = Screen.Patypet.route
                                ) {
                                    composable(Screen.Patypet.Home.route) {
                                        val homeViewModel =
                                            it.sharedViewModel<HomeViewModel>(
                                                navController = navController
                                            )

                                        val authViewModel =
                                            it.sharedViewModel<AuthViewModel>(
                                                navController = navController
                                            )
                                        val user =
                                            authViewModel.user.value

                                        LaunchedEffect(
                                            key1 = true
                                        ){
                                            homeViewModel.fetchData()
                                        }


                                        HomeScreen(
                                            deauthenticate = {
                                                navController.navigate(
                                                    Screen.Auth.route
                                                ) {
                                                    popUpTo(
                                                        Screen.Patypet.route
                                                    ) {
                                                        inclusive =
                                                            true
                                                    }
                                                }
                                            },
                                            articleUiState = homeViewModel.articleUiState,
                                            petUiState = homeViewModel.petUiState,
                                            user = user,
                                            navigateToShop = {
                                                navController.navigate(
                                                    Screen.Patypet.Shop.route
                                                )
                                            },
                                            navigateToCaretake = {
                                                navController.navigate(
                                                    Screen.Patypet.Caretake.route
                                                )
                                            },
                                            navigateToVet = {
                                                navController.navigate(
                                                    Screen.Patypet.Vet.route
                                                )
                                            },
                                            navigateToAdopt = {
                                                navController.navigate(
                                                    Screen.Patypet.Adopt.route
                                                )
                                            },
                                            authState = authViewModel.authState.value,
                                        )
                                    }

                                    composable(Screen.Patypet.Pet.route) {
                                        val viewModel =
                                            it.sharedViewModel<PetViewModel>(
                                                navController = navController
                                            )

                                        if (shouldShowCamera.value) {
                                            PetCameraScreen(
                                                outputDirectory = outputDirectory,
                                                executor = cameraExecutor,
                                                onImageCaptured = ::handleImageCapture,
                                                onError = {
                                                    Log.e(
                                                        "kilo",
                                                        "View error:",
                                                        it
                                                    )
                                                },

                                                onPredict = viewModel::predict,
                                            )
                                        }

                                        if (shouldShowPhoto.value) {
                                            PetPredictionScreen(
                                                uiState = viewModel.uiState,
                                                photoUri = photoUri,
                                                onPredict = viewModel::predict,
                                                onRetakePhoto = ::handleImageRetake,
                                                onNavigateToDetail = {
                                                    navController.navigate(
                                                        Screen.Patypet.PredictionDetail.route
                                                    )
                                                },
                                                onSavePet = viewModel::savePet,
                                                bookmarkState = viewModel.bookmarkState
                                            )
                                        }

                                    }
                                    composable(Screen.Patypet.PredictionDetail.route) {
                                        val viewModel =
                                            it.sharedViewModel<PetViewModel>(
                                                navController = navController
                                            )
                                        PetPredictionDetailScreen(
                                            uiState = viewModel.uiState,
                                            onNavigateUp = {
                                                navController.navigateUp()
                                            }
                                        )

                                    }

                                    composable(
                                        route = Screen.Patypet.PetById.route,
                                        arguments = listOf(
                                            navArgument(
                                                "petId"
                                            ) {
                                                nullable =
                                                    true
                                            }
                                        )
                                    ) {
                                        val viewModel =
                                            it.sharedViewModel<PetViewModel>(
                                                navController = navController
                                            )

                                        val arguments =
                                            it.arguments
                                        Log.d(
                                            "Patypet's App",
                                            " ${arguments.toString()}"
                                        )

                                        val id =
                                            arguments?.getString(
                                                "petId"
                                            )?.toInt()
                                                ?: -1





                                        PetScreen(
                                        )
                                    }

                                    composable(Screen.Patypet.Profile.route) {
                                        val viewModel =
                                            it.sharedViewModel<AuthViewModel>(
                                                navController = navController
                                            )

                                        val scope = viewModel.viewModelScope
                                        ProfileScreen(
                                            deauthenticate = {

                                                scope.launch{
                                                    viewModel.logout()

                                                    navController.navigate(
                                                        Screen.Auth.route
                                                    ) {
                                                        popUpTo(
                                                            Screen.Patypet.route
                                                        ) {
                                                            inclusive =
                                                                true
                                                        }
                                                    }
                                                }



                                                Log.d(
                                                    "MainActivity",
                                                    "Deauthenticate in Profile"
                                                )
                                            }
                                        )
                                    }

                                    composable(Screen.Patypet.Shop.route) {

                                        val viewModel =
                                            it.sharedViewModel<ShopViewModel>(
                                                navController = navController
                                            )
                                        ShopScreen(
                                            onSearch = viewModel::searchProduct,
                                            onJenisChange = viewModel::setJenis,
                                            onProductChange = viewModel::setProduct,
                                            productUiState = viewModel.productUiState,
                                            onGetProduct = viewModel::getShopProduct
                                        )
                                    }

                                    composable(Screen.Patypet.Adopt.route) {

                                        val viewModel =
                                            it.sharedViewModel<AdoptViewModel>(
                                                navController = navController
                                            )
                                        AdoptScreen()

                                    }

                                    composable(Screen.Patypet.Caretake.route) {

                                        val viewModel =
                                            it.sharedViewModel<CaretakeViewModel>(
                                                navController = navController
                                            )
                                        CaretakeScreen()

                                    }

                                    composable(Screen.Patypet.Vet.route) {

                                        val viewModel =
                                            it.sharedViewModel<VetViewModel>(
                                                navController = navController
                                            )
                                        VetScreen()

                                    }


                                }
                            }
                        }
                    }

                }
            }
        }
    }

    private fun isVisibleBarRoute(route: String?): Boolean {
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

    private fun handleImageRetake() {
        shouldShowCamera.value = true
        photoUri = Uri.EMPTY
        shouldShowPhoto.value = false
    }

    private fun getOutputDirectory(): File {
        val mediaDir =
            externalMediaDirs.firstOrNull()?.let {
                File(
                    it,
                    resources.getString(R.string.app_name)
                ).apply { mkdirs() }
            }

        return if (mediaDir != null && mediaDir.exists()) mediaDir else filesDir
    }

    override fun onResume() {
        super.onResume()
        cameraExecutor = Executors.newSingleThreadExecutor()
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
                Log.i(
                    "kilo",
                    "Permission previously granted"
                )
                shouldShowCamera.value = true // 👈🏽
            }

            ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                Manifest.permission.CAMERA
            ) -> Log.i(
                "kilo",
                "Show camera permissions dialog"
            )

            else -> requestPermissionLauncher.launch(
                Manifest.permission.CAMERA
            )
        }
    }

    private val requestPermissionLauncher =
        registerForActivityResult(
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
inline fun <reified T : ViewModel> NavBackStackEntry.sharedViewModel(
    navController: NavController
): T {
    val navGraphRoute =
        destination.parent?.route ?: return hiltViewModel()
    val parentEntry = remember(this) {
        navController.getBackStackEntry(navGraphRoute)
    }
    return hiltViewModel(parentEntry)
}

fun Activity.openAppSettings() {
    Intent(
        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
        Uri.fromParts("package", packageName, null)
    ).also(::startActivity)
}

