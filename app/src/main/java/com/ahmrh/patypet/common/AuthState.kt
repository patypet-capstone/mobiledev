package com.ahmrh.patypet.common

import com.ahmrh.patypet.domain.model.User

sealed class AuthState{
    object Unknown: AuthState()
    data class Authenticated(val user: User = User()) : AuthState()

}
