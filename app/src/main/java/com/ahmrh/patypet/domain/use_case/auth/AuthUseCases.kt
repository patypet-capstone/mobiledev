package com.ahmrh.patypet.domain.use_case.auth

import javax.inject.Inject

data class AuthUseCases @Inject constructor(
    val signUp: SignUpUseCase,
    val signIn: SignInUseCase,
)
