package com.ahmrh.patypet.ui.navigation

sealed class Screen(val route: String) {


    // Authentication
    object Landing: Screen("landing")

    object Login: Screen("login")

    object Register: Screen("register")


    // Main App
    object Home: Screen("home")

    object Pet: Screen("pet")

    object PetCamera: Screen("pet_camera")

    object Profile: Screen("profile")

    object Detail : Screen("pet/{petId}"){
        fun createRoute(petId: Int) = "pet/$petId"
    }
}
