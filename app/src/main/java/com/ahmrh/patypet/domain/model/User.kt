package com.ahmrh.patypet.domain.model

data class User(
    val id: Int? = null,
    val token: String = "",
    val name: String? = null,
    val email: String? = null
)
