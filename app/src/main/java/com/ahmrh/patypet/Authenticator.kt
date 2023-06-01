package com.ahmrh.patypet

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ahmrh.patypet.ui.navigation.Screen
import com.ahmrh.patypet.ui.screen.auth.AuthViewModel
import com.ahmrh.patypet.ui.screen.auth.SignInScreen
import com.ahmrh.patypet.ui.screen.auth.SignUpScreen
import com.ahmrh.patypet.ui.screen.auth.LandingScreen
import com.ahmrh.patypet.utils.ViewModelFactory

@Composable
fun Authenticator(
    navController: NavHostController = rememberNavController(),
    viewModel: AuthViewModel = viewModel(
        factory = ViewModelFactory(LocalContext.current)
    )
){

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    Scaffold {
        NavHost(
            navController = navController,
            startDestination = Screen.Landing.route,
            modifier = Modifier.padding(it)
        ){
            composable(Screen.Landing.route){
                LandingScreen(
                    navigateToSignIn = {
                       navController.navigate(Screen.SignIn.route)
                    },
                    navigateToSignOut = {
                        navController.navigate(Screen.SignUp.route)
                    }
                )

            }
            composable(Screen.SignIn.route){
                SignInScreen(
                    viewModel.uiState,
                    viewModel::login,
                    viewModel::forceLogin
                )
            }
            composable(Screen.SignUp.route){
                SignUpScreen(
                    viewModel.uiState,
                    viewModel::register
                )
            }
        }
    }
}