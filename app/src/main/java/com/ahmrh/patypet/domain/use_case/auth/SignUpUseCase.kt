package com.ahmrh.patypet.domain.use_case.auth

import com.ahmrh.patypet.common.Resource
import com.ahmrh.patypet.data.repositories.AuthRepository
import com.ahmrh.patypet.domain.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    operator fun invoke(
        name: String,
        email: String,
        password: String
    ): Flow<Resource<String>> = flow {
        try {
            emit(Resource.Loading())
            repository.register(name, email, password)
            emit(Resource.Success("User registered"))
        } catch (e: HttpException) {
            emit(
                Resource.Error(
                    message = e.localizedMessage
                        ?: "An unexpected error occurred"
                )
            )
        } catch (e: IOException) {
            emit(Resource.Error(message = "Couldn't reach server"))
        }
    }

}