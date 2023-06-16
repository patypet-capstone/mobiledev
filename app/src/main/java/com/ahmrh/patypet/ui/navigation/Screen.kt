package com.ahmrh.patypet.ui.navigation

sealed class Screen(val route: String) {
    // Launch
    object Launch: Screen("launch")


    // Authentication
    object Auth: Screen("auth"){
        object Landing: Screen("landing")

        object SignIn: Screen("sign_in")

        object SignUp: Screen("sign_up")
    }
//    object Landing: Screen("landing")
//
//    object SignIn: Screen("sign_in")
//
//    object SignUp: Screen("sign_up")


    // Patypet
    object Patypet: Screen("patypet") {

        object Home: Screen("home")

        object Pet: Screen("pet")
        object PredictionDetail: Screen("prediction_detail")

        object Profile: Screen("profile")

        object PetById : Screen("pet/{petId}"){
            fun createRoute(petId: Int) = "pet/$petId"
        }


        object Shop: Screen("shop")
        object Caretake: Screen("caretake")
        object Vet: Screen("vet")
        object Adopt: Screen("adopt")
    }




}
