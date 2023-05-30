package com.ahmrh.patypet.utils

sealed class AuthState<out T: Any?>{
    object Unknown: AuthState<Nothing>()

    data class Authenticated<out T: Any>(val data: T) : AuthState<T>()

}
