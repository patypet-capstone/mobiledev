package com.ahmrh.patypet.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.ahmrh.patypet.R
import com.ahmrh.patypet.ui.navigation.NavigationItem
import com.ahmrh.patypet.ui.navigation.Screen


@Composable
fun BottomBar(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    onImageRetake: () -> Unit
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val navigationItems = listOf(
        NavigationItem(
            title = "Home",
            icon = Icons.Default.Home,
            screen = Screen.Patypet.Home
        ),
        NavigationItem(
            title = "Pet",
            icon = ImageVector.vectorResource(id = R.drawable.splash_logo_patypet),
            screen = Screen.Patypet.Pet
        ),
        NavigationItem(
            title = "Profile",
            icon = Icons.Default.Person,
            screen = Screen.Patypet.Profile
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
            label = { Text("Home") },
            selected = currentRoute == Screen.Patypet.Home.route,
            onClick = {
                navController.navigate(Screen.Patypet.Home.route)
            }
        )
        FloatingActionButton(
            modifier = Modifier
                .padding(12.dp)
                .clip(CircleShape),
            containerColor = MaterialTheme.colorScheme.secondary,
            onClick = {
                onImageRetake()
                navController.navigate(Screen.Patypet.Pet.route)
            }
        ) {
            Icon(
                imageVector = navigationItems[1].icon,
                contentDescription = navigationItems[1].title,
                modifier = Modifier
                    .padding(8.dp)
            )

        }

        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Profile",
                )
            },
            label = { Text("Profile") },
            selected = currentRoute == Screen.Patypet.Profile.route,
            onClick = {
                navController.navigate(Screen.Patypet.Profile.route)
            },

            )

    }

}