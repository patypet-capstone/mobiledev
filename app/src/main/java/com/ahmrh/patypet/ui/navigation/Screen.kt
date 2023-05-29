package com.ahmrh.patypet.ui.navigation

sealed class Screen(val route: String) {
    object Auth: Screen("auth")

    object Home: Screen("home")

    object Pet: Screen("pet")

    object Profile: Screen("profile")

    object Detail : Screen("pet/{petId}"){
        fun createRoute(petId: Int) = "pet/$petId"
    }
}
