package com.ahmrh.patypet.domain.state

sealed class AuthState{
    object Unknown: AuthState()
    data class Authenticated(val token: String) : AuthState()

}
