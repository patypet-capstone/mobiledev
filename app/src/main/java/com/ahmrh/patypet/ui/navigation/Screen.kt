package com.ahmrh.patypet.ui.navigation

sealed class Screen(val route: String) {
    // Launch
    object Launch: Screen("launch")



    // Authentication
    object Landing: Screen("landing")

    object SignIn: Screen("sign_in")

    object SignUp: Screen("sign_up")


    // Patypet
    object Home: Screen("home")

    object Pet: Screen("pet")

    object Profile: Screen("profile")

    object Detail : Screen("pet/{petId}"){
        fun createRoute(petId: Int) = "pet/$petId"
    }


}
