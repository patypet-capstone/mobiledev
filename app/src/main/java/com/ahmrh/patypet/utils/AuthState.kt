package com.ahmrh.patypet.utils

sealed class AuthState{
    object Unknown: AuthState()
    data class Authenticated(val token: String) : AuthState()

}
