package com.ahmrh.patypet

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ahmrh.patypet.ui.navigation.NavigationItem
import com.ahmrh.patypet.ui.navigation.Screen
import com.ahmrh.patypet.ui.screen.auth.AuthViewModel
import com.ahmrh.patypet.ui.screen.patypet.home.HomeScreen
import com.ahmrh.patypet.ui.screen.patypet.home.MainViewModel
import com.ahmrh.patypet.ui.screen.patypet.pet.PetViewModel
import com.ahmrh.patypet.ui.theme.PatypetTheme
import com.ahmrh.patypet.utils.ViewModelFactory

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
    )

) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    Scaffold(
        bottomBar = {
            if (currentRoute != Screen.Detail.route) {
                BottomBar(navController)
            }
        }
    ) {
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(it)
        ){
            composable(Screen.Home.route){
                HomeScreen(
                    onLogout = authViewModel::logout
                )
            }
            composable(Screen.Profile.route){

            }
            composable(Screen.Pet.route){

            }
            composable(Screen.PetCamera.route){

            }
        }
    }
}

@Composable
private fun BottomBar(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val navigationItems = listOf(
        NavigationItem(
            title = "Home",
            icon = Icons.Default.Home,
            screen = Screen.Home
        ),
        NavigationItem(
            title = "Pet",
            icon = ImageVector.vectorResource(id = R.drawable.patypet_logo),
            screen = Screen.Home
        ),
        NavigationItem(
            title = "Profile",
            icon = Icons.Default.Person,
            screen = Screen.Profile
        )
    )
    NavigationBar(
        modifier = modifier
    ) {
        NavigationBarItem(
            icon = {
               Icon(
                   imageVector = Icons.Default.Home,
                   contentDescription = "Home"
               )
            },
            label = {Text("Home")},
            selected = currentRoute == Screen.Home.route,
            onClick = {

            }
        )
        FloatingActionButton(
            modifier = Modifier
                .padding(12.dp),
            onClick = {

            }
        ) {
            Icon(
                imageVector = navigationItems[1].icon,
                contentDescription = navigationItems[1].title
            )

        }

        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Profile",
                )
            },
            label = {Text("Profile")},
            selected = currentRoute == Screen.Profile.route,
            onClick = {

            },

        )

    }

}


@Preview(showBackground = true)
@Composable
fun PatypetAppPreview() {
    PatypetTheme {
        PatypetApp()
    }
}